package me.avery246813579.hotpotato.commands;

import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.util.MessageUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

public class CreateCommand extends GameClass {
	public CreateCommand() {
		super("create", "hotpotato.command.create", null);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		if (args.length == 1 || args.length == 2) {
			MessageUtil.sendTextMessage(sender, "usage", "/HotPotato Create (Name) (Map Creator)");
			return;
		}

		if (FileHandler.DataFile.getFile().contains("arenas." + args[1].toLowerCase())) {
			MessageUtil.sendTextMessage(sender, "arenaAlreadyCreated");
			return;
		}

		FileHandler.DataFile.getFile().createSection("arenas." + args[1].toLowerCase());
		ConfigurationSection configurationSection = FileHandler.DataFile.getFile().getConfigurationSection("arenas." + args[1].toLowerCase());
		configurationSection.set("mapName", args[1]);
		configurationSection.set("mapCreator", args[2]);
		FileHandler.DataFile.saveFile();

		MessageUtil.sendTextMessage(sender, "createdArena", args[1]);
	}
}
