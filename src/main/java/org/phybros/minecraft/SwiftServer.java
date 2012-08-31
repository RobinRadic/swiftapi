package org.phybros.minecraft;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.bukkit.Bukkit;
import org.phybros.thrift.Plugin;
import org.phybros.thrift.PluginService;

public class SwiftServer {

	private SwiftApiPlugin plugin;

	public SwiftServer(SwiftApiPlugin plugin) {
		this.plugin = plugin;

		this.start();
	}

	private void start() {
		(new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub

				try {
					PluginServiceHandler psh = new PluginServiceHandler();
					PluginService.Processor<PluginService.Iface> pro = new PluginService.Processor<PluginService.Iface>(
							psh);

					TServerTransport tst = new TServerSocket(21111);
					TServer t = new TSimpleServer(
							new TSimpleServer.Args(tst).processor(pro));
					plugin.getLogger().info("Starting server on port 21111");
					t.serve();
				} catch (Exception e) {
					Bukkit.getLogger().severe(e.getMessage());
				}
			}

		})).start();

	}

	public class PluginServiceHandler implements PluginService.Iface {

		public List<Plugin> getPlugins() throws TException {
			List<Plugin> serverPlugins = new ArrayList<Plugin>();
			
			for(org.bukkit.plugin.Plugin p : Bukkit.getServer().getPluginManager().getPlugins()) {
				Plugin newPlugin = new Plugin();

				newPlugin.authors = p.getDescription().getAuthors();
				newPlugin.description = p.getDescription().getDescription();
				newPlugin.enabled = p.isEnabled();
				newPlugin.name = p.getDescription().getName();
				newPlugin.version = p.getDescription().getVersion();
				newPlugin.website = p.getDescription().getWebsite();
				
				serverPlugins.add(newPlugin);
			}
			
			return serverPlugins;
		}

		public Plugin getPlugin(String name) throws TException {
			Plugin p = new Plugin();

			p.name = "This is a test, " + name;

			return p;
		}

	}

}
