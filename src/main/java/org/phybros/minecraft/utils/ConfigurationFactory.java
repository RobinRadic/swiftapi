package org.phybros.minecraft.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.phybros.minecraft.extensions.SwiftExtension;

import java.util.HashMap;

// Singleton
public class ConfigurationFactory implements Listener {

    // Hashmap<String plugin, Hashmap<filename, config object>>
    HashMap<String, HashMap<String, Configuration>> files;

    private static ConfigurationFactory instance = null;

    private ConfigurationFactory() {
        files = new HashMap<String, HashMap<String, Configuration>>();
    }

    public static ConfigurationFactory getInstance() {
        if(instance == null){
            instance = new ConfigurationFactory();
        }
        return instance;
    }

    public void create(SwiftExtension plugin, String fileName){
        Configuration config = new Configuration(plugin, fileName);
    }
    public void create(SwiftExtension plugin, String[] fileName){

    }

}
