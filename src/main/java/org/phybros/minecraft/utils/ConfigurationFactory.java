package org.phybros.minecraft.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.phybros.minecraft.extensions.SwiftExtension;

import java.util.HashMap;

/**
 * Created by radic on 7/21/14.
 */
public class ConfigurationFactory implements Listener {

    private static ConfigurationFactory instance = null;

    // plugin, files
    HashMap<String, HashMap<String, Configuration>> files;

    protected ConfigurationFactory() {
        files = new HashMap<String, Configuration>();
    }

    public void create(SwiftExtension plugin, String fileName){
        Configuration config = new Configuration(plugin, fileName);
    }
    public void create(SwiftExtension plugin, String[] fileName){

    }

    public static ConfigurationFactory getInstance() {
        if(instance == null){
            instance = new ConfigurationFactory();
        }
        return instance;
    }

    @EventHandler
    public void onPluginEnabled(PluginEnableEvent event){

    }

}
