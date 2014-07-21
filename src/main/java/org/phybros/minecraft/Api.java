package org.phybros.minecraft;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.phybros.minecraft.commands.CommandHandler;
import org.phybros.minecraft.commands.ICommand;
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

    public static Configuration config() {
        return SwiftApiPlugin.config;
    }

    public static ExtensionBag extensions() {
        return SwiftApiPlugin.extensions;
    }

    public static CommandHandler commands() {
        return SwiftApiPlugin.commands;
    }

    public static List<ConsoleLine> consoleLinex() {
        return SwiftApiPlugin.consoleBuffer;
    }

    public static void registerCommand(String name, ICommand command) {
        SwiftApiPlugin.commands.register(name, command);
    }

    public static void registerCommands() {
        SwiftApiPlugin.plugin.getCommand("swift").setExecutor(SwiftApiPlugin.commands);
    }

    public static void console(String name, String value){
        SwiftApiPlugin.plugin.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[" + ChatColor.GREEN + name + ChatColor.GOLD + "] " + ChatColor.WHITE + value);
    }

    public static void console(String value){
        SwiftApiPlugin.plugin.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + value);
    }

    public static void console(String name, String childName, String value){
        SwiftApiPlugin.plugin.getServer().getConsoleSender().sendMessage(
                ChatColor.GOLD + "[" + ChatColor.GREEN + name + ChatColor.GOLD + "]" +
                        ChatColor.WHITE + "->" + ChatColor.AQUA + "(" + ChatColor.YELLOW + childName + ChatColor.AQUA + ") " +
                        ChatColor.WHITE + value);
    }

    public static void debug(String name, String value)
    {
        System.out.print(SwiftApiPlugin.config.get("debug"));
        console("configetdebug", SwiftApiPlugin.config.get("debug").toString());
        if(true == true) {
            SwiftApiPlugin.plugin.getServer().getConsoleSender().sendMessage(
                    ChatColor.GOLD + "[" + ChatColor.GREEN + "SwiftApi:Debug" + ChatColor.GOLD + "]" +
                            ChatColor.WHITE + ":" + ChatColor.AQUA + "[" + ChatColor.YELLOW + name + ChatColor.AQUA + "]: " +
                            ChatColor.WHITE + value
            );
        }

    }

    /**
     * some more public accessible stuff here, to extend the SwiftServer
     */
}
