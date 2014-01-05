package me.avery246813579.hotpotato.handlers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.avery246813579.hotpotato.HotPotato;

public class FileHandler {
	
	HotPotato plugin;
	
	public FileHandler ( HotPotato plugin ){
		this.plugin = plugin;
	}
	
	private FileConfiguration arena = null;
	private File arenaFile = null;
	
	public void reloadArena() {
	    if (arenaFile == null) {
	    	arenaFile = new File(plugin.getDataFolder(), "arena.yml");
	    }
	    
	    arena = YamlConfiguration.loadConfiguration(arenaFile);
	 
	    InputStream defConfigStream = plugin.getResource("arena.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        arena.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getArena() {
	    if (arena == null) {
	        reloadArena();
	    }
	    return arena;
	}
	
	public void saveArena() {
	    if (arena == null || arenaFile == null) {
	        return;
	    }
	    try {
	        getArena().save(arenaFile);
	    } catch (IOException ex) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + arenaFile, ex);
	    }
	}
	
	public void saveDefaultArena() {
	    if (arenaFile == null) {
	    	arenaFile = new File(plugin.getDataFolder(), "arena.yml");
	    }
	    if (!arenaFile.exists()) {            
	         plugin.saveResource("arena.yml", false);
	     }
	}
}
