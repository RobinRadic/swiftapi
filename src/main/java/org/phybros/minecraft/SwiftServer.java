package org.phybros.minecraft;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.bukkit.Bukkit;
import org.phybros.thrift.EAuthException;
import org.phybros.thrift.EDataException;
import org.phybros.thrift.ErrorCode;
import org.phybros.thrift.Plugin;
import org.phybros.thrift.SwiftApi;

public class SwiftServer {

	private int port;
	private TServer server;
	private SwiftApiPlugin plugin;

	public class SwiftApiHandler implements SwiftApi.Iface {

		/**
		 * Get a loaded server plugin by name
		 * 
		 * @throws TException
		 *             , EDataException
		 * @return Plugin The plugin
		 * @see org.phybros.thrift.SwiftApi.Iface#getPlugins()
		 */
		public Plugin getPlugin(String authString, String name)
				throws TException, EDataException, EAuthException {
			logCall("getPlugin");
			authenticate(authString, "getPlugin");

			Plugin newPlugin = new Plugin();
			org.bukkit.plugin.Plugin p = plugin.getServer().getPluginManager()
					.getPlugin(name);

			if (p == null) {
				EDataException e = new EDataException();
				e.code = ErrorCode.NOT_FOUND;
				e.message = "Server plugin \"" + name + "\" not found";
				throw e;
			}

			newPlugin.authors = p.getDescription().getAuthors();
			newPlugin.description = p.getDescription().getDescription();
			newPlugin.enabled = p.isEnabled();
			newPlugin.name = p.getDescription().getName();
			newPlugin.version = p.getDescription().getVersion();
			newPlugin.website = p.getDescription().getWebsite();

			return newPlugin;
		}

		/**
		 * This method returns a list of all the currently loaded plugins on the
		 * server.
		 * 
		 * @throws TException
		 * @return List<Plugin> A list of the plugins on the server
		 * @see org.phybros.thrift.SwiftApi.Iface#getPlugins()
		 */
		public List<Plugin> getPlugins(String authString) throws TException,
				EAuthException {
			logCall("getPlugins");
			authenticate(authString, "getPlugins");

			List<Plugin> serverPlugins = new ArrayList<Plugin>();

			for (org.bukkit.plugin.Plugin p : plugin.getServer()
					.getPluginManager().getPlugins()) {
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

		private void logCall(String methodName) {
			if (plugin.getConfig().getBoolean("logMethodCalls")) {
				plugin.getLogger().info(
						"SwiftApi method called: " + methodName + "()");
			}
		}

		private void authenticate(String authString, String methodName)
				throws EAuthException {
			String username = plugin.getConfig().getString("username");
			String password = plugin.getConfig().getString("password");
			String salt = plugin.getConfig().getString("salt");

			// build the pre-hashed string
			String myAuthString = username + methodName + password + salt;

			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(myAuthString.getBytes());
				String hash = byteToString(md.digest());
				plugin.getLogger().info("Expecting: " + hash);
				plugin.getLogger().info("Received:  " + authString);

				if (!hash.equalsIgnoreCase(authString)) {
					plugin.getLogger().info("Invalid Authentication received");
					EAuthException e = new EAuthException();
					e.code = ErrorCode.INVALID_AUTHSTRING;
					e.message = "Authentication string was invalid";
					throw e;
				}
			} catch (NoSuchAlgorithmException algex) {
				plugin.getLogger().severe(algex.getMessage());
			}
		}

		private String byteToString(byte[] bytes) {
			String result = "";
			for (int i = 0; i < bytes.length; i++) {
				result += String.format("%02x", bytes[i]);
			}
			return result;
		}

	}

	public SwiftServer(SwiftApiPlugin plugin) {
		this.plugin = plugin;
		this.port = plugin.getConfig().getInt("port");

		this.start();
	}

	private void start() {
		(new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub

				try {
					SwiftApiHandler psh = new SwiftApiHandler();
					SwiftApi.Processor<SwiftApi.Iface> pro = new SwiftApi.Processor<SwiftApi.Iface>(
							psh);

					TServerTransport tst = new TServerSocket(port);
					server = new TSimpleServer(
							new TSimpleServer.Args(tst).processor(pro));
					plugin.getLogger().info(
							"Listening on port " + String.valueOf(port));
					server.serve();
				} catch (Exception e) {
					Bukkit.getLogger().severe(e.getMessage());
				}
			}

		})).start();

	}

	public void stop() {
		plugin.getLogger().info("Stopping server...");
		server.stop();
		plugin.getLogger().info("Server stopped successfully");
	}

}
