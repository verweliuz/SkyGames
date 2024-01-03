package me.verwelius.skygames.game;

import me.verwelius.skygames.config.MapConfig;
import me.verwelius.skygames.util.Schematic;
import org.bukkit.event.Listener;

public class GameController implements Listener {

    private final MapConfig mapConfig;

    public GameController(MapConfig mapConfig) {
        this.mapConfig = mapConfig;
    }

    public void start() {
        Schematic schematic = new Schematic(mapConfig.mapFile);
        schematic.paste(mapConfig.gameWorld, mapConfig.mapLocation);
    }

}
