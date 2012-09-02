package org.phybros.minecraft;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.phybros.thrift.EAuthException;
import org.phybros.thrift.EDataException;
import org.phybros.thrift.Enchantment;
import org.phybros.thrift.ErrorCode;
import org.phybros.thrift.GameMode;
import org.phybros.thrift.Player;
import org.phybros.thrift.PlayerArmor;
import org.phybros.thrift.PlayerInventory;
import org.phybros.thrift.Plugin;
import org.phybros.thrift.SwiftApi;

public class SwiftServer {

	public class SwiftApiHandler implements SwiftApi.Iface {

		/**
		 * Add a Player to the server's whitelist. The player can be offline, or
		 * be a player that has never played on this server before
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param name
		 *            The name of the player to add to the whitelist
		 * 
		 * @return boolean true on success, false on failure
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @throws EDataException
		 *             If the player was not found
		 * 
		 * @throws org.apache.thrift.TException
		 *             If something went wrong with Thrift
		 */
		@Override
		public boolean addToWhitelist(String authString, String name)
				throws EAuthException, EDataException, TException {
			logCall("addToWhitelist");
			authenticate(authString, "addToWhitelist");

			OfflinePlayer offPl = plugin.getServer().getOfflinePlayer(name);

			if (offPl == null) {
				plugin.getLogger().info("Player not found");
				EDataException e = new EDataException();
				e.code = ErrorCode.NOT_FOUND;
				e.message = String.format(
						plugin.getConfig().getString(
								"errorMessages.playerNotFound"), name);
				throw e;
			}

			try {
				offPl.setWhitelisted(true);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		/**
		 * Permanently ban a player from the server by name. The player can be
		 * offline, or have never played on this server before
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param name
		 *            The name of the player to ban
		 * 
		 * @return boolean true on success false on failure
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @throws EDataException
		 *             If the player was not found
		 * 
		 * @throws org.apache.thrift.TException
		 *             If something went wrong with Thrift
		 */
		@Override
		public boolean ban(String authString, String name)
				throws EAuthException, EDataException, TException {
			logCall("ban");
			authenticate(authString, "ban");

			OfflinePlayer offPl = plugin.getServer().getOfflinePlayer(name);

			if (offPl == null) {
				plugin.getLogger().info("Player not found");
				EDataException e = new EDataException();
				e.code = ErrorCode.NOT_FOUND;
				e.message = String.format(
						plugin.getConfig().getString(
								"errorMessages.playerNotFound"), name);
				throw e;
			}

			try {
				offPl.setBanned(true);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		/**
		 * Permanently ban a specific IP from connecting to this server
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param ip
		 *            The IP address to ban
		 * 
		 * @return boolean true on success false on failure
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @throws org.apache.thrift.TException
		 *             If something went wrong with Thrift
		 */
		@Override
		public boolean banIp(String authString, String ip)
				throws EAuthException, TException {
			logCall("banIp");
			authenticate(authString, "banIp");

			try {
				plugin.getServer().banIP(ip);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		/**
		 * Takes "op" (operator) privileges away from a player. If the player is
		 * already deopped, then this method does nothing
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param name
		 *            The player to deop
		 * 
		 * @param notifyPlayer
		 *            Whether or not to tell the player that they were deopped
		 * 
		 * @throws TException
		 *             If something thrifty went wrong
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @throws EDataException
		 *             If the Player was not found
		 * 
		 * @return String the current bukkit version
		 * 
		 */
		@Override
		public boolean deOp(String authString, String name, boolean notifyPlayer)
				throws EAuthException, EDataException, TException {
			logCall("deOp");
			authenticate(authString, "deOp");

			OfflinePlayer offPl = plugin.getServer().getOfflinePlayer(name);

			if (offPl == null) {
				plugin.getLogger().info("Player not found");
				EDataException e = new EDataException();
				e.code = ErrorCode.NOT_FOUND;
				e.message = String.format(
						plugin.getConfig().getString(
								"errorMessages.playerNotFound"), name);
				throw e;
			}

			try {
				boolean wasOp = offPl.isOp();
				offPl.setOp(false);
				if (wasOp && notifyPlayer && offPl.isOnline()) {
					offPl.getPlayer().sendMessage(
							plugin.getConfig().getString("messages.deOp"));
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		/**
		 * Get the current bukkit version
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @throws TException
		 *             If something thrifty went wrong
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @return String the current bukkit version
		 * 
		 */
		@Override
		public String getBukkitVersion(String authString)
				throws EAuthException, TException {
			logCall("getBukkitVersion");
			authenticate(authString, "getBukkitVersion");

			return plugin.getServer().getBukkitVersion();
		}

		/**
		 * Get an offline player. This method will always return an
		 * OfflinePlayer object, even if the requested player has never played
		 * before.
		 * 
		 * The "hasPlayedBefore" property can be checked to determine if the
		 * requested player has ever played on this particular server before
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param name
		 *            The player to get
		 * 
		 * @throws TException
		 *             If something thrifty went wrong
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @throws EDataException
		 *             If the player could not be found
		 * 
		 * @return OfflinePlayer the requested player.
		 * 
		 */
		@Override
		public org.phybros.thrift.OfflinePlayer getOfflinePlayer(
				String authString, String name) throws EAuthException,
				EDataException, TException {
			logCall("getOfflinePlayer");
			authenticate(authString, "getOfflinePlayer");

			OfflinePlayer p = plugin.getServer().getOfflinePlayer(name);

			if (p == null) {
				plugin.getLogger().info("Player not found");
				EDataException e = new EDataException();
				e.code = ErrorCode.NOT_FOUND;
				e.message = String.format(
						plugin.getConfig().getString(
								"errorMessages.offlinePlayerNotFound"), name);
				throw e;
			}

			return convertBukkitOfflinePlayer(p);
		}

		/**
		 * Gets a list of all players who have ever played on this server
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @throws TException
		 *             If something thrifty went wrong
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @return List<OfflinePlayer> A list of all players who have ever
		 *         played on this server
		 * 
		 */
		@Override
		public List<org.phybros.thrift.OfflinePlayer> getOfflinePlayers(
				String authString) throws EAuthException, TException {
			logCall("getOfflinePlayers");
			authenticate(authString, "getOfflinePlayers");

			List<org.phybros.thrift.OfflinePlayer> result = new ArrayList<org.phybros.thrift.OfflinePlayer>();
			OfflinePlayer[] players = plugin.getServer().getOfflinePlayers();

			for (OfflinePlayer p : players) {
				result.add(convertBukkitOfflinePlayer(p));
			}

			return result;
		}

		/**
		 * Get a player by name. Throws an EDataException if the player is
		 * offline, or doesn't exist
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param name
		 *            The name of the player to try and get
		 * 
		 * @throws TException
		 *             If something thrifty went wrong
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @throws EDataException
		 *             If the player is not online, or does not exist
		 * 
		 * @return Player The requested player. If the player could not be
		 *         found, and EDataException is thrown
		 * @see org.phybros.thrift.SwiftApi.Iface#getPlugins(java.lang.String)
		 */
		@Override
		public Player getPlayer(String authString, String name)
				throws EAuthException, EDataException, TException {
			logCall("getPlayer");
			authenticate(authString, "getPlayer");
			org.bukkit.entity.Player p = plugin.getServer().getPlayer(name);

			if (p == null) {
				plugin.getLogger().info("Player not found");
				EDataException e = new EDataException();
				e.code = ErrorCode.NOT_FOUND;
				e.message = String.format(
						plugin.getConfig().getString(
								"errorMessages.playerNotFound"), name);
				throw e;
			}

			return convertBukkitPlayer(p);
		}

		/**
		 * Get all online Players
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @throws TException
		 *             If something thrifty went wrong
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @return List<Player> A list of all currently online players
		 */
		@Override
		public List<Player> getPlayers(String authString)
				throws EAuthException, TException {
			logCall("getPlayers");
			authenticate(authString, "getPlayers");
			List<Player> players = new ArrayList<Player>();

			for (org.bukkit.entity.Player bPlayer : plugin.getServer()
					.getOnlinePlayers()) {
				players.add(convertBukkitPlayer(bPlayer));
			}

			return players;
		}

		/**
		 * Get a loaded server plugin by name
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param name
		 *            The name of the plugin to try and get
		 * 
		 * @throws TException
		 *             If something thrifty went wrong
		 * 
		 * @throws EDataException
		 *             If the requested plugin was not found
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @return Plugin The plugin
		 * 
		 */
		@Override
		public Plugin getPlugin(String authString, String name)
				throws TException, EDataException, EAuthException {
			logCall("getPlugin");
			authenticate(authString, "getPlugin");

			Plugin newPlugin = new Plugin();
			org.bukkit.plugin.Plugin p = plugin.getServer().getPluginManager()
					.getPlugin(name);

			if (p == null) {
				plugin.getLogger().info("Plugin not found");
				EDataException e = new EDataException();
				e.code = ErrorCode.NOT_FOUND;
				e.message = String.format(
						plugin.getConfig().getString(
								"errorMessages.pluginNotFound"), name);
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
		 * @param authString
		 *            The authentication hash
		 * 
		 * @throws TException
		 *             If something thrifty went wrong
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @return List<Plugin> A list of the plugins on the server
		 * 
		 */
		@Override
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

		/**
		 * Get the current server version
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @throws TException
		 *             If something thrifty went wrong
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @return String the version of the server
		 * 
		 */
		@Override
		public String getServerVersion(String authString)
				throws EAuthException, TException {
			logCall("getServerVersion");
			authenticate(authString, "getServerVersion");

			return plugin.getServer().getVersion();
		}

		/**
		 * Kick a currently online Player from the server with a specific custom
		 * message
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param name
		 *            The name of the player to kick
		 * 
		 * @param message
		 *            The message to send to the player after they have been
		 *            kicked
		 * 
		 * @return boolean true on success false on failure
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @throws EDataException
		 *             If the player is not currently online
		 * 
		 * @throws org.apache.thrift.TException
		 *             If something went wrong with Thrift
		 */
		@Override
		public boolean kick(String authString, String name, String message)
				throws EAuthException, EDataException, TException {
			logCall("kick");
			authenticate(authString, "kick");

			org.bukkit.entity.Player player = plugin.getServer()
					.getPlayer(name);

			if (player == null) {
				plugin.getLogger().info("Player not found");
				EDataException e = new EDataException();
				e.code = ErrorCode.NOT_FOUND;
				e.message = String.format(
						plugin.getConfig().getString(
								"errorMessages.playerNotFound"), name);
				throw e;
			}

			try {
				player.kickPlayer(message);
				return true;
			} catch (Exception e) {
				return false;
			}

		}

		/**
		 * Makes a player "op" (operator). If the player is already op, then
		 * this method does nothing
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param name
		 *            The name of the player to op
		 *            
		 * @throws TException
		 *             If something thrifty went wrong
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @throws EDataException
		 *             If the Player was not found
		 * 
		 * @return String the current bukkit version
		 * 
		 */
		@Override
		public boolean op(String authString, String name, boolean notifyPlayer)
				throws EAuthException, EDataException, TException {
			logCall("op");
			authenticate(authString, "op");

			OfflinePlayer offPl = plugin.getServer().getOfflinePlayer(name);

			if (offPl == null) {
				plugin.getLogger().info("Player not found");
				EDataException e = new EDataException();
				e.code = ErrorCode.NOT_FOUND;
				e.message = String.format(
						plugin.getConfig().getString(
								"errorMessages.playerNotFound"), name);
				throw e;
			}

			try {
				boolean wasOp = offPl.isOp();
				offPl.setOp(true);
				if (!wasOp && notifyPlayer && offPl.isOnline()) {
					offPl.getPlayer().sendMessage(
							plugin.getConfig().getString("messages.op"));
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		/**
		 * Sets the gamemode of a player
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param name
		 *            The name of the player
		 * 
		 * @param mode
		 *            The GameMode to set the player to
		 * 
		 * @throws TException
		 *             If something thrifty went wrong
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @throws EDataException
		 *             If the Player was not found
		 * 
		 * @return String the current bukkit version
		 * 
		 */
		@Override
		public boolean setGameMode(String authString, String name, GameMode mode)
				throws EAuthException, EDataException, TException {
			logCall("setGameMode");
			authenticate(authString, "setGameMode");

			org.bukkit.entity.Player player = plugin.getServer()
					.getPlayer(name);

			if (player == null) {
				plugin.getLogger().info("Player not found");
				EDataException e = new EDataException();
				e.code = ErrorCode.NOT_FOUND;
				e.message = String.format(
						plugin.getConfig().getString(
								"errorMessages.playerNotFound"), name);
				throw e;
			}

			try {
				org.bukkit.GameMode m = org.bukkit.GameMode.getByValue(mode
						.getValue());
				player.setGameMode(m);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		/**
		 * Un ban a specific player
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param name
		 *            The name of the player to unban
		 * 
		 * @return boolean true on success false on failure
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @throws EDataException
		 *             If the player was not found
		 * 
		 * @throws org.apache.thrift.TException
		 *             If something went wrong with Thrift
		 */
		@Override
		public boolean unBan(String authString, String name)
				throws EAuthException, EDataException, TException {
			logCall("unBan");
			authenticate(authString, "unBan");
			OfflinePlayer offPl = plugin.getServer().getOfflinePlayer(name);

			if (offPl == null) {
				plugin.getLogger().info("Player not found");
				EDataException e = new EDataException();
				e.code = ErrorCode.NOT_FOUND;
				e.message = String.format(
						plugin.getConfig().getString(
								"errorMessages.playerNotFound"), name);
				throw e;
			}

			try {
				offPl.setBanned(false);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		/**
		 * Un ban a specific IP from connecting to this server
		 * 
		 * @param authString
		 *            The authentication hash
		 * 
		 * @param ip
		 *            The IP to unban
		 * 
		 * @return boolean true on success false on failure
		 * 
		 * @throws EAuthException
		 *             If the method call was not correctly authenticated
		 * 
		 * @throws org.apache.thrift.TException
		 *             If something went wrong with Thrift
		 */
		@Override
		public boolean unBanIp(String authString, String ip)
				throws EAuthException, EDataException, TException {
			logCall("unBanIp");
			authenticate(authString, "unBanIp");

			try {
				plugin.getServer().unbanIP(ip);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		/**
		 * Authenticate a method call.
		 * 
		 * @param authString
		 *            The authentication hash
		 * @param methodName
		 *            The method that is being called.
		 * @throws EAuthException
		 *             This is thrown if the authString is invalid.
		 */
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
				// plugin.getLogger().info("Expecting: " + hash);
				// plugin.getLogger().info("Received:  " + authString);

				if (!hash.equalsIgnoreCase(authString)) {
					plugin.getLogger()
							.info(String
									.format("Invalid Authentication received (method: %s)",
											methodName));
					EAuthException e = new EAuthException();
					e.code = ErrorCode.INVALID_AUTHSTRING;
					e.message = plugin.getConfig().getString(
							"errorMessages.invalidAuthentication");
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

		/**
		 * Converts a bukkit OfflinePlayer into a thrift-compatible version.
		 * 
		 * @param bukkitOfflinePlayer
		 *            The object to convert.
		 * @return org.phybros.thrift.OfflinePlayer The converted object.
		 */
		private org.phybros.thrift.OfflinePlayer convertBukkitOfflinePlayer(
				OfflinePlayer bukkitOfflinePlayer) {
			org.phybros.thrift.OfflinePlayer newPlayer = new org.phybros.thrift.OfflinePlayer();

			newPlayer.firstPlayed = bukkitOfflinePlayer.getFirstPlayed();
			newPlayer.lastPlayed = bukkitOfflinePlayer.getLastPlayed();
			newPlayer.isOp = bukkitOfflinePlayer.isOp();
			newPlayer.isWhitelisted = bukkitOfflinePlayer.isWhitelisted();
			newPlayer.name = bukkitOfflinePlayer.getName();
			newPlayer.hasPlayedBefore = bukkitOfflinePlayer.hasPlayedBefore();

			if (bukkitOfflinePlayer.isOnline()) {
				newPlayer.player = convertBukkitPlayer(bukkitOfflinePlayer
						.getPlayer());
			}

			return newPlayer;
		}

		/**
		 * This method converts an org.bukkit.entity.Player into an
		 * org.phybros.thrift.Player.
		 * 
		 * @param bukkitPlayer
		 *            The Bukkit Player object to convert.
		 * @return org.phybros.thrift.Player The converted Player.
		 */
		private Player convertBukkitPlayer(org.bukkit.entity.Player bukkitPlayer) {
			Player newPlayer = new Player();

			newPlayer.name = bukkitPlayer.getName();
			newPlayer.exhaustion = bukkitPlayer.getExhaustion();
			newPlayer.xpToNextLevel = bukkitPlayer.getExpToLevel();
			newPlayer.levelProgress = bukkitPlayer.getExp();
			newPlayer.firstPlayed = bukkitPlayer.getFirstPlayed();
			newPlayer.foodLevel = bukkitPlayer.getFoodLevel();

			switch (bukkitPlayer.getGameMode()) {
			case SURVIVAL:
				newPlayer.gamemode = GameMode.SURVIVAL;
				break;
			case CREATIVE:
				newPlayer.gamemode = GameMode.CREATIVE;
				break;
			case ADVENTURE:
				newPlayer.gamemode = GameMode.ADVENTURE;
				break;
			default:
				newPlayer.gamemode = GameMode.SURVIVAL;
				break;
			}
			newPlayer.health = bukkitPlayer.getHealth();

			newPlayer.inventory = convertBukkitPlayerInventory(bukkitPlayer
					.getInventory());

			newPlayer.ip = bukkitPlayer.getAddress().getHostName();
			newPlayer.port = bukkitPlayer.getAddress().getPort();
			newPlayer.isBanned = bukkitPlayer.isBanned();
			newPlayer.isInVehicle = bukkitPlayer.isInsideVehicle();
			newPlayer.isOp = bukkitPlayer.isOp();
			// TODO: add bukkitPlayer.isFlying();
			newPlayer.isSleeping = bukkitPlayer.isSleeping();
			newPlayer.isSneaking = bukkitPlayer.isSneaking();
			newPlayer.isSprinting = bukkitPlayer.isSprinting();
			newPlayer.isWhitelisted = bukkitPlayer.isWhitelisted();
			newPlayer.lastPlayed = bukkitPlayer.getLastPlayed();
			newPlayer.level = bukkitPlayer.getLevel();

			return newPlayer;
		}

		/**
		 * Converts a bukkit PlayerInventory into a thrift-compatible version.
		 * 
		 * @param bukkitInventory
		 *            The object to convert.
		 * @return PlayerInventory The converted object.
		 */
		private PlayerInventory convertBukkitPlayerInventory(
				org.bukkit.inventory.PlayerInventory bukkitInventory) {
			// TODO: Finish inventory, armor etc.
			PlayerInventory playerInventory = new PlayerInventory();
			playerInventory.inventory = new ArrayList<org.phybros.thrift.ItemStack>();
			playerInventory.armor = new PlayerArmor();
			playerInventory.itemInHand = new org.phybros.thrift.ItemStack();

			for (ItemStack i : bukkitInventory) {
				org.phybros.thrift.ItemStack newItemStack = new org.phybros.thrift.ItemStack();

				newItemStack.enchantments = new HashMap<Enchantment, Integer>();

				if (i != null) {
					newItemStack.amount = i.getAmount();
					newItemStack.durability = i.getDurability();
					newItemStack.typeId = i.getTypeId();

					for (Map.Entry<org.bukkit.enchantments.Enchantment, Integer> entry : i
							.getEnchantments().entrySet()) {
						newItemStack.enchantments.put(
								Enchantment.findByValue(entry.getValue()),
								entry.getValue());
					}
				}

				// add to the inventory
				playerInventory.inventory.add(newItemStack);
			}

			return playerInventory;
		}

		/**
		 * Log an API call. If the config option logMethodCalls is false, this
		 * method does nothing.
		 * 
		 * @param methodName
		 *            Name of the method that was called.
		 */
		private void logCall(String methodName) {
			if (plugin.getConfig().getBoolean("logMethodCalls")) {
				plugin.getLogger().info(
						"SwiftApi method called: " + methodName + "()");
			}
		}
	}

	private int port;
	private TServer server;

	private SwiftApiPlugin plugin;

	public SwiftServer(SwiftApiPlugin plugin) {
		this.plugin = plugin;
		this.port = plugin.getConfig().getInt("port");

		this.start();
	}

	public void stop() {
		plugin.getLogger().info("Stopping server...");
		server.stop();
		plugin.getLogger().info("Server stopped successfully");
	}

	private void start() {
		(new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub

				try {
					SwiftApiHandler psh = new SwiftApiHandler();
					SwiftApi.Processor<SwiftApi.Iface> pro = new SwiftApi.Processor<SwiftApi.Iface>(
							psh);

					TNonblockingServerTransport tst = new TNonblockingServerSocket(
							port);
					server = new TNonblockingServer(
							new TNonblockingServer.Args(tst).processor(pro));
					plugin.getLogger().info(
							"Listening on port " + String.valueOf(port));
					server.serve();
				} catch (Exception e) {
					Bukkit.getLogger().severe(e.getMessage());
				}
			}

		})).start();

	}

}
