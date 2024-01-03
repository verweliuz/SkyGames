package me.verwelius.skygames;

import me.verwelius.skygames.command.LogCommand;
import me.verwelius.skygames.config.MapConfig;
import me.verwelius.skygames.config.serializers.FileSerializer;
import me.verwelius.skygames.config.serializers.LocationSerializer;
import me.verwelius.skygames.config.serializers.WorldSerializer;
import me.verwelius.skygames.game.GameController;
import me.verwelius.skygames.util.DirFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;

public final class SkyGames extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        DirFile.setFolder(getDataFolder());

        File mapConfigFile = new DirFile("map.yml");
        MapConfig mapConfig = (MapConfig) getConfig(mapConfigFile, MapConfig.class);

        GameController controller = new GameController(mapConfig);
        Bukkit.getPluginManager().registerEvents(controller, this);

        new LogCommand(controller).register(getCommand("log"));
    }

    private Object getConfig(File file, Class<?> clazz) {
        try {
            YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                    .defaultOptions(opts -> opts.serializers(build -> {
                        build.register(File.class, new FileSerializer());
                        build.register(World.class, new WorldSerializer());
                        build.register(Location.class, new LocationSerializer());
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
