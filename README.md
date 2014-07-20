![SwiftApi Logo](https://dev.bukkit.org/media/images/62/892/SwiftApi-256.png "SwiftApi is an Apache Thrift based API for your Bukkit server")

Check the [SwiftApi](https://bitbucket.org/phybros/swiftapi) page for all information. Do not use this fork.


##### Extending SwiftApi

The main plugin file
```
#!java
package org.yourname.extension.vault;

import org.phybros.minecraft.Api;
import org.phybros.minecraft.extensions.SwiftExtension;      
import org.yourname.extension.vault.commands.SwiftVaultCommand; 
 
class SwiftApiVaultExtension implements SwiftApiExtension {

    // Note that we are not using onEnable and onDisable, instead use enable and disable
    public void enable() {  
        Api.commands.register("vault", new SwiftVaultCommand());
        Api.swiftApi.extendHandler("vault", new SwiftVaultApiHandler())
    }
    
    public void disable() {
        Api.plugin.getServer(); // Api.plugin is the SwiftApiPlugin instance
    }
}
```

Exposing functionality to the api will go something like this
```
#!java
package org.yourname.extension.vault;

class SwiftVaultApiHandler implements SwiftVaultApi.IFace {
    public void addMoney(String playerName, Double amount){
        // code here
    }
}
```


A command 
```
#!java
package org.yourname.extension.vault.commands;

import org.phybros.minecraft.Api;

// Every command that you want under the /swift handler should implement the ICommand interface
import org.phybros.minecraft.commands.ICommand;      
 
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
