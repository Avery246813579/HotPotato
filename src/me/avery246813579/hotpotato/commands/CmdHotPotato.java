package me.avery246813579.hotpotato.commands;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.game.GameManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHotPotato implements CommandExecutor{

	HotPotato plugin;
	
	public CmdHotPotato ( HotPotato plugin ){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if(!(sender instanceof Player)){
			plugin.sendConsole("You have to be in game to use this command.");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(CommandLabel.equalsIgnoreCase("hotpotato") || CommandLabel.equalsIgnoreCase("hp")){
			if(args.length == 0){
				//TODO Make help menu.
				for(GameManager gm : plugin.getGames()){
					player.sendMessage(gm.getGame().getArenaName());
				}
				return false;
			}
				
			/** If Command is Create **/
			if(args[0].equalsIgnoreCase("create")){
				
				if(args.length == 1 || args.length >= 3){
					sendArgs(player);
					return true;
				}
				
				if(!player.hasPermission("hotpotato.create")){
					sendPerm(player);
					return true;
				}
					
				plugin.getGc().createArena(player, args[1]);
			}
			
			else if (args[0].equalsIgnoreCase("start")) {
				if(args.length == 1 || args.length >= 3){
					sendArgs(player);
					return true;
				}
				
				if(!player.hasPermission("hotpotato.start")){
					sendPerm(player);
					return true;
				}

	            String arena = args[1].toLowerCase();
	            GameManager gm = plugin.getGameManager(arena);

	            if (plugin.getGames().contains(gm)) {
	                plugin.getServer().getScheduler().cancelTask(gm.getLt().t);
	            	gm.loadArena();
	            } else {
	                plugin.sendMessage(player, ChatColor.RED + "Arena not found.");
	            }

	            return true;
	        }
			
			else if (args[0].equalsIgnoreCase("join")) {
				if(args.length == 1 || args.length >= 3){
					sendArgs(player);
					return true;
				}
				
				if(!player.hasPermission("hotpotato.join")){
					sendPerm(player);
					return true;
				}

	            String arena = args[1].toLowerCase();
	            GameManager gm = plugin.getGameManager(arena);

	            if (plugin.getGames().contains(gm)) {
	                gm.joinGame(player);
	            } else {
	                plugin.sendMessage(player, ChatColor.RED + "Arena not found.");
	            }

	            return true;
	        }
			
			else if (args[0].equalsIgnoreCase("leave")) {
				if(args.length >= 2){
					sendArgs(player);
					return true;
				}
				
				if (plugin.getInArena().contains(player)) {
	                plugin.getPlayersGame(player).removePlayer(player);
	            } else {
	                plugin.sendMessage(player, ChatColor.RED + "You are not in a arena.");
	            }

	            return true;
	        }
			
			/** Update command **/
				
			else if(args[0].equalsIgnoreCase("update")){
					
				if(args.length == 1 || args.length >= 4){
					sendArgs(player);
					return true;
				}
				
				if(args.length == 2){
					plugin.sendMessage(player, "Please add a arena to update.");
					return true;
				}
				
				if(!player.hasPermission("hotpotato.update")){
					sendPerm(player);
					return true;
				}
				
				if(args[1].equalsIgnoreCase("lobby")){
					plugin.getGc().updateLobby(player, args[2]);
				}else if(args[1].equalsIgnoreCase("spawn")){
					plugin.getGc().updateSpawn(player, args[2]);
				}else if(args[1].equalsIgnoreCase("spec")){
					plugin.getGc().updateSpec(player, args[2]);
				}else if(args[1].equalsIgnoreCase("end")){
					plugin.getGc().updateEnd(player, args[2]);
				}else if(args[1].equalsIgnoreCase("world")){
					plugin.getGc().updateWorld(player, args[2]);
				}else{
					plugin.sendMessage(player, ChatColor.RED + "Waypoint not found.");
				}
					
			}
			
			/** If Command is Create **/
			else if(args[0].equalsIgnoreCase("enable")){
				
				if(args.length == 1 || args.length >= 3){
					sendArgs(player);
					return true;
				}
				
				if(!player.hasPermission("hotpotato.enable")){
					sendPerm(player);
					return true;
				}
					
				plugin.getGc().enableArena(player, args[1]);
			}
			
			/** If Command is Create **/
			else if(args[0].equalsIgnoreCase("disable")){
				
				if(args.length == 1 || args.length >= 3){
					sendArgs(player);
					return true;
				}
				
				if(!player.hasPermission("hotpotato.disable")){
					sendPerm(player);
					return true;
				}
					
				plugin.getGc().disableArena(player, args[1]);
			}
		}
		
		return false;
	}
	
	public void sendPerm(Player player){
		plugin.sendMessage(player, ChatColor.RED + "You do not have permission to use this command.");
	}
	
	public void sendArgs(Player player){
		plugin.sendMessage(player, ChatColor.RED + "Incorrect amount of arguments. Please use help menu.");
	}

}
