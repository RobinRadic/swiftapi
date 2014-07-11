package org.phybros.minecraft;

import org.apache.logging.log4j.LogManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.phybros.thrift.ConsoleLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SwiftApiPlugin extends JavaPlugin {

	private SwiftServer server;
	public List<ConsoleLine> consoleBuffer;

	public SwiftApiPlugin() {
	}

	@Override
	public void onEnable() {
		try {			
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch(IOException e) {
			getLogger().severe("Exception initializing stats: " + e.getMessage());			
		}
		
		try {
			consoleBuffer = new ArrayList<>();
			this.saveDefaultConfig();

            SwiftFilter filter = new SwiftFilter(this);
            ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(filter);

            server = new SwiftServer(this);

			getLogger().info("SwiftApi was enabled.");
		} catch (Exception e) {
			getLogger().severe("SwiftApi was not enabled: " + e.getMessage());
		}
	}

	@Override
	public void onDisable() {
		try {
			server.stop();
			getLogger().info("SwiftApi was disabled");
		} catch (Exception e) {
			getLogger().severe(e.getMessage());
		}
	}
}
