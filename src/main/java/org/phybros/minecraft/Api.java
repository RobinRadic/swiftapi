package org.phybros.minecraft;

import org.bukkit.ChatColor;
import org.phybros.minecraft.commands2.CommandHandler;
import org.phybros.minecraft.commands2.ICommand;
import org.phybros.minecraft.extensions.ExtensionBag;
import org.phybros.thrift.ConsoleLine;

import java.util.ArrayList;
import java.util.List;


public class Api {

    private static Api instance = null;

    private SwiftApiPlugin plugin;
    private ExtensionBag extensions;
    private CommandHandler commands;
    private List<ConsoleLine> consoleBuffer;

    protected Api(SwiftApiPlugin plugin) {
        this.plugin = plugin;
        extensions = new ExtensionBag();
        commands = new CommandHandler();
        consoleBuffer = new ArrayList<>();
    }

    public static Api getInstance(SwiftApiPlugin plugin) {
        if(instance == null) {
            instance = new Api(plugin);
        }
        return instance;
    }

    public SwiftApiPlugin getPlugin() {
        return plugin;
    }

    public ExtensionBag getExtensions() {
        return extensions;
    }

    public CommandHandler getCommands() {
        return commands;
    }

    public List<ConsoleLine> getConsoleBuffer() { return consoleBuffer; }

    public void registerCommand(String name, ICommand command) {
        commands.register(name, command);
    }

    public void registerCommands() {
        //plugin.getCommand("swift").setExecutor(commands2);
    }

    public void console(String name, String value){
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[" + ChatColor.GREEN + name + ChatColor.GOLD + "] " + ChatColor.WHITE + value);
    }

    public void console(String value){
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + value);
    }

    public void console(String name, String childName, String value){
        plugin.getServer().getConsoleSender().sendMessage(
                ChatColor.GOLD + "[" + ChatColor.GREEN + name + ChatColor.GOLD + "]" +
                        ChatColor.WHITE + "->" + ChatColor.AQUA + "(" + ChatColor.YELLOW + childName + ChatColor.AQUA + ") " +
                        ChatColor.WHITE + value);
    }
    /**
     * some more public accessible stuff here, to extend the SwiftServer
     */
}
