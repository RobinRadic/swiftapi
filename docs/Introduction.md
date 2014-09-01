# SwiftApi 2.0 

## Introduction
SwiftApi is an Apache Thrift based API for CraftBukkit Minecraft servers. 
This API allows simple calls to Bukkit methods over the internet using your favorite programming language. 
This plugin acts as the server component, allowing you to write client programs/websites/apps that can talk to your CraftBukkit server in almost any programming language!


Basically, SwiftApi allows you write awesome code and apps that can talk to and control your Minecraft server.
An example of such an app is MineCenter. Check it out at [http://minecenter.org](http://minecenter.org).


SwiftApi also allows other plugins to use it's internal API to add custom data. 
For example, the SwiftApiVault extension uses the provided extension interface to include extra data for client application to request.
It is also possible to use the SwiftApi internal API to register your own service without the use of the extension interface.
This allows developers of existing plugins to make SwiftApi an optional feature/dependency. Check out the documentation for specificks.

## Gettings started
Start with reading the [basic usage](Basic-usage) page, then move over to any topic of interest:

- [Basic usage](Basic-usage)
- [Apache Thrift documentation](http://thrift.apache.com) and [usage examples](http://thrift.apache.com/examples) for all supported languages
- Client code examples for SwiftApi in [PHP](Basic-usage), [PHP](Basic-usage), [PHP](Basic-usage) and [PHP](Basic-usage)
- [Adding extra functionallity to SwiftApi](Basic-usage)
- [Extending SwiftApi](Basic-usage)
- SwiftApi [API](http://a) javadocs
- [Thrift generated documentation](http://a) for the SwiftApi thrift service.  

## Contribute
Think you can improve SwiftApi? Fork the repo on Bitbucket and submit a pull request!

Repo URL: https://bitbucket.org/phybros/swiftapi

## License
GNU GPLv3