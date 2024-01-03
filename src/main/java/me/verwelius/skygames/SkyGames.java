package me.verwelius.skygames;

import me.verwelius.skygames.command.LogCommand;
import me.verwelius.skygames.config.Config;
import me.verwelius.skygames.config.serializers.FileSerializer;
import me.verwelius.skygames.config.serializers.LocationSerializer;
import me.verwelius.skygames.config.serializers.WorldSerializer;
import me.verwelius.skygames.game.GameController;
import me.verwelius.skygames.util.DirFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;

public final class SkyGames extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        DirFile.setFolder(getDataFolder());

        File configFile = new DirFile("config.yml");
        Config config = getConfig(configFile);

        GameController controller = new GameController(this, config);
        Bukkit.getPluginManager().registerEvents(controller, this);

        new LogCommand(controller).register(getCommand("log"));

        controller.start();
    }

    private Config getConfig(File file) {
        try {
            YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                    .defaultOptions(opts -> opts.serializers(build -> {
                        build.register(File.class, new FileSerializer());
                        build.register(World.class, new WorldSerializer());
                        build.register(Location.class, new LocationSerializer());
                    }))
                    .nodeStyle(NodeStyle.BLOCK)
                    .path(file.toPath())
                    .build();
            ConfigurationNode root = loader.load();
            Config config = root.get(Config.class);
            root.set(Config.class, config);
            loader.save(root);
            return config;
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }

}
