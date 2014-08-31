![SwiftApi Logo](https://dev.bukkit.org/media/images/62/892/SwiftApi-256.png "SwiftApi is an Apache Thrift based API for your Bukkit server")

Check the [SwiftApi](https://bitbucket.org/phybros/swiftapi) page for all information. 


##### Commands
- `swift` lists all registered commands including those from extensions
- `swift start` starts the server
- `swift stop` stops the server
- `swift extensions` lists all extensions
- `swift config` displays a configuration how-to
- `swift config list` displays all extension and files that can be used in get/set
- `swift config get <accessor> <file> <path>` shows the configuration value
- `swift config set <accessor> <file> <path> <value>` will set a config item

### Extending SwiftApi

For a working demo example [check out the swiftapi-serverdata repository](https://bitbucket.org/robinradic/swiftapi-serverdata) or [click here to download](https://bitbucket.org/robinradic/swiftapi-serverdata/get/97a36d844a5d.zip).
 
SwiftApi provides several classes to ease the creation of extensions. The general gist is:

- `org.phybros.minecraft.extensions.SwiftExtension` is an abstract class extending JavaPlugin. It provides several improvements to make extending a piece of cake.
- `org.phybros.minecraft.Api` provides several static methods that do certain stuff you can invoke from anywhere in your code.
- `org.phybors.commands.ICommand` is the interface used to internally register commands, so you can easily create and register commands in the swift namespace.
 
###### SwiftExtension
```
#!java
package org.radic.minecraft.swiftapi.serverdata;

import org.apache.thrift.TProcessor;
import org.phybros.minecraft.Api;
import org.phybros.minecraft.extensions.SwiftExtension;
import org.radic.minecraft.swiftapi.serverdata.commands.ServerDataCommand;
import org.radic.minecraft.swiftapi.serverdata.handlers.ServerDataApiHandler;
import org.radic.minecraft.swiftapi.serverdata.thrift.SwiftApiServerData;

public class ServerDataExtension extends SwiftExtension {

    // Note that we are not using onEnable and onDisable, instead use enable and disable
    public void enable() {
        Api.debug("Haai");
    }

    public void disable() {
        Api.debug("Baai");
    }

    @Override
    public void register() {
    
        // Any commands registered with the api will be callable like: swift vault
        registerCommand("serverdata", new ServerDataCommand());
        
        // This will register your handler with the SwiftServer and allows remote clients to use your methods
        registerApiHandler("org.radic.serverdata.serverdata.thrift.SwiftApiServerData");
        
        // If you have configuration files, you can define them here. registerConfig(accessor, filename)
        // accessor is used for command line configuration, like: swift config set serverdata config logging.enabled true
        // filename is the name of the .yml file, without .yml
        registerConfig("serverdata", "config")
                .section("logging")
                .bool("logging.enabled")
                .string("logging.file");
        
        // By declaring .section, .bool, .string, etc. You can define what configuration may be altered from the command line.        
        registerConfig("serverdata", "worlds")
                .string("type");
    }

    @Override
    public TProcessor getApiProcessor(String name) {
        // For every Api handler you've registered with registerApiHandler, you need to return the processor.
        if (name.equals("SwiftApiServerData")) {
            return new SwiftApiServerData.Processor(new ServerDataApiHandler());
        }
        return null;
    }
}
```

###### SwiftApi server extension handler
```
#!java
package org.radic.minecraft.swiftapi.serverdata.handlers;

import org.radic.minecraft.swiftapi.serverdata.SysInfo;
import org.radic.minecraft.swiftapi.serverdata.thrift.*;

public class ServerDataApiHandler extends SwiftApiHandler implements SwiftApiServerData.Iface {

    private SysInfo si;
    public ServerDataApiHandler(){
        si = SysInfo.getInstance();
    }

    public Size makeSize(long bytes) {
        Size size = new Size();
        size.prettyBits = si.prettifyBytes(bytes, true);
        size.prettyBytes = si.prettifyBytes(bytes, false);
        size.KB = ((int) bytes / 1024);
        size.MB = ((int) bytes / 1024 / 1024);
        size.GB = ((int) bytes / 1024 / 1024 / 1024);
        return size;
    }
    
    // etc..
}
```


###### Commands
classes implementing `org.phybros.minecraft.commands.ICommand` can be registered with `SwiftExtension.registerCommand(name, command)`
```
#!java
package org.radic.minecraft.swiftapi.serverdata.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.phybros.minecraft.Api;
import org.phybros.minecraft.commands.ICommand;

import java.io.File;

public class ServerDataCommand implements ICommand
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args, SwiftApiPlugin plugin) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        File[] roots = File.listRoots();
        for (File root : roots) {
            Api.message(sender, "File system root: " + root.getAbsolutePath());
            Api.message(sender, "Total space (bytes): " + root.getTotalSpace());
            Api.message(sender, "Free space (bytes): " + root.getFreeSpace());
            Api.message(sender, "Usable space (bytes): " + root.getUsableSpace());
        }
        return true;
    }


}
```


##### License
GNU General Public License version 3 (GPLv3)