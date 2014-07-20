package org.phybros.minecraft.extensions;

import org.bukkit.plugin.java.JavaPlugin;

import org.phybros.minecraft.Api;
import org.phybros.minecraft.SwiftApiPlugin;
import org.phybros.minecraft.utils.ConfigurationFactory;


import java.util.logging.Level;


abstract public class SwiftExtension extends JavaPlugin implements ISwiftApiExtension {

    protected String[] yamls = { "config", "plugin" };


    public final void yamlFile(String fileName)
    {

    }

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

        SwiftApiPlugin.extensions.add(this);
        this.enable();
        Api.debug("Extension:onEnable", this.name());
    }

    @Override
    public final void onDisable() {
        SwiftApiPlugin.extensions.remove(name());
        this.disable();
        Api.console("Extension:onDisable: ", this.name());
    }

    public ConfigurationFactory() {
        return ConfigurationFactory.getInstance();
    }
    public final String name(){
        return getName();
    }

    public final String getVersion() {
        return this.getDescription().getVersion();
    }

}
