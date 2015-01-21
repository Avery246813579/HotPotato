package me.avery246813579.hotpotato.timers;

import org.bukkit.Bukkit;

import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GamePlayer;
import me.avery246813579.hotpotato.game.GameState;
import me.avery246813579.hotpotato.game.GameTimer;
import me.avery246813579.hotpotato.util.FireworkUtil;

public class PotatoTimer extends GameTimer{
	public PotatoTimer(GameManager game) {
		super(game, FileHandler.ConfigFile.getFile().getInt("potatoTime"));
	}

	@Override
	protected void onScheduleEnd(int timeState) {
		Bukkit.getScheduler().cancelTask(timeState);
		getGameManager().eliminatePlayer();
	}

	@Override
	protected void onRunnableTick(int timeLeft) {
		if(getGameManager().getGameState() != GameState.Live){
			Bukkit.getScheduler().cancelTask(timeState);
			return;
		}

		for(GamePlayer gamePlayer : getGameManager().getGamePlayers()){
			gamePlayer.getPlayer().setLevel(timeLeft);
		}
		
		try {
			FireworkUtil.playFirework(getGameManager().getPotatoPlayer().getWorld(), getGameManager().getPotatoPlayer().getLocation(), FireworkUtil.getRandomEffect());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
