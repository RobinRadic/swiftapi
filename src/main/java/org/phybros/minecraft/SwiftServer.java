package org.phybros.minecraft;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
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

					TNonblockingServerTransport tst = new TNonblockingServerSocket(
							21111);
					TServer t = new TNonblockingServer(
							new TNonblockingServer.Args(tst).processor(pro));
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
			// TODO Auto-generated method stub
			return null;
		}

		public Plugin getPlugin(String name) throws TException {
			Plugin p = new Plugin();

			p.name = "This is a test";

			return p;
		}

	}

}
