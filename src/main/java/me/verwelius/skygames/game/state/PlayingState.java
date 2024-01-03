package me.verwelius.skygames.game.state;

import me.verwelius.skygames.config.Config;
import me.verwelius.skygames.game.GameController;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

        Location location = config.playingConfig.spectatorSpawn;
        location.setWorld(config.gameWorld);
        controller.schedule(() -> player.teleport(location), 0);
    }

    @Override
    public void removePlayer(Player player) {
        super.removePlayer(player);
        spectators.remove(player);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
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
        event.setCancelled(true);

        Player player = event.getPlayer();
        if(getPlayers().contains(player)) addSpectator(player);

        Player damager = lastDamager.get(player);
        Integer damageTime = lastPlayerDamageTime.get(player);
        if(damager != null && damageTime != null) {
            if(Bukkit.getCurrentTick() - damageTime <= config.playingConfig.countsKillFor) {
                kills.put(damager, kills.getOrDefault(damager, 0) + 1);
            }
        }

        Set<Player> alive = getPlayers().stream().filter(p -> !spectators.contains(p)).collect(Collectors.toSet());
        if(alive.size() == 1) {
            Player winner = alive.iterator().next();
            controller.announce(
                    Component.text("Игрок " + winner.getName() + " победил с " + kills.getOrDefault(winner, 0) + " убийствами"),
                    getPlayers()
            );
            controller.updateState(new EndingState(controller, config, winner));
        }
    }

}
