### Creating a Client
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

{% highlight csharp %}
TTransport transport = new TSocket("your.bukkitserver.org", 21111);
transport = new TFramedTransport(transport);

TProtocol Protocol = new TBinaryProtocol(transport, true, true);

TMultiplexedProtocol multiplex;

multiplex = new TMultiplexedProtocol(Protocol, "SwiftApi");
SwiftApi.Iface swiftApi = new SwiftApi.Client(multiplex);

// If you are using SwiftApi extensions, SwiftApiVault for example
multiplex = new TMultiplexedProtocol(Protocol, "SwiftApiVault");
SwiftApiVault.Iface swiftApiVault = new SwiftApiVault.Client(multiplex);
    
transport.Open();
Console.WriteLine("Server Version: " + swiftApi.getServerVersion(authString));
Console.WriteLine("Permission groups: " + swiftApiVault.getGroups(authString));
transport.Close();
{% endhighlight %}
    
The variable `authString` is required by all API methods. The `authString` is 
calculated like this:

{% highlight csharp %}
authString = sha256(username + methodName + password + salt);
{% endhighlight %}

Where the `username`, `password` and `salt` are the values you configured in the
plugin's config.yml file at install time and `methodName` is the name of the 
method you are calling.

### More Info
For more information on Thrift (tutorials, examples in different languages 
etc), check these sites:

* [Apache Thrift Wikipedia Page](http://en.wikipedia.org/wiki/Apache_Thrift)
* [Apache Thrift Website](http://thrift.apache.org)
* [Apache Thrift Wiki](http://wiki.apache.org/thrift/) (Recommended!)
* [The "Missing Guide" to Apache Thrift](http://diwakergupta.github.com/thrift-missing-guide/) (Recommended!)
* [A blog post I wrote about Thrift](http://willwarren.com/2012/01/24/creating-a-public-api-with-apache-thrift/)

