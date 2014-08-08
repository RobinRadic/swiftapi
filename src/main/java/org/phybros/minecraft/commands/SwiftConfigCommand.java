package org.phybros.minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.phybros.minecraft.Api;
import org.phybros.minecraft.SwiftApiPlugin;
import org.phybros.minecraft.configuration.Configuration;
import org.phybros.thrift.SwiftApi;

import java.util.HashMap;
import java.util.Map;


public class SwiftConfigCommand implements ICommand
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, SwiftApiPlugin plugin) {

        switch(args.length)
        {
            case 1:
                // config
                for(Map.Entry<String, String> entry : SwiftApiPlugin.config.getLayout().get().entrySet()){
                    Api.message(sender, entry.getKey(), getValue(SwiftApiPlugin.config, entry.getKey()));
                }
                break;
            case 2:
                // config set
                // config get
                sender.sendMessage("The config command requires additional arguments");
                break;
            case 3:
                // config get <name>
                if(args[1].equals("get")) {
                    get(sender, args[2]);
                } else {
                    sender.sendMessage("The config get command requires additional arguments");
                }
                break;
            case 4:
                // config set <name> <val>
                // config get <ext> <name>
                if(args[1].equals("set")){
                    set(sender, args[2], args[3]);
                } else if(args[1].equals("get")){
                    get(sender, args[2], args[3]);
                } else {
                    sender.sendMessage("The command requires additional arguments");
                }
                break;
            case 5:
                // config set <ext> <name> <val>
                set(sender, args[2], args[3], args[4]);
                break;
        }
        return false;
    }

    private String getValue(Configuration config, String key){
        if(config.getLayout().has(key)) {
            String value = "NaN";
            switch (config.getLayout().get(key)) {
                case "string":
                    value = String.valueOf(config.getString(key));
                    break;
                case "boolean":
                    value = String.valueOf(config.getBoolean(key));
                    break;
                case "integer":
                    value = String.valueOf(config.getInt(key));
                    break;
                case "list":
                    value = String.valueOf(config.getStringList(key));
                    break;
                case "section":
                    value = config.getConfigurationSection(key).getKeys(false).toString();
                    break;
                case "vector":
                    value = config.getVector(key).toString();
                    break;
                default:
                    return "Not valid type";
            }
            return ChatColor.RESET + value; // + ChatColor.RESET + " (" + config.getLayout().get(key) + ")";
        }

        return "Configuration key " + key + " does not exist or is not configurable using commands";
    }

    private String setValue(Configuration config, String key, String value){
        if(config.getLayout().has(key)) {
            switch (config.getLayout().get(key)) {
                case "string":
                    config.set(key, value);
                    break;
                case "boolean":
                    config.set(key, Boolean.valueOf(value));
                    break;
                case "integer":
                    config.set(key, ((int) Integer.valueOf(value)));
                    break;
                case "list":
                    return "Can not set list items";
                case "section":
                    return "Can not set section items";
                case "vector":
                    return "Can not set vector items.. yet";
            }
            config.save();
            return "Configuration in " + config.getFileName() + " altered. Setted [" + key + ":" + config.getLayout().get(key) + "] to: " + value;
        }

        return "Configuration key " + key + " does not exist in " + config.getFileName();

    }

    private void get(CommandSender sender, String key){
        Api.message(sender, key, getValue(SwiftApiPlugin.config, key));
    }

    private void get(CommandSender sender, String extensionName, String key){

    }

    private void set(CommandSender sender, String key, String value){
        Api.message(sender, key, setValue(SwiftApiPlugin.config, key, value));
    }

    private void set(CommandSender sender, String extensionName, String key, String value){

    }
}