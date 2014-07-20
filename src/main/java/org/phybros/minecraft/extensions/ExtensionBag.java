package org.phybros.minecraft.extensions;


import org.phybros.minecraft.Api;
import org.phybros.minecraft.SwiftApiPlugin;

import java.util.HashMap;
import java.util.logging.Logger;


public class ExtensionBag  {

    private final Logger log = Logger.getLogger("Minecraft");

    private HashMap<String, SwiftApiExtension> extensions;


    public ExtensionBag(){
        extensions = new HashMap<String, SwiftApiExtension>();
    }

    public void add(SwiftApiExtension extension){
        extensions.put(extension.name(), extension);
        SwiftApiPlugin.getApi().console("Api:extensions:add", extension.name(), extension.getVersion());
    }


    public SwiftApiExtension get(String name) throws ExtensionNotExistsException {
        if(has(name)) {
            SwiftApiPlugin.getApi().console("Api:extensions:get", name);
            return extensions.get(name);
        } else {
            throw new ExtensionNotExistsException(name);
        }
    }

    public boolean has(String name){
        return extensions.containsKey(name);
    }

    public void remove(String name) throws ExtensionNotExistsException
    {
        if(has(name)) {
            extensions.remove(name);
            SwiftApiPlugin.getApi().console("Api:extensions:remove", name);
        } else {
            throw new ExtensionNotExistsException(name);
        }
    }

    public int count() {
        return extensions.size();
    }

    public String toString()
    {
        return extensions.toString();
    }
}
