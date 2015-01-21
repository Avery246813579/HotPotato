package me.avery246813579.hotpotato.listener;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.util.MessageUtil;
import me.avery246813579.hotpotato.util.SignUtil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if (!event.getPlayer().hasPermission("hotpotato.signs")) {
			return;
		}

		if (!event.getLine(0).equalsIgnoreCase("[arena]") || event.getLine(1).isEmpty()) {
			return;
		}

		SignUtil.createSign(event.getBlock().getLocation(), event.getLine(1));
		MessageUtil.sendTextMessage(event.getPlayer(), "signCreate");
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		if (block == null) {
			return;
		}

		if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST) {
			Sign sign = (Sign) block.getState();

			if (sign.getLine(0).contains("[Full-") || sign.getLine(0).contains("[Spec-") || sign.getLine(0).contains("[Join-")) {
				String[] st = sign.getLine(0).split("-");
				if (HotPotato.findGame(st[1]) != null) {
					HotPotato.findGame(st[1]).joinGame(player);
				} else {
					MessageUtil.sendTextMessage(player, "cantFindArena");
				}
			}
		}
	}
}
