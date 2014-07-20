package org.phybros.minecraft.commands2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.phybros.minecraft.SwiftApiPlugin;

public class SwiftCommand implements ICommand
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, SwiftApiPlugin plugin) {
        for (String key : SwiftApiPlugin.getApi().getCommands().getMap().keySet()) {
            SwiftApiPlugin.getApi().console("swift:command", "swift-command", key);
        }
        return false;
    }

}