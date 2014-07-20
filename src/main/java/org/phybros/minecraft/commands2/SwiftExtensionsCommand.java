package org.phybros.minecraft.commands2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.phybros.minecraft.SwiftApiPlugin;

public class SwiftExtensionsCommand implements ICommand
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, SwiftApiPlugin plugin) {

        SwiftApiPlugin.getApi().console("swift:extensions", SwiftApiPlugin.getApi().getExtensions().toString());

        return false;
    }
}