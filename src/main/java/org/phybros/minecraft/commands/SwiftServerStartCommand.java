package org.phybros.minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.phybros.minecraft.Api;
import org.phybros.minecraft.SwiftApiPlugin;

public class SwiftServerStartCommand implements ICommand
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, SwiftApiPlugin plugin) {
        Api.message(sender, "swift", "server-start-command");
        if(args.length > 0) Api.message(sender, plugin, "args:0", args[0]);
        if(args.length > 1) Api.message(sender, plugin, "args:1", args[1]);
        if(args.length > 2) Api.message(sender, plugin, "args:2", args[2]);


        return false;
    }
}