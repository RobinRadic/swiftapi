package org.phybros.minecraft.extensions;

import org.apache.thrift.TProcessor;
import org.bukkit.plugin.java.JavaPlugin;

import org.phybros.minecraft.Api;
import org.phybros.minecraft.SwiftApiPlugin;
import org.phybros.minecraft.commands.ICommand;
import org.phybros.minecraft.configuration.Configuration;
import org.phybros.minecraft.configuration.ConfigurationFactory;

import java.net.URLClassLoader;
import java.util.HashMap;

abstract public class SwiftExtension extends JavaPlugin implements ISwiftApiExtension {

    private HashMap<String, ISwiftApiHandlerExtension> apiHandlers;

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



    public final void registerApiHandler(String thriftServiceClassName, ISwiftApiHandlerExtension handler)
    {
        if( ! apiHandlers.containsKey(thriftServiceClassName) ) {
            apiHandlers.put(thriftServiceClassName, handler);
        }
    }

    public boolean hasRegisteredApiHandlers() {
        return apiHandlers.size() > 0;
    }

    public HashMap<String, ISwiftApiHandlerExtension> getRegisteredApiHandlers() {
        return apiHandlers;
    }

    public final void registerCommand(String name, ICommand command) {
        Api.registerCommand(name, command);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void onEnable() {

        apiHandlers  = new HashMap<String, ISwiftApiHandlerExtension>();

        SwiftApiPlugin.extensions.add(this);

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
        Api.debug("Extension:onEnable", this.name());
    }

    @Override
    public final void onDisable() {

        if(config.size() > 0) {
            for(String configFile : config.keySet()) {
                config.get(configFile).save();
            }
        }

        SwiftApiPlugin.extensions.remove(name());
        this.disable();
        Api.debug("Extension:onDisable: ", this.name());
    }


    public final String name(){
        return getName();
    }

    public final String getVersion() {
        return this.getDescription().getVersion();
    }

}
