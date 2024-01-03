package me.verwelius.skygames.config;

import me.verwelius.skygames.util.DirFile;
import org.bukkit.Location;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.File;

@ConfigSerializable
public class MapConfig {

    public File mapFile = new DirFile("map.schem");

    public Location mapLocation = new Location(null, 0, 0, 0);

}
