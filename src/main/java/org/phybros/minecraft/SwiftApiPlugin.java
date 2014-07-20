package org.phybros.minecraft;

import org.apache.logging.log4j.LogManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.phybros.minecraft.commands2.CommandHandler;
import org.phybros.minecraft.commands2.SwiftCommand;
import org.phybros.minecraft.commands2.SwiftExtensionsCommand;
import org.phybros.minecraft.commands2.SwiftInfoCommand;


import java.io.IOException;
import java.lang.reflect.Constructor;

public class SwiftApiPlugin extends JavaPlugin implements Listener {
    private static SwiftApiPlugin plugin;
    private static Api api = null;
    private SwiftServer server;

    @SuppressWarnings("unused")
    public static Api getApi() {
        return api;
    }

    public void registerCommands(){
        CommandSender
        CommandHandler handler = new CommandHandler();
        handler.register("swift", new SwiftCommand());
        handler.register("extensions", new SwiftExtensionsCommand());
        handler.register("info", new SwiftInfoCommand());
        //getLogger().info(api.getCommands().getMap().size() + " is the size");
        getCommand("swift").setExecutor(handler);

    }


    private void registerCommand(CommandExecutor ce, String command) {

        try {
            final Constructor c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);
            final PluginCommand pc = (PluginCommand) c.newInstance(command, this);
            pc.setExecutor(ce);
            pc.setAliases(this.getAliases(command));
            pc.setDescription(this.getDescription(command));
            pc.setUsage(this.getUsage(command));
            this.getCommandMap().register(this.getDescription().getName(), pc);
        } catch (Exception e) {
            this.getLogger().warning("Could not register command \"" + command + "\" - an error occurred: " + e.getMessage() + ".");
        }
    }

	@Override
	public void onEnable() {
        api = Api.getInstance(this);


        getServer().getPluginManager().registerEvents(this, this);




		try {			
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch(IOException e) {
			getLogger().severe("Exception initializing stats: " + e.getMessage());			
		}



		try {
			this.saveDefaultConfig();

            SwiftFilter filter = new SwiftFilter(this);
            ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(filter);

            server = new SwiftServer(this);

			getLogger().info("SwiftApi was enabled.");
		} catch (Exception e) {
			getLogger().severe("SwiftApi was not enabled: " + e.getMessage());
		}
	}

	@Override
	public void onDisable() {
		try {
			server.stop();
			getLogger().info("SwiftApi was disabled");
		} catch (Exception e) {
			getLogger().severe(e.getMessage());
		}
	}


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

            api.console("on-command", cmd.getLabel());
            api.console("plugin", api.getPlugin().toString());

        return true;
    }


}
