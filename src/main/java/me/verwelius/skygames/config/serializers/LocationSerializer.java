package me.verwelius.skygames.config.serializers;

import org.bukkit.Location;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class LocationSerializer implements TypeSerializer<Location> {

    @Override
    public void serialize(Type type, Location loc, ConfigurationNode target) throws SerializationException {
        if(loc == null) return;

        String serialized = loc.getX() + " " + loc.getY() + " " + loc.getZ();

        if(loc.getPitch() != 0.0F || loc.getYaw() != 0.0F) {
            serialized += " " + loc.getPitch() + " " + loc.getYaw();
        }

        target.set(serialized);

    }

    @Override
    public Location deserialize(Type type, ConfigurationNode target) throws SerializationException {

        String serialized = target.getString();
        if(serialized == null) return null;

        String[] strings = serialized.split(" ");
        if(strings.length != 3 && strings.length != 5) return null;

        Location loc = new Location(null,
                Integer.parseInt(strings[0]),
                Integer.parseInt(strings[1]),
                Integer.parseInt(strings[2])
        );

        if(strings.length == 5) {
            loc.setPitch(Integer.parseInt(strings[3]));
            loc.setYaw(Integer.parseInt(strings[4]));
        }

        return loc;
    }


}
