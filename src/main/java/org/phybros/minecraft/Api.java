package org.phybros.minecraft;

import org.apache.thrift.TProcessor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.phybros.minecraft.commands.ICommand;


public class Api {

    private Api() {
    }


    /**
     * Resolves if the debug config value is set
     * @return
     */
    public static boolean isDebug(){
        return SwiftApiPlugin.getInstance().getConfiguration().getBoolean("debug");
    }


    /**
     * Registers a command in the swift namespace
     * @param name The command name
     * @param command A class that implements the ICommand interface to do stuff when the command is fired
     */
    public static void registerCommand(String name, ICommand command) {
        SwiftApiPlugin.getInstance().getCommands().register(name, command);
    }

    public static void registerCommands() {
        SwiftApiPlugin.getInstance().registerCommands();
    }

    /**
     * Registers a Thrift service to be included by the server
     * @param serviceName The name of the service
     * @param processor Instance of the server processor
     */
    public static void registerApiService(String serviceName, TProcessor processor){
        SwiftApiPlugin.getInstance().getSwiftServer().addApiProcessor(serviceName, processor);
    }




    public static void message(CommandSender sender, String message){
        sender.sendMessage("[" + ChatColor.LIGHT_PURPLE + "SwiftApi" + ChatColor.RESET + "] " + message);
    }

    public static void message(CommandSender sender, String key, String value) {
        message(sender, ChatColor.AQUA + key + " " + ChatColor.RESET + value);
    }

    public static void message(CommandSender sender, String parent, String key, String value) {
        message(sender, "[" + ChatColor.DARK_RED + parent + ChatColor.RESET + "] " + ChatColor.AQUA + key + " " + ChatColor.RESET + value);
    }


    public static void message(CommandSender sender, JavaPlugin plugin, String message){
        message(sender, "[" + ChatColor.YELLOW + plugin.getName() + ChatColor.RESET + "] " + message);
    }

    public static void message(CommandSender sender, JavaPlugin plugin, String key, String value){
        message(sender, plugin, ChatColor.AQUA + key + " " + ChatColor.RESET + value);
    }


    public static void message(CommandSender sender, JavaPlugin plugin, String parent, String key, String value) {
        message(sender, plugin, "[" + ChatColor.DARK_RED + parent + ChatColor.RESET + "] " + ChatColor.AQUA + key + " " + ChatColor.RESET + value);
    }




    public static void debug(String message){
        if(isDebug()) {
            SwiftApiPlugin.getInstance().getServer().getConsoleSender().sendMessage("[" + ChatColor.LIGHT_PURPLE + "SwiftApi-Debug" + ChatColor.RESET + "] " + message);
        }
    }

    public static void debug(String key, String value){
        debug(ChatColor.AQUA + key + ChatColor.RESET + ChatColor.WHITE + " - " + ChatColor.BOLD + value);
    }


    public static void debug(String parent, String key, String value) {
        debug("[" + ChatColor.GREEN + parent + ChatColor.RESET + "] " + ChatColor.AQUA + key + ": " + ChatColor.RESET + value);
    }

}
