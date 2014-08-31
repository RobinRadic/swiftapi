package org.phybros.minecraft;

import org.apache.logging.log4j.LogManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.phybros.minecraft.commands.*;
import org.phybros.minecraft.configuration.Configuration;
import org.phybros.minecraft.configuration.ConfigurationFactory;
import org.phybros.minecraft.extensions.ExtensionBag;
import org.phybros.thrift.ConsoleLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SwiftApiPlugin extends JavaPlugin implements Listener {

    protected static SwiftApiPlugin plugin;
    protected Configuration config;
    protected ConfigurationFactory configurationFactory;
    protected ExtensionBag extensions;
    protected CommandHandler commands;
    protected List<ConsoleLine> consoleBuffer = new ArrayList<>();
    protected SwiftServer server;

    private Configuration buildConfig(){
        getConfigurationFactory().add("core", this, "config");
        Configuration config = getConfigurationFactory().get("core", "config");
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
        commands = new CommandHandler(plugin);
        extensions = new ExtensionBag();
        configurationFactory = new ConfigurationFactory();
        config = buildConfig();
        getServer().getPluginManager().registerEvents(this, this);

        commands.register("swift", new SwiftCommand());
        commands.register("start", new SwiftStartCommand());
        commands.register("stop", new SwiftStopCommand());
        commands.register("extensions", new SwiftExtensionsCommand());
        commands.register("config", new SwiftConfigCommand());
        registerCommands();

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


    /**
     * Gets the instantiated class for SwiftApiPlugin
     *
     * @return SwiftApiPlugin
     */
    public static SwiftApiPlugin getInstance() {
        return plugin;
    }

    /**
     * Gets the configuration of SwiftApi
     *
     * @return Configuration
     */
    public Configuration getConfiguration() {
        return config;
    }

    /**
     * Get the configuration factory, contains configuration of SwiftApi and it's extensions
     *
     * @return ConfigurationFactory
     */
    public ConfigurationFactory getConfigurationFactory() {
        return configurationFactory;
    }

    /**
     * Get the extensions container class instance
     *
     * @return ExtensionBag
     */
    public ExtensionBag getExtensions() {
        return extensions;
    }

    /**
     * The command handler registers and maintains all the SwiftApi and SwiftApi extensions commands
     *
     * @return CommandHandler
     */
    public CommandHandler getCommands() {
        return commands;
    }

    /**
     * Process the registered commands to execute when called
     */
    public void registerCommands(){
        getCommand("swift").setExecutor(SwiftApiPlugin.getInstance().getCommands());
    }

    public List<ConsoleLine> getConsoleBuffer() {
        return consoleBuffer;
    }

    /**
     * The server instance which handles all communication
     *
     * @return SwiftServer
     */
    public SwiftServer getSwiftServer() {
        return server;
    }

}
