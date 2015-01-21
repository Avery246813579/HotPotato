package me.avery246813579.hotpotato.timers;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GamePlayer;
import me.avery246813579.hotpotato.game.GameTimer;
import me.avery246813579.hotpotato.util.MessageUtil;

public class PrepareTimer extends GameTimer{
	public PrepareTimer(GameManager game) {
		super(game, FileHandler.ConfigFile.getFile().getInt("prepareTime"));
	}

	@Override
	protected void onScheduleEnd(int timeState) {
		Bukkit.getScheduler().cancelTask(timeState);
		getGameManager().startGame();
	}

	@Override
	protected void onRunnableTick(int timeLeft) {
		if(timeLeft <= 5){
			for(GamePlayer gp : getGameManager().getGamePlayers()){
				gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.ORB_PICKUP, 2.0F, 1.0F);
				MessageUtil.sendTextMessage(gp.getPlayer(), "announcePrepare", Integer.toString(timeLeft));
			}
		}
		
		for (GamePlayer gp : getGameManager().getGamePlayers()) {
			gp.getPlayer().setLevel(timeLeft);
		}
	}
}
