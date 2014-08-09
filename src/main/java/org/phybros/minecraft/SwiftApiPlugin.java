package org.phybros.minecraft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.phybros.minecraft.commands.*;
import org.phybros.minecraft.configuration.ConfigurationFactory;
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
    public static List<ConsoleLine> consoleBuffer = new ArrayList<>();

    public static SwiftServer server;

    private Configuration buildConfig(){

        Api.configuration().add("core", this, "config");
        Configuration config = Api.configuration().get("core", "config");
        config.getLayout()
                .string("username", "password", "salt")
                .integer("port")
                .bool("logMethodCalls", "debug")
                .section("messages")
                .string("messages.deOp")
                .string("messages.op")
                .string("messages.cpus")
                .string("messages.uptime")
                .string("messages.memMax")
                .string("messages.memTotal")
                .string("messages.memFree")
                .section("errorMessages")
                .string("errorMessages.invalidAuthentication")
                .string("errorMessages.playerNotFound")
                .string("errorMessages.pluginNotFound")
                .string("errorMessages.worldNotFound")
                .string("errorMessages.createDirectoryError")
                .string("errorMessages.malformedUrl")
                .string("errorMessages.fileNotFound")
                .string("errorMessages.hashMismatch")
                .string("errorMessages.jarNotFound")
                .string("errorMessages.invalidPluginType")
                .string("errorMessages.noReadAccess")
                .string("errorMessages.noWriteAccess")
                .string("errorMessages.fileIsBinary");

        config.load();
        return config;

    }

	@Override
	public void onEnable() {
        plugin = this;
        config = buildConfig();
        pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(this, this);

        Api.registerCommand("swift", new SwiftCommand());
        Api.registerCommand("start", new SwiftStartCommand());
        Api.registerCommand("stop", new SwiftStopCommand());
        Api.registerCommand("extensions", new SwiftExtensionsCommand());
        Api.registerCommand("config", new SwiftConfigCommand());
        Api.registerCommands();

		try {			
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch(IOException e) {
			getLogger().severe("Exception initializing stats: " + e.getMessage());			
		}

		try {
			this.saveDefaultConfig();

            ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(new SwiftFilter(this));

            server = new SwiftServer(this);
            server.startServer();
		} catch (Exception e) {
			getLogger().severe("SwiftApi was not enabled: " + e.getMessage());
		}


	}

	@Override
	public void onDisable() {
		try {
			server.stopServer();
			getLogger().info("SwiftApi was disabled");
		} catch (Exception e) {
			getLogger().severe(e.getMessage());
		}
	}


}
