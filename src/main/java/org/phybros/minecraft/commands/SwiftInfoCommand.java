package org.phybros.minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.phybros.minecraft.Api;
import org.phybros.minecraft.SwiftApiPlugin;

public class SwiftInfoCommand implements ICommand
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, SwiftApiPlugin plugin) {
        Api.message(sender, "Command", "info not yet implemented");
        return false;
    }
}