package org.phybros.minecraft.extensions;

import org.bukkit.plugin.java.JavaPlugin;

import org.phybros.minecraft.Api;
import org.phybros.minecraft.SwiftApiPlugin;


import java.util.logging.Level;


abstract public class SwiftApiExtension extends JavaPlugin implements ISwiftApiExtension {

    /**
     * Called when this plugin is being enabled
     */
    public void enable() {
    }

    /**
     * Called when this plugin is being disabled
     */
    public void disable() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void onEnable() {
        SwiftApiPlugin.getApi().getExtensions().add(this);
        this.enable();
        SwiftApiPlugin.getApi().console("SwiftApiExtension:extension:on-enable: " + this.name());
    }

    @Override
    public final void onDisable() {
        SwiftApiPlugin.getApi().getExtensions().remove(name());
        this.disable();
        SwiftApiPlugin.getApi().console("SwiftApiExtension:extension:on-disable: " + this.name());
    }

    public final String name(){
        return getName();
    }

    public final String getVersion() {
        return this.getDescription().getVersion();
    }

    public void log(Level level, String message) {
        this.getLogger().log(level, message);
    }

    public void log(String message) {
        this.getLogger().log(Level.INFO, message);
    }


}
