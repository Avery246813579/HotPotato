package me.avery246813579.hotpotato.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.avery246813579.hotpotato.HotPotato;

public class GameCreator {

	HotPotato plugin;

	/** We need: Lobby, Spawn, Spec, End **/
	
	public GameCreator ( HotPotato plugin ){
		this.plugin = plugin;
	}
	
	public void createArena(Player player, String arena){
		String arenaName = arena.toLowerCase();
		if(plugin.getFh().getArena().contains(arenaName)){
			plugin.sendMessage(player, "Arena already created.");
			return;
		}
		
		plugin.getFh().getArena().createSection(arenaName);
		
		plugin.getFh().saveArena();
		
		plugin.sendMessage(player, "You have created the arena named " + arenaName + "!");
	}
	
	public void enableArena(Player player, String arena){
		String arenaName = arena.toLowerCase();
		if(!plugin.getFh().getArena().contains(arenaName)){
			plugin.sendMessage(player, "Arena not found.");
			return;
		}

		/** TODO Check if points are set **/
		List<String> enabled = new ArrayList<String>();
		
		if(plugin.getConfig().contains("enabled")){
			enabled = plugin.getConfig().getStringList("enabled");
			
		}
		
		enabled.add(arenaName);
		
		
		plugin.getConfig().set("enabled", enabled);
		plugin.saveConfig();
		
        Game game = new Game(plugin, arenaName);
        game.loadGame();
        GameManager gm = new GameManager(plugin, game);
        gm.init();
		
        plugin.getGames().add(gm);
        
		plugin.sendMessage(player, "You have enabled " + arenaName + "!");
	}
	
	public void disableArena(Player player, String arena){
		String arenaName = arena.toLowerCase();
		if(!plugin.getFh().getArena().contains(arenaName)){
			plugin.sendMessage(player, "Arena not found.");
			return;
		}

		if(plugin.getConfig().contains("enabled")){
			if(plugin.getConfig().getStringList("enabled").contains(arenaName)){
				plugin.getConfig().getStringList("enabled").remove(arenaName);
				plugin.sendMessage(player, "You have disabled " + arenaName + "!");
				return;
			}
		}
		
		plugin.getGames().remove(plugin.getGameManager(arenaName));
		
		plugin.saveConfig();
		
		plugin.sendMessage(player, arenaName + " was never enabled!");
	}
	
	public void updateLobby(Player player, String arena){
		String arenaName = arena.toLowerCase();
		if(!plugin.getFh().getArena().contains(arenaName)){
			plugin.sendMessage(player, "Arena not found.");
			return;
		}
		
		Location l = player.getLocation();
		
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("lobby.x", l.getBlockX());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("lobby.y", l.getBlockY());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("lobby.z", l.getBlockZ());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("lobby.yaw", l.getYaw());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("lobby.pitch", l.getPitch());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("lobby.world", l.getWorld().getName());
		
		plugin.getFh().saveArena();
		
		plugin.sendMessage(player, "You have updated the lobby of " + arenaName + "!");
	}
	
	//TODO Add checking if the world is the same as the arena world
	
	public void updateSpawn(Player player, String arena){
		String arenaName = arena.toLowerCase();
		if(!plugin.getFh().getArena().contains(arenaName)){
			plugin.sendMessage(player, "Arena not found.");
			return;
		}
		
		Location l = player.getLocation();
		
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("spawn.x", l.getBlockX());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("spawn.y", l.getBlockY());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("spawn.z", l.getBlockZ());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("spawn.yaw", l.getYaw());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("spawn.pitch", l.getPitch());
		
		plugin.getFh().saveArena();
		
		plugin.sendMessage(player, "You have updated the spawn of " + arenaName + "!");
	}
	
	public void updateSpec(Player player, String arena){
		String arenaName = arena.toLowerCase();
		if(!plugin.getFh().getArena().contains(arenaName)){
			plugin.sendMessage(player, "Arena not found.");
			return;
		}
		
		Location l = player.getLocation();
		
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("spec.x", l.getBlockX());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("spec.y", l.getBlockY());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("spec.z", l.getBlockZ());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("spec.yaw", l.getYaw());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("spec.pitch", l.getPitch());
		
		plugin.getFh().saveArena();
		
		plugin.sendMessage(player, "You have updated the spec of " + arenaName + "!");
	}
	
	public void updateEnd(Player player, String arena){
		String arenaName = arena.toLowerCase();
		if(!plugin.getFh().getArena().contains(arenaName)){
			plugin.sendMessage(player, "Arena not found.");
			return;
		}
		
		Location l = player.getLocation();
		
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("end.x", l.getBlockX());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("end.y", l.getBlockY());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("end.z", l.getBlockZ());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("end.yaw", l.getYaw());
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("end.pitch", l.getPitch());
		
		plugin.getFh().saveArena();
		
		plugin.sendMessage(player, "You have updated the end of " + arenaName + "!");
	}
	
	public void updateWorld(Player player, String arena){
		String arenaName = arena.toLowerCase();
		if(!plugin.getFh().getArena().contains(arenaName)){
			plugin.sendMessage(player, "Arena not found.");
			return;
		}
		
		Location l = player.getLocation();
		
		plugin.getFh().getArena().getConfigurationSection(arenaName).set("arenaworld", l.getWorld().getName());
		
		plugin.getFh().saveArena();
		
		plugin.sendMessage(player, "You have updated the arena world of " + arenaName + "!");
	}
}