package me.avery246813579.hotpotato.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.game.GameManager;

public class SignHandler {

	static HotPotato plugin;
    public static List<Sign> signs = new ArrayList<Sign>();
    public static List<String> signsString = new ArrayList<String>();

    
    public SignHandler ( HotPotato plugin ){
    	SignHandler.plugin = plugin;
    }
    
    public static void updateSigns() {
        
        if (signs.size() == 0){
                return;
        }
        
        List<Sign> signList = new ArrayList<Sign>();
        
        for (Sign si : signs) {
                signList.add(si);
        }
        
        for(Sign s : signList){
        	if (s.getLocation().getBlock().getType() == Material.SIGN_POST || s.getLocation().getBlock().getType() == Material.WALL_SIGN) {
        		s.update();
                
        		GameManager gm = null;
                String arena = s.getLine(1);
                
                if(plugin.getGameManager(arena) != null){
                	s.setLine(1, arena);
                	gm = plugin.getGameManager(arena);
                }else{
                	s.setLine(1, "Arena not found.");
                	signs.remove(s);
                }
                
                if(gm.isInGame()){
                	s.setLine(3, ChatColor.RED + "In Game");
                	s.setLine(0, ChatColor.RED + "[Non-Joinable]");
                }else if(gm.getPlayers().size() >= plugin.getConfigHandler().getMaxPlayers()){
                	s.setLine(3, ChatColor.RED + "Full");
                	s.setLine(0, ChatColor.RED + "[Non-Joinable]");
        		}else{
                	s.setLine(3, ChatColor.GREEN + "In Lobby");
                	s.setLine(0, ChatColor.GREEN + "[Join]");
                }
                
                s.setLine(2, "" + ChatColor.GRAY + gm.getPlayers().size() + "/" + plugin.getConfigHandler().getMaxPlayers());
                        
                s.update();
           }else{
                signs.remove(s);
           }
        }
        signList.clear();
    }
    
    public static void unregisterSign(Sign sign) {
        if (signs.contains(sign)){
        	signs.remove(sign);
        }
    }

    public static void registerSign(Sign sign) {
        if (signs.contains(sign)){
            return;
        }
        
        signs.add(sign);
    }

    public static void saveSigns() {
        
        signsString.clear();
        
        for (Sign sign : signs) {
                String string = locationToString(sign.getLocation());
                signsString.add(string);
        }
        
        plugin.getFh().getArena().set("signs", signsString);
        plugin.getFh().saveArena();
        
    }

    public static void loadSigns() {
        
        signsString = plugin.getFh().getArena().getStringList("signs");
        
        if (signsString == null){
                return;
        }
        
        for (String s : signsString) {
                Location loc = setLocation(s);
                Block block = loc.getBlock();
                
                if (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
                	Sign sign = (Sign) block.getState();
                    signs.add(sign);
                }
        }
        
    }
    
    public static String locationToString(Location loc) {
        return loc.getWorld().getName() + "%" + loc.getX() + "%" + loc.getY() + "%" + loc.getZ();
    }
    
    public static Location setLocation(String string) {
        String[] s = string.split("%");
        Location loc = new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]));
        return loc;
    }
}
