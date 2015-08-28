package me.avery246813579.hotpotato.game;

import java.util.ArrayList;
import java.util.List;

import me.avery246813579.hotpotato.files.FileHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class Game {
	private GameManager gm;
	private String mapName, mapCreator;
	private List<Location> spawns = new ArrayList<Location>();
	private Location mapCenter, specSpawn, lobbyLocation;
	private int currentSpawn;

	public Game(GameManager gm, String mapLocation) {
		this.gm = gm;

		ConfigurationSection configSection = FileHandler.DataFile.getFile().getConfigurationSection("arenas." + mapLocation);
		mapName = configSection.getString("mapName");
		mapCreator = configSection.getString("mapCreator");

		for (String locations : configSection.getStringList("mapSpawns")) {
			spawns.add(parseLocation(locations));
		}

		mapCenter = parseLocation(configSection.getString("mapCenter"));
		specSpawn = parseLocation(configSection.getString("specSpawn"));
		lobbyLocation = parseLocation(configSection.getString("lobbyLocation"));
	}

	public Location parseLocation(String location) {
		String[] splits = location.split(" ");

		World world = Bukkit.getWorld(splits[0]);
		if (world == null) {
			gm.setGameState(GameState.Limbow);
		}

		if (splits.length <= 4) {
			return new Location(world, Integer.parseInt(splits[1]), Integer.parseInt(splits[2]), Integer.parseInt(splits[3]));
		} else {
			return new Location(world, Integer.parseInt(splits[1]), Integer.parseInt(splits[2]), Integer.parseInt(splits[3]), Float.parseFloat(splits[4]), Float.parseFloat(splits[5]));
		}
	}

	public Location getSpawn() {
		if (currentSpawn >= (spawns.size() - 1)) {
			currentSpawn = 0;
		}

		Location spawn = spawns.get(currentSpawn);
		currentSpawn++;

		return spawn;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getMapCreator() {
		return mapCreator;
	}

	public void setMapCreator(String mapCreator) {
		this.mapCreator = mapCreator;
	}

	public List<Location> getSpawns() {
		return spawns;
	}

	public void setSpawns(List<Location> spawns) {
		this.spawns = spawns;
	}

	public Location getMapCenter() {
		return mapCenter;
	}

	public void setMapCenter(Location mapCenter) {
		this.mapCenter = mapCenter;
	}

	public Location getSpecSpawn() {
		return specSpawn;
	}

	public void setSpecSpawn(Location specSpawn) {
		this.specSpawn = specSpawn;
	}

	public Location getLobbyLocation() {
		return lobbyLocation;
	}

	public void setLobbyLocation(Location lobbyLocation) {
		this.lobbyLocation = lobbyLocation;
	}
}
