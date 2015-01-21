package me.avery246813579.hotpotato.commands;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.util.MessageUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand extends GameClass {
	public LeaveCommand() {
		super("leave", "hotpotato.command.leave", null);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if (HotPotato.findGame(player) == null) {
			MessageUtil.sendTextMessage(sender, "noArenaFound");
			return;
		}

		GameManager gameManager = HotPotato.findGame(player);
		gameManager.findPlayer(player).resetPlayerManually();
		gameManager.findPlayer(player).loadPlayer();
		gameManager.getGamePlayers().remove(gameManager.findPlayer(player));
		MessageUtil.sendTextMessage(sender, "leftArena");
	}
}
