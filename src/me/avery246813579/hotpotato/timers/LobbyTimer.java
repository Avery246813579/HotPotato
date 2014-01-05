package me.avery246813579.hotpotato.timers;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.game.GameManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class LobbyTimer implements Listener, Runnable {

	HotPotato plugin;
	GameManager gm;
	
	public int t;
	int timer;
	
	public LobbyTimer ( HotPotato plugin, GameManager gm ){
		this.plugin = plugin;
		this.gm = gm;
	}
	
	public void init(){
		timer = plugin.getConfigHandler().getLobbyTimer();
		
		t = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20L, 20L);
	}

	@Override
	public void run() {
		if(timer != 0){
			timer--;
			
			for(Player player : gm.getPlayers()){
				if(plugin.getConfigHandler().isXpTimer())
					player.setLevel(timer);
			}
		}
		
		//TODO Add shortening time when percent is filled up
		
		if(timer % 60 == 0){
			if(!gm.getPlayers().isEmpty()){
				for(Player player : gm.getPlayers()){
					plugin.sendMessage(player, "Loading arena in: " + ChatColor.GOLD + timer/60 + " minutes.");
					player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
				}
			}
		}
		
		if(timer % 15 == 0 && timer < 60){
			if(!gm.getPlayers().isEmpty()){
				for(Player player : gm.getPlayers()){
					plugin.sendMessage(player, "Loading arena in: " + ChatColor.GOLD + timer + " seconds.");
					player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
				}
			}
		}
		
		if(timer <= 5){
			if(!gm.getPlayers().isEmpty()){
				for(Player player : gm.getPlayers()){
					plugin.sendMessage(player, "Loading arena in: " + ChatColor.GOLD + timer + " seconds.");
					player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
				}
			}
		}
		
		if(timer == 0){
			if(gm.getPlayers().size() < plugin.getConfigHandler().getMinPlayers()){
				if(!gm.getPlayers().isEmpty()){
					for(Player player: gm.getPlayers()){
						plugin.sendMessage(player, "Lobby timer has been reset due to not having enough players.");
					}
				}
				
				timer = plugin.getConfigHandler().getLobbyTimer();
				return;
			}
			
			plugin.getServer().getScheduler().cancelTask(t);
			gm.loadArena();
		}
	}
}