namespace java org.phybros.thrift

// Various codes used for catching errors
enum ErrorCode {
	INVALID_REQUEST = 0,
	INVALID_AUTHSTRING = 1
	NOT_FOUND = 2,
}

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

// This exception is thrown when something data-related went wrong
exception EDataException {
	1: ErrorCode code,
	2: string message,
}

// Thrown when authentication goes awry
exception EAuthException {
	1: ErrorCode code,
	2: string message,
}

struct ItemStack {
	1: i32 amount,
	2: i32 typeId,
	3: i32 durability,
	4: map<Enchantment, i32> enchantments,
}

struct PlayerArmor {
	1: ItemStack helmet,
	2: ItemStack chestplate,
	3: ItemStack leggings,
	4: ItemStack boots,
}

// Represents a player's inventory
struct PlayerInventory {
	1: list<ItemStack> inventory,
	2: ItemStack itemInHand,
	3: PlayerArmor armor,
}

// Represents a Player
struct Player {
	1: string name,
	2: GameMode gamemode,
	3: bool isSleeping,
	4: bool isSneaking,
	5: bool isSprinting,
	6: bool isInVehicle,
	7: i64 xpToNextLevel,
	8: i32 level,
	9: string ip,
	10: bool isOp,
	11: i32 foodLevel,
	12: i32 health,
	13: double exhaustion,
	14: i64 firstPlayed,
	15: i64 lastPlayed,
	16: bool isBanned,
	17: bool isWhitelisted,
	18: PlayerInventory inventory,
	19: double levelProgress,
	20: i32 port,
}

// Represents an offline player
struct OfflinePlayer {
	1: string name,
	2: i64 firstPlayed,
	3: i64 lastPlayed,
	4: bool isOp,
	5: bool isBanned,
	6: bool isWhitelisted,
	7: Player player,	
}

// Represents a server plugin
struct Plugin {
	1: string name,
	2: string description,
	3: string version,
	4: string website,
	5: list<string> authors,
	6: bool enabled,
}

// The main service that provides all the methods
service SwiftApi {
	bool deOp(1:string authString, 2:string name, 3:bool notifyPlayer) throws (1:EAuthException aex, 2:EDataException dex),
	string getBukkitVersion(1:string authString) throws (1:EAuthException aex),
	OfflinePlayer getOfflinePlayer(1:string authString, 2:string name) throws (1:EAuthException aex, 2:EDataException dex),
	list<OfflinePlayer> getOfflinePlayers(1:string authString) throws (1:EAuthException aex),
	Player getPlayer(1:string authString, 2:string name) throws (1:EAuthException aex, 2:EDataException dex),
	list<Player> getPlayers(1:string authString) throws (1:EAuthException aex),
	list<Plugin> getPlugins(1:string authString) throws (1:EAuthException aex),
	Plugin getPlugin(1:string authString, 2:string name) throws (1:EAuthException aex, 2:EDataException dex),
	string getServerVersion(1:string authString) throws (1:EAuthException aex),
	bool op(1:string authString, 2:string name, 3:bool notifyPlayer) throws (1:EAuthException aex, 2:EDataException dex),
	bool setGameMode(1:string authString, 2:string name, 3:GameMode mode) throws (1:EAuthException aex, 2:EDataException dex),
}