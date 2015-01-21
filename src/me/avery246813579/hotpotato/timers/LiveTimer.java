package me.avery246813579.hotpotato.timers;

import org.bukkit.Bukkit;

import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GamePlayer;
import me.avery246813579.hotpotato.game.GameState;
import me.avery246813579.hotpotato.game.GameTimer;
import me.avery246813579.hotpotato.util.FireworkUtil;
import me.avery246813579.hotpotato.util.MessageUtil;

public class LiveTimer extends GameTimer{
	public LiveTimer(GameManager game) {
		super(game, FileHandler.ConfigFile.getFile().getInt("maxGameLength"));
	}

	@Override
	protected void onScheduleEnd(int timeState) {
		Bukkit.getScheduler().cancelTask(timeState);
		
		for(GamePlayer gp : getGameManager().getGamePlayers()){
			MessageUtil.sendTextMessage(gp.getPlayer(), "timeRunout");
		}
		
		getGameManager().startEnd();
	}

	@Override
	protected void onRunnableTick(int timeLeft) {
		if(getGameManager().getGameState() != GameState.Live){
			Bukkit.getScheduler().cancelTask(timeState);
			return;
		}
		
		if(getGameManager().checkWinner()){
			Bukkit.getScheduler().cancelTask(timeState);
			getGameManager().startEnd();
		}
		
		if(getGameManager().getPotatoPlayer() != null){
			try {
				FireworkUtil.playFirework(getGameManager().getPotatoPlayer().getWorld(), getGameManager().getPotatoPlayer().getLocation(), FireworkUtil.getRandomEffect());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
