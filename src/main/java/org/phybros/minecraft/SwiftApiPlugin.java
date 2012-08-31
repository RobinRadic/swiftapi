package org.phybros.minecraft;

import org.bukkit.plugin.java.JavaPlugin;

public class SwiftApiPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("SwiftApi was enabled.");
		SwiftServer s = new SwiftServer(this);
	}
	
	@Override
	public void onDisable() {
		getLogger().info("SwiftApi was disabled");
	}


}
