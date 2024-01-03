package me.verwelius.skygames.config;

import me.verwelius.skygames.util.DirFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.File;
import java.util.List;

@ConfigSerializable
public class MapConfig {

    public World gameWorld = Bukkit.getWorld("world");

    public File mapFile = new DirFile("map.schem");

    public Location mapLocation = new Location(null, 0, 80, 0);

    public List<Location> spawnSpots = List.of(
            new Location(null, 20.5, 64, -20.5),
            new Location(null, -20.5, 64, 20.5)
    );

}
