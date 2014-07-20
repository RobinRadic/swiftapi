package org.phybros.minecraft.commands;
//Imports we will need.

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


import java.util.HashMap;

//The class will implement CommandExecutor.
public class CommandHandler implements CommandExecutor
{
    private JavaPlugin plugin;
    public CommandHandler(JavaPlugin plugin){
        this.plugin = plugin;
    }
    private static HashMap<String, ICommand> commands = new HashMap<String, ICommand>();


    public void register(String name, ICommand cmd) {
        commands.put(name, cmd);
    }

    public HashMap<String, ICommand> getCommands(){
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
            getExecutor("swift").onCommand(sender, cmd, commandLabel, args, this.plugin);
            return true;
        }

        if(args.length > 0) {
            if(exists(args[0])){
                getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args, this.plugin);
                return true;
            } else {
                sender.sendMessage("This command doesn't exist!");
                return true;
            }
        }

        return false;
    }

}