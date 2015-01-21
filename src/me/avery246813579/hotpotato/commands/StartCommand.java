package me.avery246813579.hotpotato.commands;

import java.util.ArrayList;
import java.util.Arrays;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GamePlayer;
import me.avery246813579.hotpotato.game.GameState;
import me.avery246813579.hotpotato.util.MessageUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand extends GameClass {
	public StartCommand() {
		super("start", "hotpotato.command.start", new ArrayList<GameState>(Arrays.asList(GameState.Recruit)));
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if (HotPotato.findGame(player) == null) {
			MessageUtil.sendTextMessage(sender, "noArenaFound");
			return;
		}

		GameManager gameManager = HotPotato.findGame(args[1]);
		gameManager.setForceStart(true);
		for(GamePlayer gamePlayer : gameManager.getGamePlayers()){
			MessageUtil.sendTextMessage(gamePlayer.getPlayer(), "forceStart", player.getName());
		}
	}
}
