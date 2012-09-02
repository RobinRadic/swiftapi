namespace java org.phybros.thrift

include "Errors.thrift"

enum GameMode {
	SURVIVAL = 0,
	CREATIVE = 1,
	ADVENTURE = 2,
}

enum Enchantment {
	PROTECTION_ENVIRONMENTAL = 0,
    PROTECTION_FIRE = 1,
    PROTECTION_FALL = 2,
    PROTECTION_EXPLOSIONS = 3,
    PROTECTION_PROJECTILE = 4,
    OXYGEN = 5,
    WATER_WORKER = 6,
    DAMAGE_ALL = 16,
    DAMAGE_UNDEAD = 17,
    DAMAGE_ARTHROPODS = 18,
    KNOCKBACK = 19,
    FIRE_ASPECT = 20,
    LOOT_BONUS_MOBS = 21,
    DIG_SPEED = 32,
    SILK_TOUCH = 33,
    DURABILITY = 34,
    LOOT_BONUS_BLOCKS = 35,
    ARROW_DAMAGE = 48,
    ARROW_KNOCKBACK = 49,
    ARROW_FIRE = 50,
    ARROW_INFINITE = 51,
}

/**
 * A stack of items
 */
struct ItemStack {
/**
 * How many of this item are currently in the stack
 */
	1: i32 amount,
/**
 * The type ID of the item
 */
	2: i32 typeId,
/**
 * The durability of the item
 */
	3: i32 durability,
/**
 * The current enchantments in effect on this item
 */
	4: map<Enchantment, i32> enchantments,
}

/**
 * Represents the armor that the player is wearing
 */
struct PlayerArmor {
/**
 * The item in the player's helmet armor slot
 */
	1: ItemStack helmet,
/**
 * The item in the player's chestplate armor slot
 */
	2: ItemStack chestplate,
/**
 * The item in the player's leggings armor slot
 */
	3: ItemStack leggings,
/**
 * The item in the player's boots armor slot
 */
	4: ItemStack boots,
}

/**
 * Represents a player's inventory
 */
struct PlayerInventory {
/**
 * The items in the Player's inventory
 */
	1: list<ItemStack> inventory,
/**
 * The item(s) that the player is currently holding
 */
	2: ItemStack itemInHand,
/**
 * The armor that the player is currently wearing
 */
	3: PlayerArmor armor,
}

/**
 * Represents a player on the server
 */
struct Player {
/**
 * The player's name
 */
	1: string name,
/**
 * The player's current gamemode
 */
	2: GameMode gamemode,
/**
 * Whether the player is sleeping or not
 */
	3: bool isSleeping,
/**
 * Whether the player is sneaking or not
 */
	4: bool isSneaking,
/**
 * Whether the player is sprinting or not
 */
	5: bool isSprinting,
/**
 * Whether the player is currently in a vehicle (minecart, boat, pig etc.)
 */
	6: bool isInVehicle,
/**
 * How many XP the Player needs to reach the next Level
 */
	7: i64 xpToNextLevel,
/**
 * The current Level of the player
 */
	8: i32 level,
/**
 * The IP address the player's client is currently connected on
 */
	9: string ip,
/**
 * Whether the Player is opped or not
 */
	10: bool isOp,
/**
 * How hungry the Player is in halves of chicken legs (max possible is 20)
 */
	11: i32 foodLevel,
/**
 * How much health the Player has in halves of hearts (20 max)
 */
	12: i32 health,
/**
 * How tired the player is (percentage value)
 */
	13: double exhaustion,
/**
 * The time/date the the player first joined (UNIX-timestamp style)
 */
	14: i64 firstPlayed,
/**
 * The time/date the the player last joined (UNIX-timestamp style)
 */
	15: i64 lastPlayed,
/**
 * If the player is currently banned from the server
 */
	16: bool isBanned,
/**
 * If the player is currently on the server's whitelist
 */
	17: bool isWhitelisted,
/**
 * The current inventory of the player
 */
	18: PlayerInventory inventory,
/**
 * Percentage progress to the next level
 */
	19: double levelProgress,
/**
 * The port number that the player's client is currently connected on
 */
	20: i32 port,
}

/**
 * Represents an offline player (or one that has never joined this server)
 */
struct OfflinePlayer {
/**
 * The player's name
 */
	1: string name,
/**
 * The time/date the the player first joined (UNIX-timestamp style). 0 if never.
 */
	2: i64 firstPlayed,
/**
 * The time/date the the player last joined (UNIX-timestamp style) 0 if never.
 */
	3: i64 lastPlayed,
/**
 * If the player is currently opped
 */
	4: bool isOp,
/**
 * If the player is currently banned from the server
 */
	5: bool isBanned,
/**
 * If the player is currently on the server's whitelist
 */
	6: bool isWhitelisted,
/**
 * If the player is online, more information is held in this Player object
 */
	7: Player player,
/**
 * If the player has joined the server at least once before now
 */
	8: bool hasPlayedBefore,
}

/**
 * Represents a server plugin. All the values that are populated into this 
 * object are  taken from the Plugin's server-side configuration file.
 */
struct Plugin {
/**
 * The full name of the plugin
 */
	1: string name,
/**
 * The description of the plugin
 */
	2: string description,
/**
 * The installed version of the plugin
 */
	3: string version,
/**
 * The website of the plugin
 */
	4: string website,
/**
 * The authors of the plugin
 */
	5: list<string> authors,
/**
 * Whether or not the plugin is enabled
 */
	6: bool enabled,
}

