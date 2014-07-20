package org.phybros.minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.phybros.minecraft.Api;
import org.phybros.minecraft.SwiftApiPlugin;

public class SwiftCommand implements ICommand
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, SwiftApiPlugin plugin) {
        for (String key : SwiftApiPlugin.commands.getMap().keySet()) {
            Api.console("swift:command", "swift-command", key);
        }
        return false;
    }


}