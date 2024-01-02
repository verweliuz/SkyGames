package me.verwelius.skygames;

import me.verwelius.skygames.config.MapConfig;
import me.verwelius.skygames.config.serializers.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
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
                commandSender.sendMessage(mapConfig.gameWorld);
                commandSender.sendMessage(mapConfig.mapPath);
                commandSender.sendMessage(String.valueOf(mapConfig.spawnSpots.size()));
                return true;
            }
        });
    }

    private Object getConfig(File file, Class<?> clazz) {
        try {
            return YamlConfigurationLoader.builder()
                    .defaultOptions(opts -> opts.serializers(build -> {
                        build.register(Location.class, new LocationSerializer());
                    }))
                    .path(file.toPath())
                    .build()
                    .load()
                    .get(clazz);
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }

}
