package me.avery246813579.hotpotato.util;

import me.avery246813579.hotpotato.files.FileHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtil {
	public static void sendTextMessage(Player player, String message){
		if(!FileHandler.TextFile.getFile().contains(message)){
			FileHandler.TextFile.reloadFile();
		}
		
		player.sendMessage(ChatColor.translateAlternateColorCodes('&' , FileHandler.TextFile.getFile().getString(message)));
	}
	
	public static void sendTextMessage(Player player, String message, String object){
		if(!FileHandler.TextFile.getFile().contains(message)){
			FileHandler.TextFile.reloadFile();
		}
		
		String sentMessage = FileHandler.TextFile.getFile().getString(message);
		
		if(sentMessage.contains("{OBJECT1}")){
			String s = sentMessage.replace("{OBJECT1}", object);
			sentMessage = ChatColor.translateAlternateColorCodes('&', s);
		}

		
		player.sendMessage(sentMessage);
	}
	
	public static void sendTextMessage(CommandSender player, String message){
		if(!FileHandler.TextFile.getFile().contains(message)){
			FileHandler.TextFile.reloadFile();
		}
		
		String send = ChatColor.translateAlternateColorCodes('&' , FileHandler.TextFile.getFile().getString(message));
		player.sendMessage(send);
	}
	
	public static void sendTextMessage(CommandSender player, String message, String object){
		if(!FileHandler.TextFile.getFile().contains(message)){
			FileHandler.TextFile.reloadFile();
		}
		
		String sentMessage = FileHandler.TextFile.getFile().getString(message);
		
		if(sentMessage.contains("{OBJECT1}")){
			String s = sentMessage.replace("{OBJECT1}", object);
			sentMessage = ChatColor.translateAlternateColorCodes('&', s);
		}

		
		player.sendMessage(sentMessage);
	}
	
	public static void sendTextMessage(Player player, String message, String object, String object2){
		if(!FileHandler.TextFile.getFile().contains(message)){
			FileHandler.TextFile.reloadFile();
		}
		
		String rawMessage = message.replace("{OBJECT1}", object);
		String sentMessage = rawMessage.replace("{OBJECT2}", object2);
		player.sendMessage(sentMessage);
	}
	
	public static void sendConsoleMessage(Player player, String message){
		if(!FileHandler.TextFile.getFile().contains(message)){
			FileHandler.TextFile.reloadFile();
		}
		
		ChatColor.translateAlternateColorCodes('&', FileHandler.TextFile.getFile().getString(message));
	}
}
