package me.verwelius.skygames.config;

import org.bukkit.Location;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import java.util.List;

@ConfigSerializable
public class MapConfig {

    public String gameWorld = "world";

    public String mapPath = "map.schem";

    public List<Location> spawnSpots = List.of(
            new Location(null, 20.5, 64, -20.5),
            new Location(null, -20.5, 64, 20.5)
    );

}
