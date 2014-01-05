package me.avery246813579.hotpotato.listeners;

import me.avery246813579.hotpotato.HotPotato;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

	HotPotato plugin;
	
	public PlayerListener ( HotPotato plugin ){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDropItem ( PlayerDropItemEvent event ){
		if(plugin.getInArena().contains(event.getPlayer())){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPickupItem ( PlayerPickupItemEvent event ){
		if(plugin.getInArena().contains(event.getPlayer())){
			event.setCancelled(true);
		}
	}
	
	  @EventHandler(priority=EventPriority.MONITOR)
	  public void onInventoryInteract(InventoryInteractEvent event){
	    Player player = (Player)event.getWhoClicked();
	    if (this.plugin.getInArena().contains(player))
	      event.setCancelled(true);
	  }
	  
	  @EventHandler
	  public void onPlayerQuit(PlayerQuitEvent event){
		  if(plugin.getInArena().contains(event.getPlayer())){
			  plugin.getPlayersGame(event.getPlayer()).removePlayer(event.getPlayer());
		  }
	  }
	  
	  @EventHandler
	  public void onPlayerKick(PlayerKickEvent event){
		  if(plugin.getInArena().contains(event.getPlayer())){
			  plugin.getPlayersGame(event.getPlayer()).removePlayer(event.getPlayer());
		  }
	  }
	  
	  @EventHandler
	  public void onEntityDamage(EntityDamageEvent event){
		  if(event.getEntity() instanceof Player){
			  if(plugin.getInArena().contains(event.getEntity())){
				  event.setCancelled(true);
			  }
		  }
	  }
	  
	  @EventHandler
	  public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
	  {
	    if ((e.getDamager() instanceof Player)) {
	      Player attacker = (Player)e.getDamager();

	      if ((e.getEntity() instanceof Player)) {
	        Player attacked = (Player)e.getEntity();
	        
	        if(plugin.getPlayersGame(attacked) == plugin.getPlayersGame(attacker)){
	        	if(plugin.getPlayersGame(attacker).getHasPotato() == attacker){
	        		plugin.getPlayersGame(attacker).givePotato(attacker, attacked);
	        	}
	        }
	      }
	    }
	  }
}
