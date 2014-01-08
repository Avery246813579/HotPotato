package me.avery246813579.hotpotato.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.handlers.SignHandler;
import me.avery246813579.hotpotato.timers.BlowupTimer;
import me.avery246813579.hotpotato.timers.EndTimer;
import me.avery246813579.hotpotato.timers.LobbyTimer;
import me.avery246813579.hotpotato.timers.StartingTimer;
import me.avery246813579.hotpotato.timers.WaitTimer;

public class GameManager {

	/** Classes **/
	HotPotato plugin;

	Game game;
	
	/** Lists **/
	private List<Player> players = new ArrayList<Player>();
	private List<GamePlayer> gamePlayers = new ArrayList<GamePlayer>();
	private List<Player> alive = new ArrayList<Player>();
	
	/** Game info **/
	private boolean inGame = false;
	private Player hasPotato;
	private Player winner;
	
	private LobbyTimer lt;
	
	public GameManager ( HotPotato plugin, Game game ){
		this.plugin = plugin;
		this.game = game;
	}
	
	public void init(){
		LobbyTimer lt = new LobbyTimer(plugin, this);
		this.setLt(lt);
		lt.init();
		SignHandler.updateSigns();
	}
	
	public void joinGame(Player player){
		if(plugin.getInArena().contains(player)){
			plugin.sendMessage(player, ChatColor.RED + "You can not join a game while you are in one.");
			return;
		}
		
		if(isInGame()){
			plugin.sendMessage(player, ChatColor.RED + "Game is in progress.");
			//TODO Add Spectating
			return;
		}
		
		if(players.size() >= plugin.getConfigHandler().getMaxPlayers()){
			plugin.sendMessage(player, ChatColor.RED + "Arena is full.");
			//TODO Add in vip permissions. Kicks a player when full.
			return;
		}
		
		addPlayer(player);
	}
	
	public void addPlayer(Player player){
		GamePlayer gp;
		
		if(!gamePlayers.contains(player)){
			gp = new GamePlayer(this, player);
			gamePlayers.add(gp);
		}else{
			gp = findGamePlayer(player);
		}
		
		gp.addPlayer();
		
		players.add(player);
		
		plugin.getInArena().add(player);
		
		player.teleport(game.getLobby());
		
		player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 2, 2);
		
		if(plugin.getConfigHandler().isFlyinLobby()){
			player.setAllowFlight(true);
			player.setFlySpeed(0.1f);
			player.setFlying(true);
		}else{
			player.setAllowFlight(false);
			player.setFlySpeed(0.1f);
			player.setFlying(false);
		}
		
