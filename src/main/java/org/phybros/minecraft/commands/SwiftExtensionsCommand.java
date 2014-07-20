package org.phybros.minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.phybros.minecraft.api.Api;

public class SwiftExtensionsCommand implements ICommand
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, JavaPlugin plugin) {

        Api.console("swift:extensions", Api.extensions.toString());

        return false;
    }
}