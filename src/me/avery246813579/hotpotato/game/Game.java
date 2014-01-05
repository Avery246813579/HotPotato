package me.avery246813579.hotpotato.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import me.avery246813579.hotpotato.HotPotato;

public class Game {

	HotPotato plugin;
	
	/** Arena info **/
	private String arenaName;
	
	/** Arena Locations **/
	private Location spawn;
	private Location lobby;
	private Location spec;
	private Location end;
	
	/** Arena Worlds **/
	private World lobbyWorld;
	private World arenaWorld;

	public Game ( HotPotato plugin, String arena ){
		this.plugin = plugin;
		setArenaName(arena.toLowerCase());
	}
	
	public void loadGame(){
		World lobbyWorld = plugin.getServer().getWorld(plugin.getFh().getArena().getConfigurationSection(arenaName).getString("lobby.world"));
		
		if(lobbyWorld != null){
			this.lobbyWorld = lobbyWorld;
			
			String y = plugin.getFh().getArena().getConfigurationSection(arenaName).getString("lobby.yaw");
			String p = plugin.getFh().getArena().getConfigurationSection(arenaName).getString("lobby.pitch");

			float yaw = (float)Float.parseFloat(y);
			float pitch = (float)Float.parseFloat(p);
			
			try {
				lobby = new Location(lobbyWorld, plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("lobby.x"), plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("lobby.y"), plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("lobby.z"), yaw, pitch).clone().add(0.5D, 0.5D, 0.5D);
				plugin.sendConsole("Lobby has been created.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		World arenaWorld = Bukkit.getWorld(plugin.getFh().getArena().getConfigurationSection(arenaName).getString("arenaworld"));
		
		/** Spawn location **/
		if(arenaWorld != null){
			this.arenaWorld = arenaWorld;

			String y = plugin.getFh().getArena().getConfigurationSection(arenaName).getString("spawn.yaw");
			String p = plugin.getFh().getArena().getConfigurationSection(arenaName).getString("spawn.pitch");

			float yaw = Float.parseFloat(y);
			float pitch = Float.parseFloat(p);
			
			spawn = new Location(arenaWorld, plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("spawn.x"), plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("spawn.y"), plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("spawn.z"), yaw, pitch).clone().add(0.5D, 0.5D, 0.5D);
		}
		
		/** Spectate Location **/
		if(arenaWorld != null){
			String y = plugin.getFh().getArena().getConfigurationSection(arenaName).getString("spec.yaw");
			String p = plugin.getFh().getArena().getConfigurationSection(arenaName).getString("spec.pitch");

			float yaw = Float.parseFloat(y);
			float pitch = Float.parseFloat(p);
			
			spec = new Location(arenaWorld, plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("spec.x"), plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("spec.y"), plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("spec.z"), yaw, pitch).clone().add(0.5D, 0.5D, 0.5D);
		}
		
		/** End Location **/
		if(arenaWorld != null){
			String y = plugin.getFh().getArena().getConfigurationSection(arenaName).getString("end.yaw");
			String p = plugin.getFh().getArena().getConfigurationSection(arenaName).getString("end.pitch");

			float yaw = Float.parseFloat(y);
			float pitch = Float.parseFloat(p);
			
			end = new Location(arenaWorld, plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("end.x"), plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("end.y"), plugin.getFh().getArena().getConfigurationSection(arenaName).getInt("end.z"), yaw, pitch).clone().add(0.5D, 0.5D, 0.5D);
		}
	}

	/********************************************
	 * 
	 * 				Getter & Setters
	 * 
	 *******************************************/
	
	public String getArenaName() {
		return arenaName;
	}

	public void setArenaName(String arenaName) {
		this.arenaName = arenaName;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	public Location getSpec() {
		return spec;
	}

	public void setSpec(Location spec) {
		this.spec = spec;
	}

	public Location getEnd() {
		return end;
	}

	public void setEnd(Location end) {
		this.end = end;
	}

	public World getLobbyWorld() {
		return lobbyWorld;
	}

	public void setLobbyWorld(World lobbyWorld) {
		this.lobbyWorld = lobbyWorld;
	}

	public World getArenaWorld() {
		return arenaWorld;
	}

	public void setArenaWorld(World arenaWorld) {
		this.arenaWorld = arenaWorld;
	}
}
