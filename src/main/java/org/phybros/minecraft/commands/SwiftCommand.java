package org.phybros.minecraft.commands;
//Imports for the base command class.

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.phybros.minecraft.api.Api;


//This class implements the Command Interface.
public class SwiftCommand implements ICommand
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, JavaPlugin plugin) {
        for (String key : Api.commands.getCommands().keySet()) {
            Api.console("swift:command", "swift-command", key);
        }
        return false;
    }

}