package me.avery246813579.hotpotato.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.timers.EndTimer;
import me.avery246813579.hotpotato.timers.LiveTimer;
import me.avery246813579.hotpotato.timers.LobbyTimer;
import me.avery246813579.hotpotato.timers.PotatoTimer;
import me.avery246813579.hotpotato.timers.PrepareTimer;
import me.avery246813579.hotpotato.timers.StartingTimer;
import me.avery246813579.hotpotato.util.ItemUtil;
import me.avery246813579.hotpotato.util.MessageUtil;

public class GameManager {
	private String gameName;
	private boolean forceStop = false, forceStart = false, starting = false, canTalk = true, canMove = true;
	private List<GamePlayer> gamePlayers = new ArrayList<GamePlayer>();
	private List<GamePlayer> winners = new ArrayList<GamePlayer>();
	private GameState gameState = GameState.Limbow;
	private Player potatoPlayer;
	private String description;
	private Game game;

	public GameManager(String gameName, String gameLocation) {
		this.gameName = gameName;

		this.game = new Game(this, gameLocation);
		this.description = FileHandler.ConfigFile.getFile().getString(ChatColor.translateAlternateColorCodes('&', "description"));
		loadLobby();
	}

	public void forceStopGame() {
		resetGame();
	}

	public void resetGame() {
		forceStart = false;
		forceStop = false;
		starting = false;
		canMove = true;
		canTalk = true;
		setPotatoPlayer(null);

		for (GamePlayer gamePlayer : gamePlayers) {
			gamePlayer.setAlive(false);
			gamePlayer.setKiller(null);
			gamePlayer.setKills(0);
			gamePlayer.setPlayedGame(false);
			gamePlayer.resetPlayerManually();
			gamePlayer.giveRespectedItems();
			gamePlayer.getPlayer().teleport(game.getLobbyLocation());

			for (GamePlayer gp : gamePlayers) {
				gamePlayer.getPlayer().showPlayer(gp.getPlayer());
			}
		}

		winners.clear();
		loadLobby();
	}

	public void loadLobby() {
		/** Used for checking map in future **/
		startRecruiting();
	}

	public void startRecruiting() {
		gameState = GameState.Recruit;

		new LobbyTimer(this);
	}

	public void stopRecruiting() {
		gameState = GameState.Prepare;

		new StartingTimer(this);
	}

	public void prepareGame() {
		for (GamePlayer gp : gamePlayers) {
			gp.getPlayer().sendMessage("");
			gp.getPlayer().sendMessage("");
			gp.getPlayer().sendMessage("");
			gp.getPlayer().sendMessage("");
			gp.getPlayer().sendMessage("");
			gp.getPlayer().sendMessage(ChatColor.RED + "☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰");
			gp.getPlayer().sendMessage(ChatColor.GREEN + "Gamemode ➜ " + ChatColor.YELLOW + "Hot Potato by Avery246813579");
			gp.getPlayer().sendMessage("");
			gp.getPlayer().sendMessage(ChatColor.GREEN + "How to play ➜  " + ChatColor.YELLOW + description);
			gp.getPlayer().sendMessage("");
			gp.getPlayer().sendMessage(ChatColor.GREEN + "Map ➜  " + ChatColor.YELLOW + game.getMapName() + " Created By ➜  " + ChatColor.YELLOW + game.getMapCreator());
			gp.getPlayer().sendMessage(ChatColor.RED + "☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰☰");

			gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.LEVEL_UP, 2.0F, 1.0F);

			gp.resetPlayerManually();
			gp.getPlayer().closeInventory();
			gp.getPlayer().teleport(game.getSpawn());
		}

