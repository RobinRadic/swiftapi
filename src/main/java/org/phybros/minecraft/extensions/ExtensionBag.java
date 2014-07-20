package org.phybros.minecraft.extensions;

import org.radic.core.Radic;
import org.radic.core.exceptions.ExtensionNotExistsException;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by radic on 7/20/14.
 */

public class ExtensionBag  {
    private static ExtensionBag instance = null;

    private final Logger log = Logger.getLogger("Minecraft");
    private HashMap<String, SwiftApiExtension> extensions;


    protected ExtensionBag(){
        extensions = new HashMap<String, SwiftApiExtension>();
    }

    public static ExtensionBag getInstance(){
        if(instance == null)
        {
            instance = new ExtensionBag();
        }

        Radic.console("radic:extensions:get-instance", "count", instance.count() + "");
        return instance;
    }


    public void add(SwiftApiExtension extension){
        extensions.put(extension.name(), extension);
        Radic.console("radic:extensions:add", extension.name(), extension.getVersion());
    }


    public SwiftApiExtension get(String name) throws ExtensionNotExistsException {
        if(has(name)) {
            Radic.console("radic:extensions:get", name);
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
            Radic.console("radic:extensions:remove", name);
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
