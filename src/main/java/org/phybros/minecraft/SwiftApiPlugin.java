package org.phybros.minecraft;

import org.bukkit.plugin.java.JavaPlugin;

public class SwiftApiPlugin extends JavaPlugin {

	private SwiftServer server;

	@Override
	public void onEnable() {
		try {
			getLogger().info("SwiftApi was enabled.");
			this.saveDefaultConfig();

			server = new SwiftServer(this);
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
