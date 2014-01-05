package me.avery246813579.hotpotato.handlers;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.commands.CmdHotPotato;

public class CommandHandler {

	HotPotato plugin;
	
	public CommandHandler ( HotPotato plugin ){
		this.plugin = plugin;
	}
	
	public void init(){
		plugin.getCommand("hotpotato").setExecutor(new CmdHotPotato(plugin));
	}
}
