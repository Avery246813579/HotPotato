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

public class StopCommand extends GameClass {
	public StopCommand() {
		super("stop", "hotpotato.command.stop", new ArrayList<GameState>(Arrays.asList(GameState.Live)));
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if (HotPotato.findGame(player) == null) {
			MessageUtil.sendTextMessage(sender, "noArenaFound");
			return;
		}

		GameManager gameManager = HotPotato.findGame(args[1]);
		gameManager.setForceStop(true);
		gameManager.forceStopGame();
		for(GamePlayer gamePlayer : gameManager.getGamePlayers()){
			MessageUtil.sendTextMessage(gamePlayer.getPlayer(), "forceStop", player.getName());
		}
	}
}
