package org.phybros.minecraft.extensions;

import org.bukkit.plugin.java.JavaPlugin;

import org.phybros.minecraft.api.Api;


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
        Api.extensions.add(this);
        this.enable();
        Api.console("SwiftApiExtension:extension:on-enable: " + this.name());
    }

    @Override
    public final void onDisable() {
        Api.extensions.remove(name());
        this.disable();
        Api.console("SwiftApiExtension:extension:on-disable: " + this.name());
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
