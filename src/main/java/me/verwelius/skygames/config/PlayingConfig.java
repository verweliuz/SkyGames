package me.verwelius.skygames.config;

import org.bukkit.Location;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class PlayingConfig {

    public Location spectatorSpawn = new Location(null, 0.5, 10, 0.5, 0.0f, 90.0f);

    public int countsKillFor = 200;

}
