package org.phybros.minecraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.phybros.thrift.ConsoleLine;

public class SwiftApiPlugin extends JavaPlugin {

	private SwiftServer server;
	public List<ConsoleLine> last500;

	public SwiftApiPlugin() {
	}

	@Override
	public void onEnable() {
		try {
			last500 = new ArrayList<ConsoleLine>();
			this.saveDefaultConfig();
			this.getServer().getLogger().addHandler(new ConsoleHandler(this));
			server = new SwiftServer(this);

			// mcstats
			Metrics metrics = new Metrics(this);
			metrics.start();

			getLogger().info("SwiftApi was enabled.");
		} catch (Exception e) {
			getLogger().severe(e.getMessage());
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
