![SwiftApi Logo](https://dev.bukkit.org/media/images/62/892/SwiftApi-256.png "SwiftApi is an Apache Thrift based API for your Bukkit server")

Check the [SwiftApi](https://bitbucket.org/phybros/swiftapi) page for all information. Do not use this fork.

##### Commands
- `swift` will list all registered commands including those from extensions
- `swift extensions` will list all extensions
- `swift info` will give some information?
- `swift config set <name> <value>` will set a config item
- `swift config get <name>` will set a config item
- `swift config vault set <name> <value>` will set a config item
- `swift config vault get <name>` will set a config item

### Extending SwiftApi

For a working example [check out the swiftapi-vault](https://bitbucket.org/robinradic/swiftapi-vault) extension.
 
SwiftApi provides several classes to simplefy the creation oof extensions. The general gist is:

- `org.phybros.minecraft.extensions.SwiftExtension` is an abstract class extending JavaPlugin. It provides several improvements to make extending a piece of cake.
- `org.phybros.minecraft.Api` provides several static methods that do certain stuff you can invoke from anywhere in your code.
- `org.phybors.commands.ICommand` is the interface used to internally register commands, so you can easily create and register commands in the swift namespace.
 
###### SwiftExtension
```
#!java
package org.yourname.extension.vault;

import org.phybros.minecraft.Api;
import org.phybros.minecraft.extensions.SwiftExtension;      
import org.yourname.extension.vault.commands.SwiftVaultCommand; 
 
class SwiftApiVaultExtension implements SwiftApiExtension {

    // overiding this array will allow easy yamlconfig access. put in the name of the yml files in resources
    protected String[] yamls = ["config", "banks", "cash"];
    
    // Note that we are not using onEnable and onDisable, instead use enable and disable
    public void enable() {  
        // Anything defined in yamlFiles will be accessible like this. 
        // config(filname) returns o.p.minecraft.utils.Configuration (YamlConfiguration) object
        boolean hasMoney = config("banks").getBoolean("hasMoney");
        
        // Any commands registered with the api will be callable like: swift vault
        registerCommand("vault", new SwiftVaultCommand());
        
        // This will register your handler with the SwiftServer and allows remote clients to use your methods
        extendSwiftApi("vault", new SwiftVaultApiHandler());        
    }
    
    public void disable() {
        // custom disable functionality here..
    }
}
```

Notes:

- the config yamls will automaticly create on first run, load on enable and save on disable.
- the handlers that extend the SwiftApi need to have a associated thrift file. 


###### SwiftApi server extension handler
```
#!java
package org.yourname.extension.vault;

class SwiftVaultApiHandler implements SwiftVaultApi.IFace {
    public void addMoney(String playerName, Double amount){
        // code here
    }
}
```


###### Commands
classes implementing `org.phybros.minecraft.commands.ICommand` can be registered with `SwiftExtension.registerCommand(name, command)`
```
#!java
package org.yourname.extension.vault.commands;

import org.phybros.minecraft.Api;
import org.phybros.minecraft.commands.ICommand;      
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.SwiftApiPlugin;


//This class implements the Command Interface.
public class SwiftInfoCommand implements ICommand
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, JavaPlugin SwiftApiPlugin) {
        // Some custom command code here.
        this.sender = sender;
        sender.sendMessage("onCommand SwiftInfoCommand!");
        return false;
    }
}
```


##### License
GNU General Public License version 3 (GPLv3)