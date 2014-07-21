package org.phybros.minecraft.configuration;

import org.phybros.minecraft.extensions.SwiftExtension;

/**
 * Created by radic on 7/21/14.
 */
public class ConfigFileNotExistsException extends ArrayIndexOutOfBoundsException {

    public ConfigFileNotExistsException() {
        super("Config file does not exist in ConfigrationFactory files HashMap");
    }

    public ConfigFileNotExistsException(SwiftExtension plugin, String name) {
        super("Config for plugin " + plugin.getName() + " does not contains file " + name + " in ConfigrationFactory files HashMap");
    }

}
