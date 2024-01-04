package me.verwelius.skygames.game.state;

import me.verwelius.skygames.config.Config;
import me.verwelius.skygames.game.GameController;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public abstract class GameState implements Listener {

    protected final long started;

    protected final GameController controller;
    protected final Config config;

    protected final Set<Player> players = new HashSet<>();

    protected GameState(GameController controller, Config config) {
        this.controller = controller;
        this.config = config;
        this.started = System.currentTimeMillis();
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setHealth(20.0f);
        player.setSaturation(20.0f);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0.0f);
        player.clearActivePotionEffects();
        player.getInventory().clear();
        player.getOpenInventory().getTopInventory().clear();
        player.setItemOnCursor(null);
        player.setVelocity(new Vector(0, 0, 0));
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Set<Player> getPlayers() {
        return players;
    }

}
