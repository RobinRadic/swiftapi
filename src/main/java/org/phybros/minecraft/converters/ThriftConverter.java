package org.phybros.minecraft.converters;

import org.phybros.thrift.ItemStack;

public class ThriftConverter {

	public static org.bukkit.inventory.ItemStack convertItemStack(ItemStack item) {
		org.bukkit.inventory.ItemStack newItemStack = new org.bukkit.inventory.ItemStack(item.typeId, item.amount);
		
		//TODO: enchantments, lore, displayname
		
		return newItemStack;
	}
	
}
