package me.avery246813579.hotpotato.game;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GamePlayer {

	GameManager gm;
	
	private Player player;
	private String playerName;
	private Location spawnBack;
	private double health; 
	private int hunger;
	private ItemStack[] inventory;
	private GameMode gamemode;
	private float xpAmount;
	
	
	public GamePlayer ( GameManager gm, Player player ){
		this.gm = gm;
		this.player = player;
		this.playerName = player.getName();
	}
	
	public void addPlayer(){
		spawnBack = player.getLocation().clone().add(0.5D, 0.5D, 0.5D);
		health = player.getHealth();
		hunger = player.getFoodLevel();
		setInventory(player.getInventory().getContents());
		gamemode = player.getGameMode();
		xpAmount = player.getExp();
		
		/** Clear all of this **/
		player.setHealth(20.0);
		player.setFoodLevel(20);
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.setGameMode(GameMode.SURVIVAL);
		player.setExp(0);
		player.setLevel(0);
	}
	
	public void removePlayer(){
		player.teleport(spawnBack);
		if(gm.getPlugin().getConfigHandler().isSaveHealth()){
			player.setHealth(health);
			player.setFoodLevel(hunger);
		}
		player.getInventory().clear();
		player.getInventory().setContents(inventory);
		player.setGameMode(gamemode);
		player.setExp(xpAmount);
	}
	
	/************************************************
	 * 
	 * 				Getters & Setters
	 * 
	 ************************************************/

	public Location getSpawnBack() {
		return spawnBack;
	}

	public void setSpawnBack(Location spawnBack) {
		this.spawnBack = spawnBack;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	public ItemStack[] getInventory() {
		return inventory;
	}

	public void setInventory(ItemStack[] inventory) {
		this.inventory = inventory;
	}

	public GameMode getGamemode() {
		return gamemode;
	}

	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
	}

	public float getXpAmount() {
		return xpAmount;
	}

	public void setXpAmount(int xpAmount) {
		this.xpAmount = xpAmount;
	}
	
	
	
}
