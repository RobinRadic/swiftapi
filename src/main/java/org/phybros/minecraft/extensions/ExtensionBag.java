package org.phybros.minecraft.extensions;


import org.phybros.minecraft.Api;


import java.util.HashMap;
import java.util.logging.Logger;


public class ExtensionBag  {

    private final Logger log = Logger.getLogger("Minecraft");

    private HashMap<String, SwiftExtension> extensions;


    public ExtensionBag(){
        extensions = new HashMap<String, SwiftExtension>();
    }

    public void add(SwiftExtension extension){
        extensions.put(extension.name(), extension);
        Api.debug("Api:extensions:add", extension.name() + " " + extension.getVersion());
    }


    public SwiftExtension get(String name) throws ExtensionNotExistsException {
        if(has(name)) {
            Api.debug("Api:extensions:get", name);
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
            Api.debug("Api:extensions:remove", name);
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
