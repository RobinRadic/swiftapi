package org.phybros.minecraft.extensions;

import org.bukkit.plugin.java.JavaPlugin;
import org.phybros.minecraft.Api;
import org.phybros.minecraft.SwiftApiPlugin;
import org.phybros.minecraft.commands.ICommand;
import org.phybros.minecraft.configuration.Configuration;
import org.phybros.minecraft.configuration.ConfigurationFactory;
import org.phybros.minecraft.configuration.Layout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

abstract public class SwiftExtension extends JavaPlugin implements ISwiftApiExtension {

    /**
     * Instance of the SwiftExtension plugin
     */
    public static SwiftExtension plugin;

    public static Logger log;
    private Set<String> apiHandlers;
    private HashMap<String, Configuration> configCollection = new HashMap<String, Configuration>();

    /**
     * Gets the Configuration object for the associated filename after it's been registered with RegisterConfig
     *
     * @param fileName Configuration file name
     * @return Configuration
     */
    public Configuration config(String fileName){
        return configCollection.get(fileName);
    }

    public boolean hasApiHandlers() {
        return apiHandlers.size() > 0;
    }

    public Set<String> getApiHandlers() {
        return apiHandlers;
    }

    /**
     * Designated method to place all registerApiHandler, registerCommand and registerConfig calls.
     */
    abstract public void register();


    /**
     * Registers a Thrift service to be included by the server
     * @param thriftServiceClassName The name of the service
     */
    public final void registerApiHandler(String thriftServiceClassName){
        if( ! apiHandlers.contains(thriftServiceClassName) ) {
            apiHandlers.add(thriftServiceClassName);
        }
    }

    /**
     * Registers a command in the swift namespace
     * @param name The command name
     * @param command A class that implements the ICommand interface to do stuff when the command is fired
     */
    public final void registerCommand(String name, ICommand command) {
        Api.registerCommand(name, command);
    }

    /**
     * Registers a config file for your extension. Can be accessed by config(fileName) and if the layout is defined, the configuration will be adjustable by commands
     * @param commandAccessor The accessor name, usually a shorthand version of your extension's name (eg: vault)
     * @param fileName The name of the config file, without .yml extension
     * @return Layout The layout object allows defining of configurable config keys trough the commandline
     */
    public final Layout registerConfig(String commandAccessor, String fileName){
        ConfigurationFactory factory = SwiftApiPlugin.getInstance().getConfigurationFactory();
        factory.add(commandAccessor, this, fileName);
        Configuration configFile = factory.get(commandAccessor, fileName);
        configCollection.put(fileName, configFile);

        return configCollection.get(fileName).getLayout();
    }


    /**
     * Method will be called when plugin is enabled
     */
    public void enable() {

    }

    /**
     * Method will be called when plugin is disabled
     */
    public void disable() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void onEnable() {
        plugin = this;
        log = SwiftApiPlugin.getInstance().getLogger();
        apiHandlers  = new HashSet<>();

        SwiftApiPlugin.getInstance().getExtensions().add(this);
        register();

        for(Configuration config : configCollection.values()) {
            config.load();
        }

        this.enable();
    }

    @Override
    public final void onDisable() {

        if(configCollection.size() > 0) {
            for(String configFile : configCollection.keySet()) {
                configCollection.get(configFile).save();
            }
        }

        SwiftApiPlugin.getInstance().getExtensions().remove(getName());
        this.disable();
    }


    public final String getVersion() {
        return this.getDescription().getVersion();
    }

}
