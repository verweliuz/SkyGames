package me.verwelius.skygames.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import java.util.List;

public abstract class AbstractCommand implements TabExecutor {

    public void register(PluginCommand command) {
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    abstract void execute(CommandSender sender, String[] args);
    abstract List<String> suggest(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        execute(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return suggest(sender, args);
    }

}
