package me.avery246813579.hotpotato.timers;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.game.GameManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class WaitTimer implements Listener, Runnable {

	HotPotato plugin;
	GameManager gm;
	
	public int t;
	int timer;
	
	public WaitTimer ( HotPotato plugin, GameManager gm ){
		this.plugin = plugin;
		this.gm = gm;
	}
	
	public void init(){
		timer = plugin.getConfigHandler().getWaitTimer();
		
		t = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20L, 20L);
	}

	@Override
	public void run() {
		if(timer != 0){
			timer--;
			
			for(Player player : Bukkit.getOnlinePlayers()){
				if(plugin.getConfigHandler().isXpTimer())
					player.setLevel(timer);
			}
		}
		
		if(timer <= 5){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Starting game in: " + ChatColor.GOLD + timer + " seconds.");
				player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
			}
		}
		
		if(timer == 0){
			plugin.getServer().getScheduler().cancelTask(t);
			gm.startGame();
		}
	}
}

