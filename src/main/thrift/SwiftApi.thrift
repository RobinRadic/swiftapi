namespace java nl.radic.swiftapi.thrift

/**
 * Various codes used for catching errors
 */
enum ErrorCode {
/**
 * If a parameter was invalid
 */
	INVALID_REQUEST = 0,
/**
 * Authentication failed
 */
	INVALID_AUTHSTRING = 1,
/**
 * Requested data could not be found
 */
	NOT_FOUND = 2,
/**
 *	Something went wrong during a download operation
 */
 	DOWNLOAD_ERROR = 3,
/**
 *	Something went wrong during a file operation
 */
 	FILE_ERROR = 4,
/**
 *	Could not read a file
 */
 	NO_READ = 5,
}

/**
 * This exception is thrown when something data-related went wrong
 */
exception EDataException {
/**
 * Detailed reason for the exception
 */
	1: ErrorCode code,
/**
 * A message that describes the exception
 */
	2: string errorMessage,
}

/**
 * Thrown when authentication fails, this is thrown
 */
exception EAuthException {
/**
 * Detailed reason for the exception
 */
	1: ErrorCode code,
/**
 * A message that describes the exception
 */
	2: string errorMessage,
}

/**
 * Game difficulties
 */
enum Difficulty {
/**
 * Players regain health over time, hostile mobs don't spawn,
 * the hunger bar does not deplete.
 */
	PEACEFUL = 0,
/**
 * Hostile mobs spawn, enemies deal less damage than on
 * normal difficulty, the hunger bar does deplete and starving
 * deals up to 5 hearts of damage.
 */
	EASY = 1,
/**
 * Hostile mobs spawn, enemies deal normal amounts of damage,
 * the hunger bar does deplete and starving deals up to 9.5
 * hearts of damage.
 */
	NORMAL = 2,
/**
 * Hostile mobs spawn, enemies deal greater damage than on
 * normal difficulty, the hunger bar does deplete and starving
 * can kill players.
 */
	HARD = 3,
}

/**
 * Represents various map environment types that a world may be
 */
enum Environment {
	NETHER = 0,
	NORMAL = 1,
	THE_END = 2,
}

/**
 * Valid game modes
 */
enum GameMode {
/**
 * Survival Mode
 */
	SURVIVAL = 0,
/**
 * Creative Mode
 */
	CREATIVE = 1,
/**
 * Adventure Mode
 */
	ADVENTURE = 2,

	SPECTATOR = 3
}

/**
 * All enchantments
 */
enum Enchantment {
/**
 * Protection
 */
	PROTECTION_ENVIRONMENTAL = 0,
/**
 * Fire Protection
 */
    PROTECTION_FIRE = 1,
/**
 * Feather Falling
 */
    PROTECTION_FALL = 2,
/**
 * Blast Protection
 */
    PROTECTION_EXPLOSIONS = 3,
/**
 * Projectile Protection
 */
    PROTECTION_PROJECTILE = 4,
/**
 * Respiration
 */
    OXYGEN = 5,
/**
 * Aqua Afiinity
 */
    WATER_WORKER = 6,
/**
 * Sharpness
 */
    DAMAGE_ALL = 16,
/**
 * Smite
 */
    DAMAGE_UNDEAD = 17,
/**
 * Bane of Arthropods
 */
    DAMAGE_ARTHROPODS = 18,
/**
 * Knockback
 */
    KNOCKBACK = 19,
/**
 * Fire Aspect
 */
    FIRE_ASPECT = 20,
/**
 * Looting
 */
    LOOT_BONUS_MOBS = 21,
/**
 * Efficiency
 */
    DIG_SPEED = 32,
/**
 * Silk Touch
 */
    SILK_TOUCH = 33,
/**
 * Unbreaking
 */
    DURABILITY = 34,
/**
 * Fortune
 */
    LOOT_BONUS_BLOCKS = 35,
/**
 * Power
 */
    ARROW_DAMAGE = 48,
/**
 * Punch
 */
    ARROW_KNOCKBACK = 49,
/**
 * Flame
 */
    ARROW_FIRE = 50,
/**
 * Infinity
 */
    ARROW_INFINITE = 51,
}

/**
 * A line from the console
 */
struct ConsoleLine {
	/**
	 * A unix-style timestamp (in milliseconds)
	 */
	1:i64 timestamp,
	/**
	 * The actual message from the console
	 */
	2:string message,
	/**
	 * The log level (INFO, WARN, SEVERE etc.)
	 */
	3:string level,
}

/**
 * An object that represents a location in the game world
 */
struct Location {
/**
 * The X coordinate
 */
	1: double x,
/**
 * The Y coordinate (height)
 */
	2: double y,
/**
 * The Z coordinate
 */
	3: double z,
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
/**
 * The lore associated with this item
 * @since 1.5
 */
 	5: list<string> lore,
/**
 * The display name of the item
 * @since 1.5
 */
 	6: string displayName,
/**
 * The data value associated with this item
 * @since 1.5
 */
 	7: i32 data,
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
 * DEPRECATED: use Player.healthDouble instead.
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
/**
 * The current location of the player
 */
	21: Location location,
/**
 * The health of the player. Use this instead of Player.health.
 */
	22: double healthDouble,
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


service SwiftApi {

/**
 * Broadcasts a message to all players on the server
 *
 * @param authString
 *            The authentication hash
 *
 * @param message
 *            The message to send
 *
 * @return boolean true on success false on serious failure
 *
 * @throws EAuthException
 *             If the method call was not correctly authenticated
 *
 * @throws org.apache.thrift.TException
 *             If something went wrong with Thrift
 */
	bool announce(1:string authString, 2:string message)
	throws (1:EAuthException aex),
}