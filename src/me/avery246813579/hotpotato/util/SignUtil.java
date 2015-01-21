package me.avery246813579.hotpotato.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.files.FileHandler;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GameState;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;

public class SignUtil {
	private static ConcurrentHashMap<Location, String> signs = new ConcurrentHashMap<Location, String>();
	private static boolean loaded = false;

	public SignUtil() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(HotPotato.getPlugin(), new Runnable() {
			public void run() {
				if (!loaded) {
					loadSigns();
				}

				if (signs.isEmpty()) {
					return;
				}

				Iterator<Entry<Location, String>> it = signs.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Location, String> pairs = (Map.Entry<Location, String>) it.next();

					if (pairs.getKey().getBlock().getState().getType() == Material.SIGN || pairs.getKey().getBlock().getState().getType() == Material.WALL_SIGN || pairs.getKey().getBlock().getState().getType() == Material.SIGN_POST) {
						updateSign(pairs.getKey(), pairs.getValue());
					} else {
						signs.remove(pairs.getKey());
					}
				}
			}
		}, 20L, 20L);
	}

	public static void loadSigns() {
		if (!FileHandler.SignFile.getFile().contains("signs")) {
			FileHandler.SignFile.getFile().createSection("signs");
			return;
		}

		for (String location : FileHandler.SignFile.getFile().getConfigurationSection("signs").getKeys(false)) {
			String game = FileHandler.SignFile.getFile().getString("signs." + location);

			if (parseLocation(location).getBlock().getState().getType() == Material.SIGN || parseLocation(location).getBlock().getState().getType() == Material.WALL_SIGN || parseLocation(location).getBlock().getState().getType() == Material.SIGN_POST) {
				updateSign(parseLocation(location), game);
				signs.put(parseLocation(location), game);
			}
		}

		loaded = true;
	}

	public static void createSign(Location location, String game) {
		if (!FileHandler.SignFile.getFile().contains("signs")) {
			FileHandler.SignFile.getFile().createSection("signs");
		}

		ConfigurationSection config = FileHandler.SignFile.getFile().getConfigurationSection("signs");
		if (!config.contains(parseString(location))) {
			config.set(parseString(location), game);
			FileHandler.SignFile.saveFile();
		}

		signs.put(location, game);
		updateSign(location, game);
	}

	public static void deleteSign(Location location) {
		if (!FileHandler.SignFile.getFile().contains("signs")) {
			FileHandler.SignFile.getFile().createSection("signs");
		}

		ConfigurationSection config = FileHandler.SignFile.getFile().getConfigurationSection("signs");
		if (config.contains(parseString(location))) {
			FileHandler.SignFile.getFile().getConfigurationSection("signs").getKeys(false).remove(parseString(location));
			FileHandler.SignFile.saveFile();
		}

		if (signs.contains(location)) {
			signs.remove(location);
		}
	}

	public static void updateSign(Location location, String game) {
		GameManager gameManager = HotPotato.findGame(game);
		Sign sign = (Sign) location.getBlock().getState();

		if (gameManager == null) {
			sign.setLine(0, ChatColor.RED + "-=-=-=-");
			sign.setLine(1, ChatColor.RED + "Arena " + game);
			sign.setLine(2, ChatColor.RED + "not found!");
			sign.setLine(3, ChatColor.RED + "-=-=-=-");
			sign.update();
			return;
		}

		if (gameManager.getGameState() != GameState.Live && gameManager.getGameState() != GameState.End) {
			sign.setLine(0, "" + ChatColor.GREEN + ChatColor.BOLD + "[Join-" + gameManager.getGameName() + "]");
		} else if (gameManager.getGamePlayers().size() >= FileHandler.ConfigFile.getFile().getInt("maxPlayers") && gameManager.getGameState() != GameState.Live && gameManager.getGameState() != GameState.End) {
			sign.setLine(0, "" + ChatColor.RED + ChatColor.BOLD + "[Full-" + gameManager.getGameName() + "]");
		} else {
			sign.setLine(0, "" + ChatColor.RED + ChatColor.BOLD + "[Spec-" + gameManager.getGameName() + "]");
		}

		sign.setLine(1, "");
		sign.setLine(2, "" + ChatColor.GRAY + gameManager.getGamePlayers().size() + "/" + FileHandler.ConfigFile.getFile().getInt("maxPlayers"));
		sign.setLine(3, "" + ChatColor.YELLOW + gameManager.getGameState().name());
		sign.update();
	}

	public static Location parseLocation(String string) {
		String[] locParse = string.split(" ");
		return new Location(Bukkit.getWorld(locParse[0]), Integer.parseInt(locParse[1]), Integer.parseInt(locParse[2]), Integer.parseInt(locParse[3]));
	}

	public static String parseString(Location location) {
		return location.getWorld().getName() + " " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ();
	}
}
