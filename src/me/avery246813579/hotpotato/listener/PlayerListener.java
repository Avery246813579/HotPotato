package me.avery246813579.hotpotato.listener;

import me.avery246813579.hotpotato.HotPotato;
import me.avery246813579.hotpotato.game.GameManager;
import me.avery246813579.hotpotato.game.GamePlayer;
import me.avery246813579.hotpotato.game.GameState;
import me.avery246813579.hotpotato.util.ItemUtil;
import me.avery246813579.hotpotato.util.MessageUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (HotPotato.findGame(event.getPlayer()) == null) {
			return;
		}

		GameManager gameManager = HotPotato.findGame(event.getPlayer());
		if (gameManager.getPotatoPlayer() == event.getPlayer()) {
			gameManager.eliminatePlayer();
			gameManager.getGamePlayers().remove(gameManager.findPlayer(event.getPlayer()));
		} else {
			gameManager.getGamePlayers().remove(gameManager.findPlayer(event.getPlayer()));
		}
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (HotPotato.findGame((Player) event.getEntity()) == null) {
			return;
		}

		GameManager gameManager = HotPotato.findGame((Player) event.getEntity());
		if (gameManager.getGameState() != GameState.Live) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (HotPotato.findGame((Player) event.getPlayer()) == null) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (HotPotato.findGame((Player) event.getPlayer()) == null) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (HotPotato.findGame((Player) event.getPlayer()) == null) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (HotPotato.findGame((Player) event.getPlayer()) == null) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void onEntiyDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (HotPotato.findGame((Player) event.getEntity()) == null) {
				return;
			}

			GameManager gameManager = HotPotato.findGame((Player) event.getEntity());
			if (gameManager.getGameState() != GameState.Live) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInventoryInteract(InventoryInteractEvent event) {
		if (HotPotato.findGame((Player) event.getWhoClicked()) == null) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			if (HotPotato.findGame((Player) event.getEntity()) == null) {
				return;
			}

			GameManager gameManager = HotPotato.findGame((Player) event.getEntity());
			if (gameManager.getGameState() != GameState.Live) {
				event.getEntity().setHealth(20);

				if (gameManager.isStarting()) {
					event.getEntity().teleport(gameManager.getGame().getSpawn());
				} else {
					event.getEntity().teleport(gameManager.getGame().getLobbyLocation());
				}
			} else {
				if (gameManager.getPotatoPlayer() == (Player) event.getEntity()) {
					gameManager.eliminatePlayer();

					GamePlayer gp = gameManager.findPlayer((Player) event.getEntity());
					gp.makeSpecator();
				} else {
					event.getEntity().teleport(gameManager.getGame().getLobbyLocation());
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			if (HotPotato.findGame((Player) event.getEntity()) == null) {
				return;
			}

			GameManager gameManager = HotPotato.findGame((Player) event.getEntity());
			if (gameManager.getGameState() != GameState.Live) {
				return;
			} else {
				if (HotPotato.findGame((Player) event.getDamager()) != null) {
					if (gameManager != HotPotato.findGame((Player) event.getDamager())) {
						event.setCancelled(true);
						return;
					}

					if (!gameManager.getAlivePlayers().contains(gameManager.findPlayer((Player) event.getEntity()))) {
						event.setCancelled(true);
						return;
					}

					if ((Player) event.getDamager() != gameManager.getPotatoPlayer()) {
						event.setDamage(0);
						return;
					}

					gameManager.givePotato((Player) event.getEntity());
				} else {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (HotPotato.findGame((Player) event.getPlayer()) == null) {
			return;
		}

		GameManager gameManager = HotPotato.findGame((Player) event.getPlayer());
		if (!gameManager.isCanMove()) {
			if (((event.getTo().getBlockX() != event.getFrom().getBlockX()) || (event.getTo().getBlockZ() != event.getFrom().getBlockZ()))) {
				event.setTo(event.getFrom());
				return;
			}
		}
	}

	@EventHandler
	public void onASyncPlayerChat(AsyncPlayerChatEvent event) {
		if (HotPotato.findGame((Player) event.getPlayer()) == null) {
			return;
		}

		GameManager gameManager = HotPotato.findGame((Player) event.getPlayer());
		if (!gameManager.isCanTalk()) {
			event.setCancelled(true);
			MessageUtil.sendTextMessage(event.getPlayer(), "canNotTalk");
			return;
		}
	}
	
	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent event){
		if(event.getItem().isSimilar(ItemUtil.potato())){
			event.setCancelled(true);
		}
	}
}
