package me.verwelius.skygames.command;

import me.verwelius.skygames.game.GameController;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class LogCommand extends AbstractCommand {

    private final GameController controller;

    public LogCommand(GameController controller) {
        this.controller = controller;
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {

        sender.sendMessage(String.valueOf(System.currentTimeMillis()));

    }

    @Override
    protected List<String> suggest(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
