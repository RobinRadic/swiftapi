![SwiftApi Logo](https://dev.bukkit.org/media/images/62/892/SwiftApi-256.png "SwiftApi is an Apache Thrift based API for your Bukkit server")

Check the [SwiftApi](https://bitbucket.org/phybros/swiftapi) page for all information. Do not use this fork.


##### Extending SwiftApi

The main plugin file
```
#!java
package org.yourname.extension.vault;

// The Api with static access to usefull stuff
import org.phybros.minecraft.Api;

// This is an extended JavaPlugin class
import org.phybros.minecraft.extensions.SwiftApiExtension;      

// Put your command(s) somewhere nice in a package and register them onEnable.
import org.yourname.extension.vault.commands.SwiftVaultCommand; 
 
class SwiftApiVaultExtension implements SwiftApiExtension {

    // Note that we are not using onEnable and onDisable, instead use enable and disable
    public void enable() {
        Api.plugin.getServer(); // Api.plugin is the SwiftApiPlugin instance
        
        // Registering commands, will be /swift vault
        Api.commands.register("vault", new SwiftVaultCommand());
    }
    
    public void disable() {
        Api.plugin.getServer(); // Api.plugin is the SwiftApiPlugin instance
    }
}
```


A command 
```
#!java
package org.yourname.extension.vault.commands;

import org.phybros.minecraft.Api;

// Every command that you want under the /swift handler should implement the ICommand interface
import org.phybros.minecraft.commands2.ICommand;      
 
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


//This class implements the Command Interface.
public class SwiftInfoCommand implements ICommand
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, JavaPlugin plugin) {
        this.sender = sender;

        sender.sendMessage("onCommand SwiftInfoCommand!");
        return false;
    }
}
```
