package org.phybros.minecraft.configuration;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.phybros.minecraft.extensions.SwiftExtension;

import java.util.HashMap;

// Singleton
public class ConfigurationFactory implements Listener {

    HashMap<String, HashMap<String, Configuration>> files;


    private static ConfigurationFactory instance = null;

    private ConfigurationFactory() {
        files = new HashMap<String, HashMap<String, Configuration>>();
    }

    public static ConfigurationFactory getInstance() {
        if (instance == null) {
            instance = new ConfigurationFactory();
        }
        return instance;
    }


    public boolean has(JavaPlugin plugin, String fileName) {
        return hasPluginConfigFile(plugin, fileName);
    }

    public void add(JavaPlugin plugin, String fileName) {
        if (!hasPluginConfigFile(plugin, fileName)) {
            addPluginConfigFile(plugin, fileName);
        }
    }

    public Configuration get(JavaPlugin plugin, String fileName) throws ConfigFileNotExistsException {
        if (hasPluginConfigFile(plugin, fileName)) {
            return getPluginConfigFile(plugin, fileName);
        }
        throw new ConfigFileNotExistsException(plugin, fileName);
    }


    protected boolean hasPluginConfig(JavaPlugin plugin) {
        if (files.containsKey(plugin.getName())) {
            return true;
        }
        return false;
    }

    protected boolean hasPluginConfigFile(JavaPlugin plugin, String fileName) {
        if (hasPluginConfig(plugin) && files.get(plugin.getName()).containsKey(fileName)) {
            return true;
        }
        return false;
    }

    protected Configuration getPluginConfigFile(JavaPlugin plugin, String fileName) {

        return files.get(plugin.getName()).get(fileName);

    }

    protected void addPluginConfigFile(JavaPlugin plugin, String fileName) {
        if (!hasPluginConfig(plugin)) {
            files.put(plugin.getName(), new HashMap<String, Configuration>());
        }

        HashMap<String, Configuration> pluginConfig = files.get(plugin.getName());

        if (!pluginConfig.containsKey(fileName)) {
            pluginConfig.put(fileName, new Configuration(plugin, fileName));
        }

    }

}
