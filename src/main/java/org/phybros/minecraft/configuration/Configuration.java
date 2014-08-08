package org.phybros.minecraft.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.phybros.minecraft.SwiftApiPlugin;


import java.io.*;
import java.util.HashMap;

/**
 * @todo make this a class that every extension can use to easily register their yml files
 */
public class Configuration extends YamlConfiguration {

    private File configFile;
    private JavaPlugin plugin;
    private String fileName;
    private Layout layout = new Layout();

    public Configuration(JavaPlugin plugin, String fileName){
        this.plugin = plugin;

        this.fileName = fileName;

        configFile = new File(plugin.getDataFolder(), fileName);

        try {
            firstRun();
        } catch (Exception e) {
            plugin.getLogger().severe(e.getMessage());
            e.printStackTrace();
        }

    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public void load() {
        try {
            super.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            super.save(configFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getConfigFile(){
        return configFile;
    }

    public String getFileName() { return fileName; }


    private void firstRun() throws Exception {
        if(!configFile.exists()){
            configFile.getParentFile().mkdirs();
            copy(plugin.getResource(fileName), configFile);
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
