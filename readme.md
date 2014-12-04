---
---

# SwiftApi v2.0 is going [Sponge](http://spongepowered.org)!
Because Bukkit is dead (greatly timed, exactly when SwiftApi v2.0.0 was done) v2.0.0 will be re-written for Sponge. Sponge 
is the new Minecraft modding API taking Bukkit's place. Combining the strengths of the server and client modding,
 i'm certain Sponge is the way forward.
  
#### SwiftApi v2.0 for Sponge is still under development. [Check repository branch SwiftApi-sponge](https://github.com/RobinRadic/swiftapi/tree/SwiftApi-sponge)


I have been keeping track of Sponge's development, and they've 
[just released API v1.0](https://forums.spongepowered.org/t/status-update-api-release-edition/4889). 
This means i'll slowly start converting SwiftApi v2 to the Sponge platform. The same goes for Apache Thrift, 
they've just released v0.9.2 which allows multiplexing.
Because of extendability was decided to use in v2 even though at that time Thrift 0.9.2 was still in development.
Hence the SwiftApi v2 release was stalled, and never got passed Alpha with Bukkit going awol.

Keep an eye on the repository and this page, i'll be updating it soon with release info. For now **all text below here is for the old v1.7 Bukkit version**.


### Introduction

An Apache Thrift based API for CraftBukkit Minecraft servers. This API allows
simple calls to Bukkit methods over the internet using almost any programming
language. If you're a programmer who wants to create a killer server admin
app, this is the plugin for you!

Check out [MineCenter](http://minecenter.org) for an example of what you can do with the plugin.

### Build Prerequisites
* Apache Maven 3.0+


### Build Instructions
Assuming you have maven installed, just run

{% highlight bash %}
mvn clean package
{% endhighlight %}

And maven will produce a jar file called `/target/SwiftApi-<version>.jar`

### Installation
Just drop the jar file into your `plugins/` directory on the server, and 
restart the server.

### Configuration
Edit the `plugins/SwiftApi/config.yml` file to taste. Some important options to 
set are:
{% highlight yaml %}
# Authentication Information (CHANGE THESE)
username: admin
password: password
salt: saltines
{% endhighlight %}

Leaving these at default would make it easy for an attacker to use this API on 
your server.

### Available Languages
As of Apache Thrift version 0.9.1, the languages you can use to talk to your server with are:

* Actionscript 3 (AS3)
* C (using GLib)
* C
* C#
* Cocoa (Objective-C on Mac OS and iOS)
* Delphi
* Erlang
* Go
* Haskell
* Java
* Javascript
* OCaml
* Perl
* PHP
* Python (including Twisted async support)
* Ruby
* Smalltalk
