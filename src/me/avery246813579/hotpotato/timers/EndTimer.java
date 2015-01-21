package me.avery246813579.hotpotato.timers;

import org.bukkit.Bukkit;

import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GamePlayer;
import me.avery246813579.hotpotato.game.GameTimer;
import me.avery246813579.hotpotato.util.MessageUtil;

public class EndTimer extends GameTimer {
	public EndTimer(GameManager gm) {
		super(gm, FileHandler.ConfigFile.getFile().getInt("endTime"));
	}

	@Override
	protected void onScheduleEnd(int timeState) {
		Bukkit.getScheduler().cancelTask(timeState);
		getGameManager().endGame();
	}

	@Override
	protected void onRunnableTick(int timeLeft) {
		for (GamePlayer gp : getGameManager().getGamePlayers()) {
			if (timeLeft % 2 == 0) {
				MessageUtil.sendTextMessage(gp.getPlayer(), "announceWinner", getGameManager().getWinners().get(0).getPlayer().getName());
			}
			
			gp.getPlayer().setLevel(timeLeft);
		}
	}
}
