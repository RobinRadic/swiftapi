package nl.radic.swiftapi;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.config.DefaultConfig;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Plugin(id = SwiftApiPlugin.NAME, name = "SwiftApi", version = "1.0")
public class SwiftApiPlugin {
    public static final String NAME = "SwiftApi";
    protected static SwiftApiPlugin instance;
    protected static Game game;
    protected Config config;

    @Inject
    public PluginContainer container;

    @Inject
    protected Logger logger;

    public Logger getLogger() {
        return logger;
    }

    @Inject
    @DefaultConfig(sharedRoot = false)
    private File defaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;


    @Subscribe
    public void onPreInitializationEvent(PreInitializationEvent event)
    {
        instance = this;
        game = event.getGame();
        config = new Config(this, defaultConfig, configManager);
        config.init();
        logger.info("Config swiftapi.username: " + config.getNode("username").getString());


    }

    @Subscribe
    public void onServerStartedEvent(ServerStartedEvent event)
    {
        logger.info("Starting swift api server");
        try {
            SwiftServer server = new SwiftServer(this);
            server.startServer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Config getConfig() {
        return config;
    }

    public static SwiftApiPlugin getInstance() {
        return instance;
    }

    public static Game getGame() {
        return game;
    }
}