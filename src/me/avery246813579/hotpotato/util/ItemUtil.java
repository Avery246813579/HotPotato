package me.avery246813579.hotpotato.util;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
	public static ItemStack potato(){
		ItemStack itemStack = new ItemStack(Material.POTATO_ITEM);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.YELLOW + "Hot Potato");
		itemMeta.setLore(new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "Left click a player", "to lose the potato.")));
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
}
