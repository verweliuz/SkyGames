package me.verwelius.skygames.util;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Schematic {

    private final Clipboard clipboard;

    public Schematic(File file) {
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            this.clipboard = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void paste(World world, Location loc) {
        try (EditSession session = WorldEdit.getInstance().newEditSession(
                FaweAPI.getWorld(world.getName())
        )) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(session)
                    .to(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ()))
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }
    }

}
