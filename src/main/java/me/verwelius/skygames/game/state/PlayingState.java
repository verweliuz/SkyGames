package me.verwelius.skygames.game.state;

import me.verwelius.skygames.config.Config;
import me.verwelius.skygames.game.GameController;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class PlayingState extends GameState {

    private final Set<Player> initialPlayers;
    private final Set<Player> spectators;
    private final Map<Player, Integer> kills;
    private final Map<Player, Player> lastDamager;
    private final Map<Player, Integer> lastPlayerDamageTime;

    protected PlayingState(GameController controller, Config config, Set<Player> initialPlayers) {
        super(controller, config);
        this.initialPlayers = initialPlayers;
        this.spectators = new HashSet<>();
        this.kills = new HashMap<>();
        this.lastDamager = new HashMap<>();
        this.lastPlayerDamageTime = new HashMap<>();
    }

    @Override
    public void addPlayer(Player player) {
        super.addPlayer(player);
        player.getInventory().clear();

        if(initialPlayers.contains(player)) {
            player.setGameMode(GameMode.SURVIVAL);
        } else addSpectator(player);

    }

    private void addSpectator(Player player) {
        spectators.add(player);
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(config.playingConfig.spectatorSpawn);
    }

    @Override
    public void removePlayer(Player player) {
        super.removePlayer(player);
        spectators.remove(player);
        player.setHealth(0.0f);
    }

    @EventHandler
    private void onEntityDamage(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player player
        && event.getDamager() instanceof Player damager) {
            lastDamager.put(player, damager);
            lastPlayerDamageTime.put(player, Bukkit.getCurrentTick());
        }
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        addSpectator(player);

        Player damager = lastDamager.get(player);
        Integer damageTime = lastPlayerDamageTime.get(player);
        if(damager == null || damageTime == null) return;
        if(Bukkit.getCurrentTick() - damageTime <= config.playingConfig.countsKillFor) {
            kills.put(damager, kills.getOrDefault(damager, 0));
        }

        Stream<Player> alive = getPlayers().stream().filter(p -> !spectators.contains(p));
        if(alive.count() == 1) {
            Player winner = alive.findAny().get();
            controller.announce(
                    Component.text("Игрок " + winner.getName() + " победил с " + kills.getOrDefault(winner, 0) + " убийствами"),
                    getPlayers()
            );
            controller.updateState(new EndingState(controller, config, winner));
        }
    }

}