		SignHandler.updateSigns();
	}
	
	public void loadArena(){
		StartingTimer st = new StartingTimer(plugin, this);
		st.init();
		
		if(players.size() <= 1){
			plugin.getServer().getScheduler().cancelTask(st.t);
			init();
			for(Player player : players){
				plugin.getMuted().add(player);
				player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
				player.sendMessage("");
				plugin.sendMessage(player ,"Not enough players to start.");
				player.sendMessage("");
				player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");		
			}
			return;
		}
		
		for(Player player : players){
			plugin.getMuted().add(player);
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player ,"Game is loading");
			player.sendMessage("");
			plugin.sendMessage(player, "Current Map " + game.getArenaName() + "!");
			player.sendMessage("");
			plugin.sendMessage(player, "Chat has been muted.");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");		
		}
		
		this.inGame = true;
		SignHandler.updateSigns();
	}
	
	public void loadGame(){
		WaitTimer wt = new WaitTimer(plugin, this);
		wt.init();
		
		for(Player player : players){
			plugin.getMuted().remove(player);
			alive.add(player);
			player.teleport(game.getSpawn());
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player ,"Game starting in: " + plugin.getConfigHandler().getWaitTimer() + " seconds.");
			player.sendMessage("");
			plugin.sendMessage(player, "Try to gain space away from other players!");
			player.sendMessage("");
			plugin.sendMessage(player, "Chat has been unmuted.");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");		
		}
	}
	
	public void startGame(){
		for(Player player : players){
			plugin.getMuted().remove(player);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000000, 2));
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player ,"Game has started.");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
		}
		
		chooseRandomPlayer();
	}
	
	public void chooseRandomPlayer(){
		Random randomGenerator = new Random();
		int playerIndex = randomGenerator.nextInt(alive.size());
		Player player = alive.get(playerIndex);
		
		this.hasPotato = player;
  	  
		for(Player playerz : players){
			plugin.getMuted().remove(playerz);
			playerz.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			playerz.sendMessage("");
			plugin.sendMessage(playerz , ChatColor.RED + player.getName() + ChatColor.GRAY + " has the potato.");
			playerz.sendMessage("");
			playerz.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
		}
  	  
		Inventory i = player.getInventory();
		i.setItem(0, hotPotato());
  	  
		player.getInventory().setHelmet(new ItemStack(Material.TNT));
		
		BlowupTimer bt = new BlowupTimer(plugin, this);
		bt.init();
	}
	
	public void givePotato(Player player, Player given){
		this.hasPotato = given;
		
		Inventory i = player.getInventory();
		i.setItem(0, null);
		
		Inventory i2 = given.getInventory();
		i2.setItem(0, hotPotato());
		
		player.getInventory().setHelmet(null);
		given.getInventory().setHelmet(new ItemStack(Material.TNT));
		
		for(Player playerz : players){
			plugin.getMuted().remove(playerz);
			playerz.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			playerz.sendMessage("");
			plugin.sendMessage(playerz , ChatColor.BLUE + player.getName() + ChatColor.GRAY + " has given the potato to " + ChatColor.RED + given.getName() + ".");
			playerz.sendMessage("");
			playerz.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
		}
	}
	
	public void blowupPlayer(){
		 try{
			 plugin.getFirework().playFirework(hasPotato.getWorld(), hasPotato.getLocation(), plugin.getFirework().getBlowupRandomEffect());
		 }catch (Exception e) {
		      e.printStackTrace();
		 }
		
		hasPotato.getLocation().getWorld().createExplosion(hasPotato.getLocation(), 0.0F);

	    if (this.plugin.getConfigHandler().isShootInAirOnRemove()) {
	    	hasPotato.setVelocity(new Vector(0, 5, 0));
	    }
	    
		Inventory i = hasPotato.getInventory();
		i.setItem(0, null);
		
		hasPotato.getInventory().setHelmet(null);

		if(plugin.getConfigHandler().isAnnounceOnDeath()){
			for(Player player : players){
				plugin.sendMessage(player, ChatColor.RED + hasPotato.getName() + ChatColor.GRAY + " has been removed from the arena.");
			    player.playSound(player.getLocation(), Sound.PORTAL_TRAVEL, 1.0F, 10.0F);
			}
		}
		
		addSpec(hasPotato);
		
		this.clearPotionEffects(hasPotato);
		
		alive.remove(hasPotato);
		
		if(checkPlayers()){
			findWinner();
			return;
		}
		
		chooseRandomPlayer();
	}
	
	public boolean checkPlayers(){
		if(alive.size() > 1){
			return false;
		}
		
		return true;
	}
	
	public void findWinner(){
		Player player = alive.get(0);
		player.setAllowFlight(true);
		player.setFlySpeed(0.1f);
		player.setFlying(true);
		winner = player;

		if(plugin.getConfigHandler().isEconomyReward()){
			HotPotato.economy.depositPlayer(player.getName(), plugin.getConfigHandler().getRewardAmount());
		}
		
		announceWinner();
		
		EndTimer et = new EndTimer(plugin, this);
		et.init();
	}
	
	public void announceWinner(){
		for(Player player : players){
			plugin.sendMessage(player, ChatColor.BLUE + winner.getName() + ChatColor.GRAY + " has won the game.");
		}
	}
	
	/**public void giveCredits(){
		
	}**/
	
	public void removePlayer(Player player){
		findGamePlayer(player).removePlayer();
		
		if(players.contains(player))
			players.remove(player);
		if(alive.contains(player))
			alive.remove(player);
		if(plugin.getInArena().contains(player))
			plugin.getInArena().remove(player);
		for(Player playerz : players){
			playerz.showPlayer(player);
		}		
		
		if(winner == player){
			if(!plugin.getConfigHandler().getCommandsOnWin().isEmpty()){
				return;
			}
			
			for(String s : plugin.getConfigHandler().getCommandsOnWin()){
				if(s.contains("{PLAYER}")){
					s.replaceAll("{PLAYER}", player.getName());
				}
				
				if(s.contains("{ARENA}")){
					s.replace("{ARENA}", game.getArenaName());
				}
				
				Bukkit.dispatchCommand(plugin.getServer().getConsoleSender(), s);
			}
			
		}
		
		SignHandler.updateSigns();
	}
	
	public void addSpec(Player player){
		player.teleport(game.getSpec());
		
		for(Player playerz : players){
			playerz.hidePlayer(player);
		}
		
		player.setVelocity(new Vector(0, 0, 0));
		
		player.setAllowFlight(true);
		player.setFlySpeed(0.1f);
		player.setFlying(true);
	}
	
	public void finishGame(){
		for(Player player : players){
			removePlayer(player);
		}
		
		resetGame();
	}
	
	public void resetGame(){
		hasPotato = null;
		inGame = false;
		winner = null;
		alive.clear();
		players.clear();
		gamePlayers.clear();
		init();
	}

	 public void clearPotionEffects(Player player) {
	        for (PotionEffect effect : player.getActivePotionEffects()) {
	            player.removePotionEffect(effect.getType());
	        }
	 }
	
	public GamePlayer findGamePlayer(Player player){
		for(GamePlayer gp : gamePlayers){
			if(gp.getPlayer() == player){
				return gp;
			}
		}
		
		return null;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public HotPotato getPlugin() {
		return plugin;
	}

	public void setPlugin(HotPotato plugin) {
		this.plugin = plugin;
	}

	public List<GamePlayer> getGamePlayers() {
		return gamePlayers;
	}

	public void setGamePlayers(List<GamePlayer> gamePlayers) {
		this.gamePlayers = gamePlayers;
	}

	public List<Player> getAlive() {
		return alive;
	}

	public void setAlive(List<Player> alive) {
		this.alive = alive;
	}

	public Player getHasPotato() {
		return hasPotato;
	}

	public void setHasPotato(Player hasPotato) {
		this.hasPotato = hasPotato;
	}
	
	  public ItemStack hotPotato()
	  {
	    ItemStack is = new ItemStack(Material.POTATO_ITEM);
	    ItemMeta im = is.getItemMeta();
	    ArrayList<String> lore = new ArrayList<String>();
	    lore.add(ChatColor.DARK_RED + "Left click a player to give away.");
	    im.setLore(lore);
	    im.setDisplayName("Hot Potato");
	    im.addEnchant(Enchantment.KNOCKBACK, 1, true);
	    is.setItemMeta(im);
	    return is;
	  }

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public LobbyTimer getLt() {
		return lt;
	}

	public void setLt(LobbyTimer lt) {
		this.lt = lt;
	}
	
	//Find Game Player
	//Clear Inventory
	//Clear Potion Effects
}
