package nl.radic.swiftapi;

import com.google.inject.Inject;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.service.config.DefaultConfig;

import java.io.File;
import java.io.IOException;

/**
 * Created by radic on 5/5/15.
 */
public class Config {

    protected SwiftApiPlugin plugin;
    private CommentedConfigurationNode config = null;
    private File defaultConfig;
    private ConfigurationLoader<CommentedConfigurationNode> configManager;


    public Config(SwiftApiPlugin plugin, File defaultConfig, ConfigurationLoader<CommentedConfigurationNode> configManager) {
        this.plugin = plugin;
        this.defaultConfig = defaultConfig;
        this.configManager = configManager;
        plugin.getLogger().info("Starting swiftapi configuration");
    }

    public void init(){
        plugin.getLogger().info("Init swiftapi configuration");
        try {

            if (!defaultConfig.exists()) {

                defaultConfig.getParentFile().mkdirs();
                defaultConfig.createNewFile();
                config = configManager.load();

                // This is a simple configuration node living in the 'top' path of your config file.
                // Here, you can put global configuration variables, such as a version number (for detecting
                // whether you need to integrate new changes to your config file structure into an existing
                // config file)
                //

                config.getNode("ConfigVersion").setValue(1);

                // This is a set of pathed config nodes, living in the DB 'folder' of the config file.
                // Splitting your config variables like produces sections to the config file, making sure
                // all config variables relating to eachother can be grouped together.
                // Also useful for passing only parts of your config to other modules; see below.

                config.getNode("debug").setValue(true);
                config.getNode("username").setValue("admin");
                config.getNode("password").setValue("password");
                config.getNode("salt").setValue("saltines");
                config.getNode("port").setValue(21111);
                configManager.save(config);
                plugin.getLogger().info("[ConfigDatabase]: Created default configuration");

            }

            config = configManager.load();

        } catch (IOException exception) {

            plugin.getLogger().error("[ConfigDatabase]: Couldn't create default configuration file!");

        }

    }

    public ConfigurationNode getNode(Object... path)
    {
        return this.config.getNode(path);
    }
}
