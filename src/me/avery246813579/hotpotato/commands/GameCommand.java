package me.avery246813579.hotpotato.commands;

import java.util.ArrayList;
import java.util.List;

import me.avery246813579.hotpotato.util.MessageUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameCommand implements CommandExecutor {
	List<GameClass> gameClasses = new ArrayList<GameClass>();
	
	public GameCommand(){
		gameClasses.add(new CreateCommand());
		gameClasses.add(new JoinCommand());
		gameClasses.add(new UpdateCommand());
		gameClasses.add(new LeaveCommand());
		gameClasses.add(new StartCommand());
		gameClasses.add(new StopCommand());
	}
	
	/** Protected **/
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if (CommandLabel.equalsIgnoreCase("hotpotato") || CommandLabel.equalsIgnoreCase("hp")) {
			if(args.length == 0){
				MessageUtil.sendTextMessage(sender, "notEnoughArgs");
				return true;
			}
			
			for(GameClass gameClass : gameClasses){
				if(gameClass.getCommand().equalsIgnoreCase(args[0])){
					gameClass.run(sender, cmd, CommandLabel, args);
				}
			}
		}
		
		return false;
	}
}
