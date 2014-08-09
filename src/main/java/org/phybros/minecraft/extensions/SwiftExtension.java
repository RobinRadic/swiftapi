package org.phybros.minecraft.extensions;

import org.bukkit.plugin.java.JavaPlugin;

import org.phybros.minecraft.Api;
import org.phybros.minecraft.commands.ICommand;
import org.phybros.minecraft.configuration.Configuration;
import org.phybros.minecraft.configuration.ConfigurationFactory;
import org.phybros.minecraft.configuration.Layout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

abstract public class SwiftExtension extends JavaPlugin implements ISwiftApiExtension {

    public static SwiftExtension plugin;
    public static Logger log;
    private Set<String> apiHandlers;
    private HashMap<String, Configuration> configCollection = new HashMap<String, Configuration>();


    public Configuration config(String fileName){
        return configCollection.get(fileName);
    }

    public boolean hasApiHandlers() {
        return apiHandlers.size() > 0;
    }

    public Set<String> getApiHandlers() {
        return apiHandlers;
    }

    abstract public void register();


    public final void registerApiHandler(String thriftServiceClassName){
        if( ! apiHandlers.contains(thriftServiceClassName) ) {
            apiHandlers.add(thriftServiceClassName);
        }
    }

    public final void registerCommand(String name, ICommand command) {
        Api.registerCommand(name, command);
    }

    public final Layout registerConfig(String commandAccessor, String fileName){
        ConfigurationFactory factory = ConfigurationFactory.getInstance();
        factory.add(commandAccessor, this, fileName);
        Configuration configFile = factory.get(commandAccessor, fileName);
        configCollection.put(fileName, configFile);

        return configCollection.get(fileName).getLayout();
    }


    public void enable() {

    }

    public void disable() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void onEnable() {
        plugin = this;
        log = Api.plugin().getLogger();
        apiHandlers  = new HashSet<>();

        Api.extensions().add(this);
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

        Api.extensions().remove(getName());
        this.disable();
    }


    public final String getVersion() {
        return this.getDescription().getVersion();
    }

}
