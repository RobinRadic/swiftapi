package org.phybros.minecraft.extensions;

import org.bukkit.plugin.java.JavaPlugin;

import org.phybros.minecraft.Api;
import org.phybros.minecraft.SwiftApiPlugin;
import org.phybros.minecraft.utils.Configuration;
import org.phybros.minecraft.utils.ConfigurationFactory;

abstract public class SwiftExtension extends JavaPlugin implements ISwiftApiExtension {

    protected String[] yamls = null;

    private Configuration[] configFiles = null;

    public Configuration config(String ymlFileName)
    {
        return new Configuration(this, ymlFileName); // @todo should work with factory
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

        if(yamls.length > 0) {
            ConfigurationFactory instance = ConfigurationFactory.getInstance();
            instance.create(this, yamls);
        }

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


    public final String name(){
        return getName();
    }

    public final String getVersion() {
        return this.getDescription().getVersion();
    }

}
