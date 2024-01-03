package me.verwelius.skygames.config;

import org.bukkit.Location;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;
import java.util.Set;

@ConfigSerializable
public class WaitingConfig {

    public Set<Location> spawnCapsules = Set.of(
            new Location(null, 20.5, 5, -20.5),
            new Location(null, -20.5, 5, 20.5)
    );

    public Map<Integer, Integer> countdown = Map.of(
            2, 30,
            4, 5
    );

}
