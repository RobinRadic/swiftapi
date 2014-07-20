package org.phybros.minecraft.api;

import org.bukkit.ChatColor;
import org.phybros.minecraft.SwiftApiPlugin;
import org.phybros.minecraft.commands.CommandHandler;
import org.phybros.minecraft.commands.SwiftCommand;
import org.phybros.minecraft.commands.SwiftExtensionsCommand;
import org.phybros.minecraft.commands.SwiftInfoCommand;
import org.phybros.minecraft.extensions.ExtensionBag;

/**
 * Created by radic on 7/20/14.
 */
public class Api {
    public static SwiftApiPlugin plugin;
    public static ExtensionBag extensions;
    public static CommandHandler commands;

    public static void init(SwiftApiPlugin plugin){
        Api.plugin = plugin;

        Api.extensions = ExtensionBag.getInstance();

        Api.commands = new CommandHandler(Api.plugin);
        Api.commands.register("swift", new SwiftCommand());
        Api.commands.register("extensions", new SwiftExtensionsCommand());
        Api.commands.register("info", new SwiftInfoCommand());
        Api.plugin.getCommand("swift").setExecutor(Api.commands);
    }


    public static void console(String name, String value){
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[" + ChatColor.GREEN + name + ChatColor.GOLD + "] " + ChatColor.WHITE + value);
    }

    public static void console(String value){
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + value);
    }

    public static void console(String name, String childName, String value){
        plugin.getServer().getConsoleSender().sendMessage(
                ChatColor.GOLD + "[" + ChatColor.GREEN + name + ChatColor.GOLD + "]" +
                        ChatColor.WHITE + "->" + ChatColor.AQUA + "(" + ChatColor.YELLOW + childName + ChatColor.AQUA + ") " +
                        ChatColor.WHITE + value);
    }
    /**
     * some more public accessible stuff here, to extend the SwiftServer
     */
}
