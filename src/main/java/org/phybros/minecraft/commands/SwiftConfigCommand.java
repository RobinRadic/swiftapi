package org.phybros.minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.phybros.minecraft.Api;
import org.phybros.minecraft.SwiftApiPlugin;
import org.phybros.minecraft.configuration.Configuration;
import org.phybros.minecraft.extensions.SwiftExtension;

import java.util.Map;


public class SwiftConfigCommand implements ICommand
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, SwiftApiPlugin plugin) {

        switch(args.length) {
            case 1:
                // config
                Api.message(sender, "Syntax: swift config (get/set) <" + ChatColor.BLUE + "accessor" + ChatColor.RESET + "> <" + ChatColor.YELLOW + "configfile" + ChatColor.RESET + "> <" + ChatColor.GOLD + "path" + ChatColor.RESET + "> (newvalue)");
                Api.message(sender, "Example: swift config get " + ChatColor.BLUE + "core" + ChatColor.RESET + " " + ChatColor.YELLOW + "config" + ChatColor.RESET + " " + ChatColor.GOLD + "port" + ChatColor.RESET);
                Api.message(sender, "Example: swift config set " + ChatColor.BLUE + "core" + ChatColor.RESET + " " + ChatColor.YELLOW + "config" + ChatColor.RESET + " " + ChatColor.GOLD + "port" + ChatColor.RESET + " 21113");
                Api.message(sender, "Example: swift config get " + ChatColor.BLUE + "core" + ChatColor.RESET + " " + ChatColor.YELLOW + "config" + ChatColor.RESET + " " + ChatColor.GOLD + "messages.cpus" + ChatColor.RESET);
                Api.message(sender, "Example: swift config set " + ChatColor.BLUE + "core" + ChatColor.RESET + " " + ChatColor.YELLOW + "config" + ChatColor.RESET + " " + ChatColor.GOLD + "messages.cpus" + ChatColor.RESET + " Available cpus: %d");
                Api.message(sender, "List all accessors/configfiles: swift config list");

                break;
            case 2:
                // config list
                if(args[1].equals("list")) {
                    for (String accessor : Api.configuration().getAccessors()) {
                        for(String fileName : Api.configuration().getAccessorConfigFiles(accessor)){
                            Api.message(sender, ChatColor.BLUE + accessor + " " + ChatColor.YELLOW + fileName);
                        }
                    }
                }
                break;
            case 3:
                // config get <accessor>
                if(args[1].equals("get")){
                    if(Api.configuration().hasAccessor(args[2])){
                        for(String fileName : Api.configuration().getAccessorConfigFiles(args[2])){
                            Api.message(sender, ChatColor.BLUE + args[2] + " " + ChatColor.YELLOW + fileName);
                        }
                    }
                }
                break;
            case 4:
                // config get <accessor> <filename>
                if(args[1].equals("get")){
                    if(Api.configuration().has(args[2], args[3])){
                        Api.message(sender, ChatColor.BLUE + args[2] + " " + ChatColor.YELLOW + args[3]);
                        for(Map.Entry<String, String> entry : Api.configuration().get(args[2], args[3]).getLayout().get().entrySet()){
                            if( ! entry.getValue().equals("section")) {
                                Api.message(sender, entry.getKey(), getValue(Api.configuration().get(args[2], args[3]), entry.getKey()));
                            }
                        }
                    }
                }
                break;
            case 5:
                // config get <accessor> <filename> <path>
                if(args[1].equals("get")){
                    if(Api.configuration().has(args[2], args[3])){
                        if(Api.configuration().get(args[2], args[3]).has(args[4])){
                            Api.message(sender, getValue(Api.configuration().get(args[2], args[3]), args[4]));
                        }
                    }
                }
                break;
            case 6:
                // config set <accessor> <filename> <path> <value>
                if(args[1].equals("set")){
                    if(Api.configuration().has(args[2], args[3])){
                        if(Api.configuration().get(args[2], args[3]).has(args[4])){
                            Api.message(sender, setValue(Api.configuration().get(args[2], args[3]), args[4], args[5]));
                        }
                    }
                }
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


}