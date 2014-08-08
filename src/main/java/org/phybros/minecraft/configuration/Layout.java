package org.phybros.minecraft.configuration;

import java.util.HashMap;

/**
 * Created by radic on 7/29/14.
 */
public class Layout {
    private HashMap<String, String> layout = new HashMap<String, String>();

    public void set(String type, String... keys){
        for(String key : keys){
            layout.put(key, type);
        }
    }

    public String get(String key){
        return layout.get(key);
    }

    public HashMap<String, String> get(){
        return layout;
    }

    public boolean has(String key){
        return layout.containsKey(key);
    }


    public Layout string(String... key){
        set("string", key);
        return this;
    }

    public Layout bool(String... key){
        set("boolean", key);
        return this;
    }

    public Layout integer(String... key){
        set("integer", key);
        return this;
    }

    public Layout list(String... key){
        set("list", key);
        return this;
    }

    public Layout vector(String... key){
        set("vector", key);
        return this;
    }

    public Layout section(String... key){
        set("section", key);
        return this;
    }

}
