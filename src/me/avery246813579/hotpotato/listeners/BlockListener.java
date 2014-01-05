package me.avery246813579.hotpotato.listeners;

import me.avery246813579.hotpotato.HotPotato;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

	HotPotato plugin;
	
	public BlockListener ( HotPotato plugin ){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak( BlockBreakEvent event ){
		if(plugin.getInArena().contains(event.getPlayer())){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace( BlockPlaceEvent event ){
		if(plugin.getInArena().contains(event.getPlayer())){
			event.setCancelled(true);
		}
	}
}
