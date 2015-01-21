package me.avery246813579.hotpotato.game;

import me.avery246813579.hotpotato.HotPotato;

import org.bukkit.Bukkit;

public abstract class GameTimer implements Runnable{
	/** Variables **/
	private GameManager gameManager;
	public int timeLeft, timeState;
	
	public GameTimer(GameManager game, int time){
		gameManager = game;
		
		timeLeft = time;
		timeState = Bukkit.getScheduler().scheduleSyncRepeatingTask(HotPotato.getPlugin(), this, 20L, 20L);
	}
	
	@Override
	public void run() {
		if(gameManager.isForceStop()){
			Bukkit.getScheduler().cancelTask(timeState);
			return;
		}
		
		if(timeLeft != 0){
			onRunnableTick(timeLeft);
			timeLeft--;
		}else{
			onScheduleEnd(timeState);
		}
	}
	
	protected abstract void onScheduleEnd(int timeState);
	protected abstract void onRunnableTick(int timeLeft);

	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}
}
