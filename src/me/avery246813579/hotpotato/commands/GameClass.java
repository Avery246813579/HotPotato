package me.avery246813579.hotpotato.commands;

import java.util.ArrayList;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GameState;
import me.avery246813579.hotpotato.util.MessageUtil;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class GameClass {
	protected boolean consoleCanUse = false;
	private ArrayList<GameState> whenAble;
	private String command;
	private String permissions;
	
	public GameClass(String commands, String permissions, ArrayList<GameState> whenAble) {
		this.command = commands;
		this.permissions = permissions;
		this.whenAble = whenAble;
	}
	
	public void run(CommandSender sender, Command cmd, String CommandLabel, String[] args){
		if (sender instanceof ConsoleCommandSender && !consoleCanUse) {
			sender.sendMessage(ChatColor.GREEN + "Console >> " + ChatColor.RED + "The console can not use this command.");
			return;
		}

		if (sender instanceof Player) {
			if (!((Player) sender).hasPermission(permissions)) {
				MessageUtil.sendTextMessage((Player) sender, "noPermission", permissions);
				return;
			}
		}

		if (whenAble != null) {
			if(HotPotato.findGame((Player) sender) == null){
				MessageUtil.sendTextMessage((Player) sender, "canNotUseAtTime");
				return;
			}
			
			GameManager gameManager = HotPotato.findGame((Player) sender);
			if(!whenAble.contains(gameManager.getGameState())){
				MessageUtil.sendTextMessage((Player) sender, "canNotUseAtTime");
				return;
			}
		}
		
		runCommand(sender, args);
	}
	public abstract void runCommand(CommandSender sender, String[] args);
	
	public ArrayList<GameState> getWhenAble() {
		return whenAble;
	}

	public void setWhenAble(ArrayList<GameState> whenAble) {
		this.whenAble = whenAble;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	

	public boolean isConsoleCanUse() {
		return consoleCanUse;
	}

	public void setConsoleCanUse(boolean consoleCanUse) {
		this.consoleCanUse = consoleCanUse;
	}
}
