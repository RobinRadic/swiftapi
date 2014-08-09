package org.phybros.minecraft;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.phybros.minecraft.commands.CommandHandler;
import org.phybros.minecraft.commands.ICommand;
import org.phybros.minecraft.configuration.ConfigurationFactory;
import org.phybros.minecraft.extensions.ExtensionBag;
import org.phybros.minecraft.configuration.Configuration;
import org.phybros.thrift.ConsoleLine;


import java.util.List;


public class Api {
    protected Api() {}

    public static SwiftApiPlugin plugin() {
        return SwiftApiPlugin.plugin;
    }

    public static PluginManager pluginManager() {
        return SwiftApiPlugin.pluginManager;
    }

    public static ConfigurationFactory configuration(){ return ConfigurationFactory.getInstance(); }

    public static ExtensionBag extensions() {
        return SwiftApiPlugin.extensions;
    }

    public static CommandHandler commands() {
        return SwiftApiPlugin.commands;
    }

    public static List<ConsoleLine> consoleBuffer() {
        return SwiftApiPlugin.consoleBuffer;
    }

    private static ConsoleCommandSender getConsoleSender(){
        return SwiftApiPlugin.plugin.getServer().getConsoleSender();
    }

    public static boolean isDebug(){
        return SwiftApiPlugin.config.getBoolean("debug");
    }

    public static void registerCommand(String name, ICommand command) {
        SwiftApiPlugin.commands.register(name, command);
    }

    public static void registerCommands() {
        SwiftApiPlugin.plugin.getCommand("swift").setExecutor(SwiftApiPlugin.commands);
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
            getConsoleSender().sendMessage("[" + ChatColor.LIGHT_PURPLE + "SwiftApi-Debug" + ChatColor.RESET + "] " + message);
        }
    }

    public static void debug(String key, String value){
        debug(ChatColor.AQUA + key + ChatColor.RESET + ChatColor.WHITE + " - " + ChatColor.BOLD + value);
    }


    public static void debug(String parent, String key, String value) {
        debug("[" + ChatColor.GREEN + parent + ChatColor.RESET + "] " + ChatColor.AQUA + key + ": " + ChatColor.RESET + value);
    }

}
