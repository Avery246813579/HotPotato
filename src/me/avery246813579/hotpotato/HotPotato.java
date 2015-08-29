package me.avery246813579.hotpotato;

import java.util.ArrayList;
import java.util.List;

import me.avery246813579.hotpotato.commands.GameCommand;
import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GamePlayer;
import me.avery246813579.hotpotato.listener.PlayerListener;
import me.avery246813579.hotpotato.listener.SignListener;
import me.avery246813579.hotpotato.util.FireworkUtil;
import me.avery246813579.hotpotato.util.SignUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HotPotato extends JavaPlugin {
	/** Variables **/
	private static List<Player> inGame = new ArrayList<Player>();
	private static List<GameManager> games = new ArrayList<GameManager>();
	private static HotPotato plugin;

	public void onEnable() {
		/** Sets the instance of the class **/
		HotPotato.plugin = this;

		/** Creating instances of Game Files **/
		new FileHandler();
		new FireworkUtil();
		new SignUtil();
		
		/** Load The Active Games **/
		if (FileHandler.ConfigFile.getFile().contains("activeArenas")) {
			for (String arena : FileHandler.ConfigFile.getFile().getConfigurationSection("activeArenas").getKeys(false)) {
				games.add(new GameManager(FileHandler.ConfigFile.getFile().getString("activeArenas." + arena + ".name"), arena));
			}
		}

		/** Adds a listener **/
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new SignListener(), this);

		/** Force Reloads File **/
		FileHandler.TextFile.forceReload();
		FileHandler.ConfigFile.forceReload();

		/** Loads Commands **/
		GameCommand command = new GameCommand();
		getCommand("hotpotato").setExecutor(command);
		getCommand("hp").setExecutor(command);
	}

	public static GameManager findGame(Player player) {
		for (GameManager gameManager : games) {
			for (GamePlayer gp : gameManager.getGamePlayers()) {
				if (gp.getPlayer() == player) {
					return gameManager;
				}
			}
		}

		return null;
	}

	public static GameManager findGame(String name) {
		for (GameManager gameManager : games) {
			if (gameManager.getGame().getMapName().equalsIgnoreCase(name)) {
				return gameManager;
			}
		}

		return null;
	}

	public void onDisable() {
		for(GameManager gameManager : games){
			for(GamePlayer gamePlayer : gameManager.getGamePlayers()){
				gamePlayer.resetPlayerManually();
				gamePlayer.loadPlayer();
			}
		}
	}

	public static HotPotato getPlugin() {
		return plugin;
	}

	public static void setPlugin(HotPotato plugin) {
		HotPotato.plugin = plugin;
	}

	public static List<GameManager> getGames() {
		return games;
	}

	public static void setGames(List<GameManager> games) {
		HotPotato.games = games;
	}

	public static List<Player> getInGame() {
		return inGame;
	}

	public static void setInGame(List<Player> inGame) {
		HotPotato.inGame = inGame;
	}
}
