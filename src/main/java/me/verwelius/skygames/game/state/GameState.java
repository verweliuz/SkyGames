package me.verwelius.skygames.game.state;

import me.verwelius.skygames.config.Config;
import me.verwelius.skygames.game.GameController;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public abstract class GameState implements Listener {

    protected final GameController controller;
    protected final Config config;

    protected final Set<Player> players = new HashSet<>();

    protected GameState(GameController controller, Config config) {
        this.controller = controller;
        this.config = config;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Set<Player> getPlayers() {
        return players;
    }

}
