package me.verwelius.skygames.config;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class Config {

    public World gameWorld = Bukkit.getWorld("world");

    public MapConfig mapConfig = new MapConfig();
    public LootConfig lootConfig = new LootConfig();
    public WaitingConfig waitingConfig = new WaitingConfig();
    public PlayingConfig playingConfig = new PlayingConfig();
    public EndingConfig endingConfig = new EndingConfig();

}
