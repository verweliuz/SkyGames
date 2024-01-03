package me.verwelius.skygames.config.serializers;

import me.verwelius.skygames.util.DirFile;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.io.File;
import java.lang.reflect.Type;

public class FileSerializer implements TypeSerializer<File> {

    @Override
    public void serialize(Type type, File file, ConfigurationNode target) throws SerializationException {
        if(file == null) return;

        if(file instanceof DirFile dirFile) {
            target.set(dirFile.getRelativePath());
        }
    }

    @Override
    public File deserialize(Type type, ConfigurationNode target) throws SerializationException {

        String serialized = target.getString();
        if(serialized == null) return null;

        return new DirFile(serialized);
    }

}
