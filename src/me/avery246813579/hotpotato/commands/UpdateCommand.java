package me.avery246813579.hotpotato.commands;

import java.util.ArrayList;
import java.util.List;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GamePlayer;
import me.avery246813579.hotpotato.util.MessageUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class UpdateCommand extends GameClass {
	public UpdateCommand() {
		super("update", "hotpotato.command.update", null);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		if (args.length == 1 || args.length == 2) {
			MessageUtil.sendTextMessage(sender, "usage", "/HotPotato Update (Creator, Center, Spawn, Lobby, Spec, Enable, Disable) {Creator | Boolean}");
			return;
		}

		for (String arenas : FileHandler.DataFile.getFile().getConfigurationSection("arenas").getKeys(false)) {
			if (arenas.equalsIgnoreCase(args[2].toLowerCase())) {
				if (args[1].equalsIgnoreCase("center")) {
					ConfigurationSection cs = FileHandler.DataFile.getFile().getConfigurationSection("arenas." + arenas);
					cs.set("mapCenter", ((Player) sender).getLocation().getWorld().getName() + " " + ((Player) sender).getLocation().getBlockX() + " " + ((Player) sender).getLocation().getBlockY() + " " + ((Player) sender).getLocation().getBlockZ()
							+ " " + ((Player) sender).getLocation().getYaw() + " " + ((Player) sender).getLocation().getPitch());
					FileHandler.DataFile.saveFile();
					MessageUtil.sendTextMessage(sender, "updateCommand", args[1]);
					return;
				} else if (args[1].equalsIgnoreCase("spec")) {
					ConfigurationSection cs = FileHandler.DataFile.getFile().getConfigurationSection("arenas." + arenas);
					cs.set("specSpawn", ((Player) sender).getLocation().getWorld().getName() + " " + ((Player) sender).getLocation().getBlockX() + " " + ((Player) sender).getLocation().getBlockY() + " " + ((Player) sender).getLocation().getBlockZ()
							+ " " + ((Player) sender).getLocation().getYaw() + " " + ((Player) sender).getLocation().getPitch());
					FileHandler.DataFile.saveFile();
					MessageUtil.sendTextMessage(sender, "updateCommand", args[1]);
					return;
				} else if (args[1].equalsIgnoreCase("lobby")) {
					ConfigurationSection cs = FileHandler.DataFile.getFile().getConfigurationSection("arenas." + arenas);
					cs.set("lobbyLocation", ((Player) sender).getLocation().getWorld().getName() + " " + ((Player) sender).getLocation().getBlockX() + " " + ((Player) sender).getLocation().getBlockY() + " "
							+ ((Player) sender).getLocation().getBlockZ() + " " + ((Player) sender).getLocation().getYaw() + " " + ((Player) sender).getLocation().getPitch());
					FileHandler.DataFile.saveFile();
					MessageUtil.sendTextMessage(sender, "updateCommand", args[1]);
					return;
				} else if (args[1].equalsIgnoreCase("spawn")) {
					ConfigurationSection cs = FileHandler.DataFile.getFile().getConfigurationSection("arenas." + arenas);
					List<String> spawns;

					if (!cs.contains("mapSpawns")) {
						spawns = new ArrayList<String>();
					} else {
						spawns = cs.getStringList("mapSpawns");
					}

					spawns.add(((Player) sender).getLocation().getWorld().getName() + " " + ((Player) sender).getLocation().getBlockX() + " " + ((Player) sender).getLocation().getBlockY() + " " + ((Player) sender).getLocation().getBlockZ() + " "
							+ ((Player) sender).getLocation().getYaw() + " " + ((Player) sender).getLocation().getPitch());
					cs.set("mapSpawns", spawns);
					FileHandler.DataFile.saveFile();
					MessageUtil.sendTextMessage(sender, "updateCommand", args[1]);
					return;
				} else if (args[1].equalsIgnoreCase("enable")) {
					FileHandler.ConfigFile.getFile().set("activeArenas." + args[2].toLowerCase() + ".name", args[2].toLowerCase());
					FileHandler.ConfigFile.saveFile();
					HotPotato.getGames().add(new GameManager(args[2].toLowerCase(), args[2].toLowerCase()));
					MessageUtil.sendTextMessage(sender, "updateCommand", args[1]);
					return;
				} else if (args[1].equalsIgnoreCase("disable")) {
					if (FileHandler.ConfigFile.getFile().contains("activeArenas." + args[2])) {
						FileHandler.ConfigFile.getFile().set("activeArenas." + args[2], null);
						FileHandler.ConfigFile.saveFile();

						if(HotPotato.findGame(args[2]) != null){
							GameManager gameManager = HotPotato.findGame(args[2]);
							
							for(GamePlayer gamePlayer : gameManager.getGamePlayers()){
								gamePlayer.resetPlayerManually();
								gamePlayer.loadPlayer();
							}
							
							HotPotato.getGames().remove(gameManager);
						}
						
						MessageUtil.sendTextMessage(sender, "updateCommand", args[1]);
					}
					return;
				} else if (args[1].equalsIgnoreCase("creator")) {
					ConfigurationSection cs = FileHandler.DataFile.getFile().getConfigurationSection("arenas." + arenas);
					cs.set("mapCreator", args[3]);
					MessageUtil.sendTextMessage(sender, "updateCommand", args[1]);
					return;
				} else{
					MessageUtil.sendTextMessage(sender, "usage", "/HotPotato Update (Creator, Center, Spawn, Lobby, Spec, Enable, Disable) {Creator | Boolean}");
					return;
				}
			}
		}

		MessageUtil.sendTextMessage(sender, "arenaNotFound");
	}
}
