package org.phybros.minecraft;

import java.io.File;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.phybros.thrift.Difficulty;
import org.phybros.thrift.Enchantment;
import org.phybros.thrift.Environment;
import org.phybros.thrift.GameMode;
import org.phybros.thrift.Location;
import org.phybros.thrift.Player;
import org.phybros.thrift.PlayerArmor;
import org.phybros.thrift.PlayerInventory;
import org.phybros.thrift.Plugin;
import org.phybros.thrift.World;

public class BukkitConverter {
	/**
	 * Converts a bukkit Location object into a thrift Location object
	 * 
	 * @param bukkitLocation
	 *            The location object to convert
	 * @return Location a thrift-compatible location object
	 */
	public static Location convertBukkitLocation(
			org.bukkit.Location bukkitLocation) {
		Location newLocation = new Location();

		newLocation.x = bukkitLocation.getX();
		newLocation.y = bukkitLocation.getY();
		newLocation.z = bukkitLocation.getZ();

		newLocation.pitch = bukkitLocation.getPitch();
		newLocation.yaw = bukkitLocation.getYaw();

		return newLocation;
	}

	/**
	 * Converts a bukkit OfflinePlayer into a thrift-compatible version.
	 * 
	 * @param bukkitOfflinePlayer
	 *            The object to convert.
	 * @return org.phybros.thrift.OfflinePlayer The converted object.
	 */
	public static org.phybros.thrift.OfflinePlayer convertBukkitOfflinePlayer(
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
	public static Player convertBukkitPlayer(
			org.bukkit.entity.Player bukkitPlayer) {
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

		newPlayer.location = convertBukkitLocation(bukkitPlayer.getLocation());

		return newPlayer;
	}

	/**
	 * Converts a bukkit PlayerInventory into a thrift-compatible version.
	 * 
	 * @param bukkitInventory
	 *            The object to convert.
	 * @return PlayerInventory The converted object.
	 */
	public static PlayerInventory convertBukkitPlayerInventory(
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
	 * Converts a bukkit world object into a thrift-compatible world object
	 * 
	 * @param bukkitWorld
	 *            The world to convert
	 * @return World the converted world
	 */
	public static World convertBukkitWorld(org.bukkit.World bukkitWorld) {
		World newWorld = new World();

		newWorld.allowAnimals = bukkitWorld.getAllowAnimals();
		newWorld.allowMonsters = bukkitWorld.getAllowMonsters();
		newWorld.canGenerateStructures = bukkitWorld.canGenerateStructures();
		newWorld.difficulty = Difficulty.findByValue(bukkitWorld
				.getDifficulty().getValue());
		// add 1 to the value to get the right enum value (thrift doesnt allow
		// negative numbers)
		newWorld.environment = Environment.findByValue(bukkitWorld
				.getEnvironment().getId() + 1);
		newWorld.fullTime = bukkitWorld.getFullTime();
		newWorld.hasStorm = bukkitWorld.hasStorm();
		newWorld.isPvp = bukkitWorld.getPVP();
		newWorld.isThundering = bukkitWorld.isThundering();
		newWorld.seed = bukkitWorld.getSeed();
		newWorld.time = bukkitWorld.getTime();
		newWorld.weatherDuration = bukkitWorld.getWeatherDuration();
		newWorld.name = bukkitWorld.getName();

		return newWorld;
	}

	public static Plugin convertBukkitPlugin(
			org.bukkit.plugin.Plugin bukkitPlugin) {

		Plugin newPlugin = new Plugin();
		
		newPlugin.authors = bukkitPlugin.getDescription().getAuthors();
		newPlugin.description = bukkitPlugin.getDescription().getDescription();
		newPlugin.enabled = bukkitPlugin.isEnabled();
		newPlugin.name = bukkitPlugin.getDescription().getFullName();
		newPlugin.version = bukkitPlugin.getDescription().getVersion();
		newPlugin.website = bukkitPlugin.getDescription().getWebsite();
		
		try {
			ProtectionDomain p = bukkitPlugin.getClass().getProtectionDomain();
			File f = new File(p.getCodeSource().getLocation().toString());
			newPlugin.fileName = f.getName();
		} catch(Exception e) {
			
		}
		
		return newPlugin;
		
	}
}