		canMove = false;
		canTalk = false;
		starting = true;
		new PrepareTimer(this);
	}

	public void startGame() {
		if (gamePlayers.size() < FileHandler.ConfigFile.getFile().getInt("minPlayers")) {
			for (GamePlayer gp : getGamePlayers()) {
				MessageUtil.sendTextMessage(gp.getPlayer(), "notEnoughPlayers");
				resetGame();
				return;
			}
		}

		gameState = GameState.Live;
		starting = false;

		for (GamePlayer gp : getGamePlayers()) {
			gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.CAT_MEOW, 2.0F, 1.0F);
			gp.setAlive(true);
			gp.setPlayedGame(true);
			gp.resetPlayerManually();
			gp.giveRespectedItems();
			gp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 2));
		}

		canTalk = true;
		canMove = true;

		new LiveTimer(this);
		giveRandomPotato();
	}

	public boolean checkWinner() {
		GamePlayer lastAlive = null;
		int alive = 0;

		for (GamePlayer gp : gamePlayers) {
			if (gp.isAlive()) {
				alive++;
				lastAlive = gp;
			}
		}

		if (alive == 1) {
			winners.clear();
			winners.add(lastAlive);
			return true;
		}

		return false;
	}

	public void startEnd() {
		canTalk = false;
		gameState = GameState.End;

		for (GamePlayer gp : gamePlayers) {
			gp.resetPlayerManually();
		}

		if (FileHandler.ConfigFile.getFile().contains("commandsOnWin")) {
			for (String s : FileHandler.ConfigFile.getFile().getStringList("commandsOnWin")) {
				String command = "";
				if (s.contains("{PLAYER}")) {
					command = s.replace("{PLAYER}", winners.get(0).getPlayer().getName());
				} else {
					command = s;
				}
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
			}
		}

		new EndTimer(this);
	}

	public void joinGame(Player player) {
		if (HotPotato.findGame(player) != null) {
			MessageUtil.sendTextMessage(player, "alreadyInGame");
			return;
		}

		if (FileHandler.ConfigFile.getFile().getInt("maxPlayers") <= getGamePlayers().size()) {
			MessageUtil.sendTextMessage(player, "maxPlayersReached");
			return;
		}

		GamePlayer gamePlayer = new GamePlayer(this, player);
		gamePlayers.add(gamePlayer);
		if (gameState == GameState.Live) {
			gamePlayer.makeSpecator();
		} else {
			gamePlayer.getPlayer().teleport(game.getLobbyLocation());
			MessageUtil.sendTextMessage(player, "onArenaJoin");
		}
	}

	public void giveRandomPotato() {
		if (getAlivePlayers().size() <= 1) {
			return;
		}

		GamePlayer gp = getAlivePlayers().get(new Random().nextInt((getAlivePlayers().size() - 1)));

		gp.getPlayer().getInventory().setHelmet(new ItemStack(Material.TNT));
		gp.getPlayer().getInventory().addItem(ItemUtil.potato());
		potatoPlayer = gp.getPlayer();

		for (GamePlayer gamePlayer : gamePlayers) {
			MessageUtil.sendTextMessage(gamePlayer.getPlayer(), "newPotato", gp.getPlayer().getName());
		}

		new PotatoTimer(this);
	}

	public void givePotato(Player player) {
		potatoPlayer.getInventory().remove(ItemUtil.potato());
		potatoPlayer.getInventory().setHelmet(null);

		for (GamePlayer gamePlayer : gamePlayers) {
			MessageUtil.sendTextMessage(gamePlayer.getPlayer(), "newPotatoTimeContinued", player.getName());
		}

		potatoPlayer = player;
		player.getInventory().setHelmet(new ItemStack(Material.TNT));
		player.getInventory().addItem(ItemUtil.potato());
	}

	public void eliminatePlayer() {
		GamePlayer gamePlayer = findPlayer(potatoPlayer);

		for (GamePlayer gamePlayerz : gamePlayers) {
			MessageUtil.sendTextMessage(gamePlayerz.getPlayer(), "playerDeath", gamePlayer.getPlayer().getName());
		}

		gamePlayer.makeSpecator();
		giveRandomPotato();
	}

	public List<GamePlayer> getAlivePlayers() {
		List<GamePlayer> alive = new ArrayList<GamePlayer>();

		for (GamePlayer gamePlayer : gamePlayers) {
			if (gamePlayer.isAlive()) {
				alive.add(gamePlayer);
			}
		}

		return alive;
	}

	public GamePlayer findPlayer(Player player) {
		for (GamePlayer gp : gamePlayers) {
			if (gp.getPlayer() == player) {
				return gp;
			}
		}

		return null;
	}

	public void endGame() {
		canTalk = true;
		resetGame();
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public boolean isForceStop() {
		return forceStop;
	}

	public void setForceStop(boolean forceStop) {
		this.forceStop = forceStop;
	}

	public boolean isForceStart() {
		return forceStart;
	}

	public void setForceStart(boolean forceStart) {
		this.forceStart = forceStart;
	}

	public List<GamePlayer> getGamePlayers() {
		return gamePlayers;
	}

	public void setGamePlayers(List<GamePlayer> gamePlayers) {
		this.gamePlayers = gamePlayers;
	}

	public List<GamePlayer> getWinners() {
		return winners;
	}

	public void setWinners(List<GamePlayer> winners) {
		this.winners = winners;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStarting() {
		return starting;
	}

	public void setStarting(boolean starting) {
		this.starting = starting;
	}

	public boolean isCanTalk() {
		return canTalk;
	}

	public void setCanTalk(boolean canTalk) {
		this.canTalk = canTalk;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public Player getPotatoPlayer() {
		return potatoPlayer;
	}

	public void setPotatoPlayer(Player potatoPlayer) {
		this.potatoPlayer = potatoPlayer;
	}
}
