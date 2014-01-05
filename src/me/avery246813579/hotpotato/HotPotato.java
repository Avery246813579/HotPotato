package me.avery246813579.hotpotato;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.avery246813579.hotpotato.game.Game;
import me.avery246813579.hotpotato.game.GameCreator;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.handlers.CommandHandler;
import me.avery246813579.hotpotato.handlers.ConfigurationHandler;
import me.avery246813579.hotpotato.handlers.FileHandler;
import me.avery246813579.hotpotato.handlers.FireworkHandler;
import me.avery246813579.hotpotato.listeners.BlockListener;
import me.avery246813579.hotpotato.listeners.PlayerListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HotPotato extends JavaPlugin{

	Logger logger = Logger.getLogger("Minecraft");
	
	/** Classes **/
	private ConfigurationHandler configHandler = new ConfigurationHandler(this);
	private FireworkHandler firework = new FireworkHandler();
	private CommandHandler cm = new CommandHandler(this);
	private FileHandler fh = new FileHandler(this);
	private GameCreator gc = new GameCreator(this);
	
	/** Lists **/
	private List<Player> inArena = new ArrayList<Player>();
	private List<Player> muted = new ArrayList<Player>();
	private List<Player> frozed = new ArrayList<Player>();
	private List<GameManager> games = new ArrayList<GameManager>();
	
	@Override
	public void onEnable() {
		/** Save the config **/
		this.getConfig().options().copyDefaults(true);
		this.getFh().getArena().options().copyDefaults(true);
		this.saveConfig();
		this.getFh().saveArena();
		
		/** Inits **/
		configHandler.init();
		cm.init();
		this.enableArenas();
		
		/** Plugin Manager **/
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new BlockListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
	}
	
	@Override
	public void onDisable() {

	}
	
	public void enableArenas(){
        List<String> arenas = new ArrayList<String>();
        arenas = this.getConfig().getStringList("enabled");
        for (String games : arenas) {
            Game game = new Game(this, games);
            game.loadGame();
            GameManager gm = new GameManager(this, game);
            gm.init();
            
            this.games.add(gm);
        }
	}
	
	public GameManager getGameManager(String arena) {
        for (GameManager gm : this.games) {

            if (gm.getGame().getArenaName().equalsIgnoreCase(arena)) {

                return gm;

            }
        }
        return null;

	}

	public GameManager getPlayersGame(Player player) {
		for (GameManager gm : games) {
			if (gm.getPlayers().contains(player)) {
				return gm;
	        }
		}
	    return null;
	}
	
	/************************************************
	 * 
	 * 					Messages
	 * 
	 ************************************************/
	
	public void sendMessage(Player player, String message){
		player.sendMessage(ChatColor.BLUE + "[ " + ChatColor.GRAY + this.getConfigHandler().getPrefix() + ChatColor.BLUE + " ] " + ChatColor.GRAY + message);
	}
	
	public void sendConsole(String message){
		this.getLogger().info(message);
	}
	
	/************************************************
	 * 
	 * 				Getters & Setters
	 * 
	 ************************************************/

	public ConfigurationHandler getConfigHandler() {
		return configHandler;
	}

	public void setConfigHandler(ConfigurationHandler configHandler) {
		this.configHandler = configHandler;
	}

	public FileHandler getFh() {
		return fh;
	}

	public void setFh(FileHandler fh) {
		this.fh = fh;
	}

	public GameCreator getGc() {
		return gc;
	}

	public void setGc(GameCreator gc) {
		this.gc = gc;
	}

	public List<Player> getInArena() {
		return inArena;
	}

	public void setInArena(List<Player> inArena) {
		this.inArena = inArena;
	}

	public List<Player> getMuted() {
		return muted;
	}

	public void setMuted(List<Player> muted) {
		this.muted = muted;
	}

	public List<Player> getFrozed() {
		return frozed;
	}

	public void setFrozed(List<Player> frozed) {
		this.frozed = frozed;
	}

	public FireworkHandler getFirework() {
		return firework;
	}

	public void setFirework(FireworkHandler firework) {
		this.firework = firework;
	}

	public List<GameManager> getGames() {
		return games;
	}

	public void setGames(List<GameManager> games) {
		this.games = games;
	}
}
