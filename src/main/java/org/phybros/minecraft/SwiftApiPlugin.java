package org.phybros.minecraft;

import org.apache.logging.log4j.LogManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.phybros.minecraft.commands.CommandHandler;
import org.phybros.minecraft.commands.SwiftCommand;
import org.phybros.minecraft.commands.SwiftExtensionsCommand;
import org.phybros.minecraft.commands.SwiftInfoCommand;
import org.phybros.minecraft.extensions.ExtensionBag;
import org.phybros.minecraft.configuration.Configuration;
import org.phybros.thrift.ConsoleLine;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SwiftApiPlugin extends JavaPlugin implements Listener {

    public static SwiftApiPlugin plugin;
    public static PluginManager pluginManager;
    public static Configuration config;

    public static final ExtensionBag extensions = new ExtensionBag();
    public static final CommandHandler commands = new CommandHandler();
    public static final List<ConsoleLine> consoleBuffer = new ArrayList<>();

    public static TestServer server;

	@Override
	public void onEnable() {
        plugin = this;
        pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(this, this);


        Api.registerCommand("swift", new SwiftCommand());
        Api.registerCommand("extensions", new SwiftExtensionsCommand());
        Api.registerCommand("info", new SwiftInfoCommand());
        Api.registerCommands();

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

            server = new TestServer(this);

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


}
