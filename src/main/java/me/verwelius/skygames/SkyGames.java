package me.verwelius.skygames;

import me.verwelius.skygames.config.MapConfig;
import me.verwelius.skygames.config.serializers.LocationSerializer;
import me.verwelius.skygames.config.serializers.WorldSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;

public final class SkyGames extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        System.out.println(123);

        File mapConfigFile = new File(getDataFolder() + File.separator + "map.yml");
        MapConfig mapConfig = (MapConfig) getConfig(mapConfigFile, MapConfig.class);

        getCommand("arbuz").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {
                commandSender.sendMessage(mapConfig.gameWorld.getName());
                commandSender.sendMessage(mapConfig.mapPath);
                commandSender.sendMessage(String.valueOf(mapConfig.spawnSpots.size()));
                Location loc = mapConfig.mapLocation.clone();
                loc.setWorld(mapConfig.gameWorld);
                ((Player) commandSender).teleport(loc);
                return true;
            }
        });
    }

    private Object getConfig(File file, Class<?> clazz) {
        try {
            YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                    .defaultOptions(opts -> opts.serializers(build -> {
                        build.register(Location.class, new LocationSerializer());
                        build.register(World.class, new WorldSerializer());
                    }))
                    .path(file.toPath())
                    .build();
            ConfigurationNode root = loader.load();
            Object config = root.get(clazz);
            root.set(clazz, config);
            loader.save(root);
            return config;
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }

}
