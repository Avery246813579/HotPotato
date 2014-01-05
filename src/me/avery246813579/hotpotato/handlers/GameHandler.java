package me.avery246813579.hotpotato.handlers;

import me.avery246813579.hotpotato.HotPotato;

public class GameHandler {
	HotPotato plugin;
	
	public GameHandler ( HotPotato plugin ){
		this.plugin = plugin;
	}
	
	public void loadArena(String arena){
		String arenaName = arena.toLowerCase();
		if(!plugin.getFh().getArena().contains(arenaName)){
			plugin.sendConsole(arenaName + " is not a arena.");
			return;
		}
		
		
	}
}