service SwiftApi {
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @throws Errors.EDataException
 *             If the player was not found
 * 
 * @throws org.apache.thrift.TException
 *             If something went wrong with Thrift
 */
	bool addToWhitelist(1:string authString,
						2:string name)
	throws (1:Errors.EAuthException aex,
			2:Errors.EDataException dex),
	
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @throws Errors.EDataException
 *             If the player was not found
 * 
 * @throws org.apache.thrift.TException
 *             If something went wrong with Thrift
 */
	bool ban(1:string authString, 
			 2:string name) 
 	throws (1:Errors.EAuthException aex, 
 			2:Errors.EDataException dex),
	
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @throws org.apache.thrift.TException
 *             If something went wrong with Thrift
 */
	bool banIp(1:string authString, 2:string ip) throws (1:Errors.EAuthException aex),
	
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @throws Errors.EDataException
 *             If the Player was not found
 * 
 * @return String the current bukkit version
 * 
 */
	bool deOp(1:string authString,
			  2:string name, 3:bool notifyPlayer) 
	throws (1:Errors.EAuthException aex, 
			2:Errors.EDataException dex),
	
/**
 * Get the current bukkit version
 * 
 * @param authString
 *            The authentication hash
 * 
 * @throws TException
 *             If something thrifty went wrong
 * 
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @return String the current bukkit version
 * 
 */
	string getBukkitVersion(1:string authString) throws (1:Errors.EAuthException aex),
	
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @throws Errors.EDataException
 *             If the player could not be found
 * 
 * @return OfflinePlayer the requested player.
 * 
 */
	OfflinePlayer getOfflinePlayer(1:string authString, 
								   2:string name) 
	throws (1:Errors.EAuthException aex, 
			2:Errors.EDataException dex),
	
/**
 * Gets a list of all players who have ever played on this server
 * 
 * @param authString
 *            The authentication hash
 * 
 * @throws TException
 *             If something thrifty went wrong
 * 
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @return List<OfflinePlayer> A list of all players who have ever
 *         played on this server
 * 
 */
	list<OfflinePlayer> getOfflinePlayers(1:string authString) 
	throws (1:Errors.EAuthException aex),
	
/**
 * Get a player by name. Throws an Errors.EDataException if the player is
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @throws Errors.EDataException
 *             If the player is not online, or does not exist
 * 
 * @return Player The requested player. If the player could not be
 *         found, and Errors.EDataException is thrown
 * @see org.phybros.thrift.SwiftApi.Iface#getPlugins(java.lang.String)
 */
	Player getPlayer(1:string authString, 
					 2:string name) 
	throws (1:Errors.EAuthException aex, 
 			2:Errors.EDataException dex),
	
/**
 * Get all online Players
 * 
 * @param authString
 *            The authentication hash
 * 
 * @throws TException
 *             If something thrifty went wrong
 * 
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @return List<Player> A list of all currently online players
 */
	list<Player> getPlayers(1:string authString) throws (1:Errors.EAuthException aex),
	
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
 * @throws Errors.EDataException
 *             If the requested plugin was not found
 * 
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @return Plugin The plugin
 * 
 */
	Plugin getPlugin(1:string authString, 
					 2:string name) 
	throws (1:Errors.EAuthException aex,
			2:Errors.EDataException dex),
	
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @return List<Plugin> A list of the plugins on the server
 * 
 */
	list<Plugin> getPlugins(1:string authString) throws (1:Errors.EAuthException aex),
	
/**
 * Get the current server version
 * 
 * @param authString
 *            The authentication hash
 * 
 * @throws TException
 *             If something thrifty went wrong
 * 
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @return String the version of the server
 * 
 */
	string getServerVersion(1:string authString) throws (1:Errors.EAuthException aex),
	
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @throws Errors.EDataException
 *             If the player is not currently online
 * 
 * @throws org.apache.thrift.TException
 *             If something went wrong with Thrift
 */
	bool kick(1:string authString, 
			  2:string name, 
			  3:string message) 
	throws (1:Errors.EAuthException aex, 
			2:Errors.EDataException dex),
	
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @throws Errors.EDataException
 *             If the Player was not found
 * 
 * @return String the current bukkit version
 * 
 */
	bool op(1:string authString, 
			2:string name, 3:bool notifyPlayer) 
	throws (1:Errors.EAuthException aex, 
			2:Errors.EDataException dex),
	
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @throws Errors.EDataException
 *             If the Player was not found
 * 
 * @return String the current bukkit version
 * 
 */
	bool setGameMode(1:string authString, 
					 2:string name, 
					 3:GameMode mode) 
	throws (1:Errors.EAuthException aex, 
			2:Errors.EDataException dex),
	
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @throws Errors.EDataException
 *             If the player was not found
 * 
 * @throws org.apache.thrift.TException
 *             If something went wrong with Thrift
 */
	bool unBan(1:string authString, 
			   2:string name) 
	throws (1:Errors.EAuthException aex, 
			2:Errors.EDataException dex),
	
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
 * @throws Errors.EAuthException
 *             If the method call was not correctly authenticated
 * 
 * @throws org.apache.thrift.TException
 *             If something went wrong with Thrift
 */
	bool unBanIp(1:string authString, 
				 2:string ip) 
	throws (1:Errors.EAuthException aex, 
			2:Errors.EDataException dex),
}