package me.verwelius.skygames.game;

import me.verwelius.skygames.config.Config;
import me.verwelius.skygames.game.state.GameState;
import me.verwelius.skygames.game.state.WaitingState;
import me.verwelius.skygames.util.Schematic;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Set;

public class GameController implements Listener {

    private final Plugin plugin;
    private final Config config;

    private GameState state;

    public GameController(Plugin plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void start() {
        Schematic schematic = new Schematic(config.mapConfig.mapFile);
        schematic.paste(config.gameWorld, config.mapConfig.mapLocation);

        updateState(new WaitingState(this, config));
    }

    public void updateState(GameState state) {
        if(this.state != null) {
            this.state.getPlayers().forEach(state::addPlayer);
            this.state.getPlayers().forEach(this.state::removePlayer);
            HandlerList.unregisterAll(this.state);
        }

        this.state = state;
        Bukkit.getPluginManager().registerEvents(state, plugin);
    }

    public void announce(Component message, Set<Player> audience) {
        audience.forEach(p -> p.sendMessage(message));
    }

    public BukkitTask schedule(Runnable task, int delay, int period) {
        return Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        state.addPlayer(event.getPlayer());
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        state.removePlayer(event.getPlayer());
    }

}
