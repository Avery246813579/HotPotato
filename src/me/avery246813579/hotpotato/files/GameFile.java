package me.avery246813579.hotpotato.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import me.avery246813579.hotpotato.HotPotato;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class GameFile {
	private FileConfiguration configFile = null;
	private String fileName = "Default";
	private File file = null;

	public GameFile(String fileName) {
		this.fileName = fileName + ".yml";
		
		getFile().options().copyDefaults(true);
		saveFile();
	}
	
	public void forceReload(){
		if(configFile == null){
			reloadFile();
		}
		InputStream defConfigSteam = HotPotato.getPlugin().getResource(fileName);
		if (defConfigSteam != null) {
			@SuppressWarnings("deprecation")
			YamlConfiguration cf = YamlConfiguration.loadConfiguration(defConfigSteam);

			for(String string : cf.getConfigurationSection("").getKeys(false)){
				Bukkit.getLogger().info("Contains: " + string);
				if(!configFile.contains(string)){
					Bukkit.getLogger().info("NO Contains: " + string);
					configFile.set(string, cf.get(string));
					saveFile();
				}
			}
		}
	}

	public void reloadFile() {
		if (file == null) {
			file = new File(HotPotato.getPlugin().getDataFolder(), fileName);
		}

		configFile = YamlConfiguration.loadConfiguration(file);

		InputStream defConfigSteam = HotPotato.getPlugin().getResource(fileName);
		if (defConfigSteam != null) {
			@SuppressWarnings("deprecation")
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigSteam);
			defConfig.setDefaults(defConfig);
		}
	}
	
	public FileConfiguration getFile(){
		if(configFile == null){
			reloadFile();
		}
		
		return configFile;
	}
	
	public void saveFile(){
		if(file == null || configFile == null){
			return;
		}
		
		try{
			getFile().save(file);
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public void saveDefaultFile(){
		if(file == null){
			file = new File(HotPotato.getPlugin().getDataFolder(), fileName);
		}
		
		if(!file.exists()){
			HotPotato.getPlugin().saveResource(fileName, false);
		}
	}
}
