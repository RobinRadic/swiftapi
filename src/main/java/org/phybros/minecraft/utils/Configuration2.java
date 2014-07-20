package org.phybros.minecraft.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

/**
 * @todo make this a class that every extension can use to easily register their yml files
 */
public class Configuration2 {

    private File configFile;
    private FileConfiguration config;


    private JavaPlugin plugin;

    public Configuration2(JavaPlugin plugin){
        this.plugin = plugin;

        configFile = new File(plugin.getDataFolder(), "config.yml");


        try {
            firstRun();
        } catch (Exception e) {
            plugin.getLogger().severe(e.getMessage());
            e.printStackTrace();
        }

        config = new YamlConfiguration();


    }


    public void load() {
        try {
            config.load(configFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(configFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public FileConfiguration getConfig(){

        return config;
    }


    public Object get(String path){
        return config.get(path);
    }

    private void firstRun() throws Exception {
        if(!configFile.exists()){
            configFile.getParentFile().mkdirs();
            copy(plugin.getResource("config.yml"), configFile);
        }



    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
