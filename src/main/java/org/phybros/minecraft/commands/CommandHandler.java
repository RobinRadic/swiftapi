package org.phybros.minecraft.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.phybros.minecraft.SwiftApiPlugin;

import java.util.HashMap;

public class CommandHandler implements CommandExecutor
{
    protected SwiftApiPlugin plugin;
    public CommandHandler(SwiftApiPlugin plugin){
        this.plugin = plugin;
    }
    private static HashMap<String, ICommand> commands = new HashMap<String, ICommand>();

    public void register(String name, ICommand cmd) {
        commands.put(name, cmd);
    }

    public HashMap<String, ICommand> getMap(){
        return commands;
    }

    public boolean exists(String name) {
        return commands.containsKey(name);
    }

    public ICommand getExecutor(String name) {
        return commands.get(name);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(args.length == 0) {
            getExecutor("swift").onCommand(sender, cmd, commandLabel, args, plugin);
            return true;
        }

        if(args.length > 0) {
            if(exists(args[0])){
                getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args, plugin);
                return true;
            } else {
                sender.sendMessage("This command doesn't exist!");
                return true;
            }
        }

        return false;
    }

}