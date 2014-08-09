package org.phybros.minecraft.configuration;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Set;

// Singleton
public class ConfigurationFactory implements Listener {

    HashMap<String,  HashMap<String, Configuration>> files;


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

    public boolean hasAccessor(String accessor){
        return files.containsKey(accessor);
    }

    public boolean has(String accessor, String fileName){
        if( files.containsKey(accessor) ){
            return files.get(accessor).containsKey(fileName);
        }
        return false;
    }

    public void add(String accessor, JavaPlugin plugin, String fileName){
        if( ! hasAccessor(accessor) ){
            files.put(accessor, new HashMap<String, Configuration>());
        }
        if( ! has(accessor, fileName) ){
            files.get(accessor).put(fileName, new Configuration(plugin, fileName + ".yml"));
        }
    }

    public Configuration get(String accessor, String fileName) throws ConfigFileNotExistsException{
        if(has(accessor, fileName)){
            return files.get(accessor).get(fileName);
        }
        throw new ConfigFileNotExistsException();
    }


    public Set<String> getAccessors(){
        return files.keySet();
    }

    public Set<String> getAccessorConfigFiles(String accessor) throws ConfigFileNotExistsException{
        if(hasAccessor(accessor)){
            return files.get(accessor).keySet();
        }
        throw new ConfigFileNotExistsException();
    }



}
