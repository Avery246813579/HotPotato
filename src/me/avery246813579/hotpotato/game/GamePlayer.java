package me.avery246813579.hotpotato.game;

import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.util.MessageUtil;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class GamePlayer {
	/** Classes **/
	GameManager gameManager;
	
	/** Variables **/
	private Player player, killer;
	private int kills;
	private boolean alive, playedGame = false;
	
	/** Players Old Information **/
	private ItemStack[] playerInventory, armorInventory;
	private boolean isFlying, canFly;
	private Location lastLocation;
	private GameMode gameMode;
	private int hunger, levels;
	private double health;
	private float exp;

	public GamePlayer(GameManager gameManager, Player player){
		this.gameManager = gameManager;
		this.player = player;
		
		savePlayer();
		resetPlayerManually();
		giveRespectedItems();
	}

	public void makeSpecator(){
		alive = false;
		gameManager.checkWinner();
		resetPlayerManually();
		giveRespectedItems();
		player.teleport(gameManager.getGame().getSpecSpawn());
		player.setHealth(20);
		player.setVelocity(new Vector(0, 0, 0));
		
		if(FileHandler.ConfigFile.getFile().getBoolean("canFlyWhenDead")){
			player.setAllowFlight(true);
			player.setFlying(true);
		}else{
			player.setAllowFlight(false);
			player.setFlying(false);
		}
		
		for(GamePlayer gp : gameManager.getGamePlayers()){
			if(gameManager.getGameState() == GameState.Live){
				Player other = gp.getPlayer();
				
				if(gp.isAlive()){
					player.showPlayer(other);
					other.hidePlayer(player);
				}else{
					player.hidePlayer(other);
					other.hidePlayer(player);
				}
			}else{
				player.showPlayer(gp.getPlayer());;
				gp.getPlayer().showPlayer(player);
			}
		}
		
		MessageUtil.sendTextMessage(player, "makeSpec");
	}
	
	public void savePlayer(){
		playerInventory = player.getInventory().getContents();
		armorInventory = player.getInventory().getArmorContents();
		isFlying = player.isFlying();
		canFly = player.getAllowFlight();
		lastLocation = player.getLocation();
		gameMode = player.getGameMode();
		hunger = player.getFoodLevel();
		levels = player.getLevel();
		health = player.getHealth();
		exp = player.getExp();
	}
	
	@SuppressWarnings("deprecation")
	public void loadPlayer(){
		player.getInventory().setContents(playerInventory);
		player.getInventory().setArmorContents(armorInventory);
		player.setFlying(isFlying);
		player.setAllowFlight(canFly);
		player.teleport(lastLocation);
		player.setGameMode(gameMode);
		player.setFoodLevel(hunger);
		player.setLevel(levels);
		player.setHealth(health);
		player.setExp(exp);
		player.updateInventory();
	}
	
	public void giveRespectedItems(){
		
	}
	
	@SuppressWarnings("deprecation")
	public void resetPlayerManually(){
		player.setHealth(20.0);
		player.setFoodLevel(20);
		player.setFireTicks(0);
		player.setGameMode(GameMode.SURVIVAL);
		player.setFlying(false);
		player.setAllowFlight(false);
		player.setMaxHealth(20);
		player.setVelocity(new Vector(0, 0, 0));
		player.setExp(0);
		player.setLevel(0);
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		
		for(PotionEffect potionEffect : player.getActivePotionEffects()){
			player.removePotionEffect(potionEffect.getType());
		}
		
		player.updateInventory();
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getKiller() {
		return killer;
	}

	public void setKiller(Player killer) {
		this.killer = killer;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isPlayedGame() {
		return playedGame;
	}

	public void setPlayedGame(boolean playedGame) {
		this.playedGame = playedGame;
	}
}
