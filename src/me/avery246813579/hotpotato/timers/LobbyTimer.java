package me.avery246813579.hotpotato.timers;

import org.bukkit.Bukkit;

import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GameTimer;

public class LobbyTimer extends GameTimer{
	public LobbyTimer(GameManager game) {
		super(game, 1);
	}

	@Override
	protected void onScheduleEnd(int timeState) {
		if(getGameManager().isForceStart()){
			Bukkit.getScheduler().cancelTask(timeState);
			getGameManager().startRecruiting();
			return;
		}
		
		timeLeft = 1;
	}

	@Override
	protected void onRunnableTick(int timeLeft) {
		if(getGameManager().getGamePlayers().size() >= FileHandler.ConfigFile.getFile().getInt("minPlayers")){
			Bukkit.getScheduler().cancelTask(timeState);
			getGameManager().stopRecruiting();
			return;
		}
	}
}
