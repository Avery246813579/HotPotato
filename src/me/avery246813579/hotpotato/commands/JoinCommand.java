package me.avery246813579.hotpotato.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.util.MessageUtil;

public class JoinCommand extends GameClass{
	public JoinCommand() {
		super("join", "hotpotato.command.join", null);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if(args.length == 1){
			MessageUtil.sendTextMessage(sender, "usage", "/HotPotato Join (Arena)");
			return;
		}
		
		if(HotPotato.findGame(player) != null){
			MessageUtil.sendTextMessage(sender, "alreadyInGame");
			return;
		}

		if(HotPotato.findGame(args[1]) == null){
			MessageUtil.sendTextMessage(sender, "arenaNotFound");
			return;
		}
		
		HotPotato.findGame(args[1]).joinGame(player);
	}
}
