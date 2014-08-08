package org.phybros.minecraft.extensions;

import org.bukkit.plugin.java.JavaPlugin;

import org.phybros.minecraft.Api;
import org.phybros.minecraft.commands.ICommand;
import org.phybros.minecraft.configuration.Configuration;
import org.phybros.minecraft.configuration.ConfigurationFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

abstract public class SwiftExtension extends JavaPlugin implements ISwiftApiExtension {

    public static SwiftExtension plugin;

    public static Logger log;

    private Set<String> apiHandlers;

    protected String[] yamls = {};

    private HashMap<String, Configuration> config = new HashMap<String, Configuration>();

    public Configuration config(String fileName)
    {
        return config.get(fileName);
    }

    /**
     * Called when this plugin is being enabled
     */
    public void enable() {

    }

    /**
     * Called when this plugin is being disabled
     */
    public void disable() {
    }


    public final void registerApiHandler(String thriftServiceClassName)
    {
        if( ! apiHandlers.contains(thriftServiceClassName) ) {
            apiHandlers.add(thriftServiceClassName);
        }
    }

    public boolean hasApiHandlers() {
        return apiHandlers.size() > 0;
    }

    public Set<String> getApiHandlers() {
        return apiHandlers;
    }

    public final void registerCommand(String name, ICommand command) {
        Api.registerCommand(name, command);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void onEnable() {
        plugin = this;
        log = Api.plugin().getLogger();
        apiHandlers  = new HashSet<>();

        Api.extensions().add(this);

        if(yamls.length > 0) {
            ConfigurationFactory factory = ConfigurationFactory.getInstance();

            for(String fileName : yamls) {
                if( ! factory.has(this, fileName) ) {
                    factory.add(this, fileName);
                }

                if( ! config.containsKey(fileName) ) {
                    Configuration configFile = factory.get(this, fileName);
                    configFile.load();
                    config.put(fileName, configFile);
                }
            }
        }

        this.enable();
        Api.debug("Extension:onEnable", this.getName());
    }

    @Override
    public final void onDisable() {

        if(config.size() > 0) {
            for(String configFile : config.keySet()) {
                config.get(configFile).save();
            }
        }

        Api.extensions().remove(getName());
        this.disable();
        Api.debug("Extension:onDisable: ", this.getName());
    }


    public final String getVersion() {
        return this.getDescription().getVersion();
    }

}
