package me.avery246813579.hotpotato.listeners;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.handlers.SignHandler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

	HotPotato plugin;
	
	public SignListener ( HotPotato plugin ){
		this.plugin = plugin;
	}
	
	@EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.SIGN_POST || event.getBlock().getType() == Material.WALL_SIGN) {
        	Sign sign = (Sign) event.getBlock().getState();
            	if (SignHandler.signs.contains(sign)) {
            		SignHandler.unregisterSign(sign);
            		SignHandler.updateSigns();
            	}
			}
    }
	
	@EventHandler
    public void onSignCreate(SignChangeEvent event) {
		Player player = event.getPlayer();
		if(player.hasPermission("hotpotato.signs")){
			if (event.getLine(0).equalsIgnoreCase("[HotPotato]")) {
				if(!event.getLine(1).isEmpty()){
	                Sign sign = (Sign) event.getBlock().getState();
	                sign.setLine(0, event.getLine(0));
	                sign.setLine(1, event.getLine(1));
	                sign.setLine(2, event.getLine(2));
	                sign.setLine(3, event.getLine(3));
	                sign.update();
	                SignHandler.registerSign(sign);
				}
			}else if(event.getLine(0).equalsIgnoreCase("[HPLEAVE]")){	
				event.setLine(0, ChatColor.RED + "Hot Potato");
				event.setLine(1, ChatColor.BLUE + "Leave");
			}else if(event.getLine(0).equalsIgnoreCase("[HPSTART]")){	
				event.setLine(0, ChatColor.RED + "Hot Potato");
				event.setLine(1, ChatColor.BLUE + "Start");
			}
		}
	}
	
	 @EventHandler
     public void onPlayerInteract(PlayerInteractEvent event) {
		 Player player = event.getPlayer();
         if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	 Block b = event.getClickedBlock();
             if (b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST) {
            	 Sign sign = (Sign) b.getState();
                 
            	 if(sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[Join]")){
            		 if(plugin.getGameManager(sign.getLine(1)) != null){
            			 plugin.getServer().dispatchCommand(player, "hotpotato join " + sign.getLine(1));
            		 }else{
            			 plugin.sendMessage(player, ChatColor.RED + "Game not found.");
            		 }
            	 }else if(sign.getLine(0).equalsIgnoreCase(ChatColor.RED + "[Non-Joinable]")){
            		 plugin.sendMessage(player, ChatColor.RED + "Game is not joinable.");
            	 }else if(sign.getLine(0).equalsIgnoreCase(ChatColor.RED + "Hot Potato")){
            		 if(sign.getLine(1).equalsIgnoreCase(ChatColor.BLUE + "Start")){
            			 if(plugin.getInArena().contains(player)){
            					 
            				 GameManager gm = plugin.getPlayersGame(player);
            				 
            						if(!player.hasPermission("hotpotato.start")){
            							sendPerm(player);
            							return;
            						}
            					 
            		            	if(!gm.isInGame()){
            			                plugin.getServer().getScheduler().cancelTask(gm.getLt().t);
            			            	gm.loadArena();
            		            	}else{
            		            		plugin.sendMessage(player, ChatColor.RED + "Arena is in game!");
            		            	}
            			 }else{
            					plugin.sendMessage(player, ChatColor.RED + "You are not in a arena.");
            			 }
            		 }else if(sign.getLine(1).equalsIgnoreCase(ChatColor.BLUE + "Leave")){
            			 if(plugin.getInArena().contains(player)){
            					 
            				 GameManager gm = plugin.getPlayersGame(player);
            				 
            			      gm.removePlayer(player);
            			 }else{
            					plugin.sendMessage(player, ChatColor.RED + "You are not in a arena.");
            			 }
            		 }
            	 }
             }
         }
	 }

	public void sendPerm(Player player){
		plugin.sendMessage(player, ChatColor.RED + "You do not have permission to use this command.");
	}
}
