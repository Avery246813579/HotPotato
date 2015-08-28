package me.avery246813579.hotpotato.commands;

import org.bukkit.command.CommandSender;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.util.MessageUtil;

public class ListCommand extends GameClass {
	public ListCommand() {
		super("list", "hotpotato.command.list", null);
		this.consoleCanUse = true;
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		String maps = "";
		for (GameManager gameManager : HotPotato.getGames()) {
			String key = MessageUtil.getText("gameListGame", gameManager.getGameName(), Integer.toString(gameManager.getGamePlayers().size()), Integer.toString(FileHandler.ConfigFile.getFile().getInt("maxPlayers")));
			maps = maps + ", " + key;
		}

		MessageUtil.sendTextMessage(sender, "gameList", maps.substring(2));
	}
}
