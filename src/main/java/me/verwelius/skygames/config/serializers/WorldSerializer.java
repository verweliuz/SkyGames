package me.verwelius.skygames.config.serializers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class WorldSerializer implements TypeSerializer<World> {

    @Override
    public void serialize(Type type, World world, ConfigurationNode target) throws SerializationException {
        if(world == null) return;

        target.set(world.getName());
    }

    @Override
    public World deserialize(Type type, ConfigurationNode target) throws SerializationException {

        String serialized = target.getString();
        if(serialized == null) return null;

        return Bukkit.getWorld(serialized);
    }


}
