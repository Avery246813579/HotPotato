package me.avery246813579.hotpotato.timers;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GamePlayer;
import me.avery246813579.hotpotato.game.GameTimer;
import me.avery246813579.hotpotato.util.MessageUtil;

public class StartingTimer extends GameTimer{
	public StartingTimer(GameManager game) {
		super(game, FileHandler.ConfigFile.getFile().getInt("lobbyTime"));
	}

	@Override
	protected void onScheduleEnd(int timeState) {
		if(FileHandler.ConfigFile.getFile().getInt("minPlayers") > getGameManager().getGamePlayers().size() && !getGameManager().isForceStart()){
			for(GamePlayer gp : getGameManager().getGamePlayers()){
				MessageUtil.sendTextMessage(gp.getPlayer(), "notEnoughPlayers");
			}
			
			Bukkit.getScheduler().cancelTask(timeState);
			new LobbyTimer(getGameManager());
			return;
		}
		
		Bukkit.getScheduler().cancelTask(timeState);
		getGameManager().prepareGame();
	}

	@Override
	protected void onRunnableTick(int timeLeft) {
		if((int)(FileHandler.ConfigFile.getFile().getInt("maxPlayers") * .75) <= Bukkit.getOnlinePlayers().length && timeLeft > (int)(timeLeft * 25)){
			timeLeft = (int)(timeLeft * .25);
		}
		
		for(GamePlayer gp : getGameManager().getGamePlayers()){
			if(timeLeft % 15 == 0 && timeLeft != 0 || timeLeft == 10 || timeLeft <= 5){
				MessageUtil.sendTextMessage(gp.getPlayer(), "lobbyCountdown", Integer.toString(timeLeft));
			}
			
			if(timeLeft <= 5){
				gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.NOTE_PLING, 2.0F, 1.0F);
			}
		}
		
		for (GamePlayer gp : getGameManager().getGamePlayers()) {
			gp.getPlayer().setLevel(timeLeft);
		}
	}
}
