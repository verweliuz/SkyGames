package me.verwelius.skygames.config;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class Config {

    public World gameWorld = Bukkit.getWorld("world");

    public MapConfig mapConfig = new MapConfig();
    public WaitingConfig waitingConfig = new WaitingConfig();

}
