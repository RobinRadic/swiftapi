SwiftApi
========

An Apache Thrift based API for CraftBukkit Minecraft servers. This API allows
simple calls to Bukkit methods over the internet using almost any programming
language.

Build Prerequisites
----
* Apache Maven 3.0+


Build Instructions
----
Assuming you have maven installed, just run

    mvn package

And maven will produce a jar file called `/target/SwiftApi-<version>.jar`

Installation
----
Just drop the built jar file into your `plugins/` directory on the server, and 
restart the server.

Configuration
----
Edit the `plugins/SwiftApi/config.yml` file to taste. Some important options to 
set are:

    # Authentication Information (CHANGE THESE)
    username: admin
    password: password
    salt: saltines

Leaving these at default would make it easy for an attacker to use this API on 
your server.

Creating a Client
----
Once you have 
[downloaded the Apache Thrift compiler](http://thrift.apache.org/download/) and 
checked out this repo, you can create a client app in a few lines of code.

**1. Generate the code**  
You can generate the client library (example is in C#) by typing

    thrift -r --gen csharp SwiftApi.thrift

The `-r` option tells the compiler to generate code for all files (including 
ones that are referenced in `SwiftApi.thrift` with the `include` directive.
The `--gen csharp` option tells the compiler to generate C# code. 
This command will leave you with a gen-csharp directory with all the code you 
need to connect to a server running the SwiftApi plugin.

**2. Include the code in your app**  
If you're using C#, you can just drag all the source files right into your 
client app to use them, or alternatively you could create a new project with 
the generated files to keep your code and the generated code seperate.

**3. Call some methods!**  
Once you have included this generated code into your app, you can call API 
methods like so:

```csharp
TSocket socket = new TSocket("your.bukkitserver.org", 21111);
socket.Open();

TBinaryProtocol protocol = new TBinaryProtocol(new TFramedTransport(socket));
SwiftApi.Client client = new SwiftApi.Client(protocol);

Console.WriteLine("Server Version: " + client.getServerVersion(authString));

socket.Close();
```
    
The variable `authString` is required by all API methods. The `authString` is 
calculated like this:

```csharp
authString = sha256(username + methodName + password + salt);
```

Where the `username`, `password` and `salt` are the values you configured in the
plugin's config.yml file at install time and `methodName` is the name of the 
method you are calling.

More Info
----
For more information on Thrift (tutorials, examples in different languages 
etc), check these sites:

* [Apache Thrift Wikipedia Page](http://en.wikipedia.org/wiki/Apache_Thrift)
* [Apache Thrift Website](http://thrift.apache.org)
* [Apache Thrift Wiki](http://wiki.apache.org/thrift/) (Recommended!)
* [The "Missing Guide" to Apache Thrift](http://diwakergupta.github.com/thrift-missing-guide/) (Recommended!)
* [A blog post I wrote about Thrift](http://willwarren.com/2012/01/24/creating-a-public-api-with-apache-thrift/)
