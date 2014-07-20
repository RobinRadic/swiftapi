package org.phybros.minecraft.api;

import org.bukkit.ChatColor;
import org.phybros.minecraft.SwiftApiPlugin;
import org.phybros.minecraft.extensions.ExtensionBag;

/**
 * Created by radic on 7/20/14.
 */
public class Api {
    public static SwiftApiPlugin plugin;
    public static ExtensionBag extensions;

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
