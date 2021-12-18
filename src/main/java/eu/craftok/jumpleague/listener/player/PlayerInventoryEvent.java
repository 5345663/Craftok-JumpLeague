package eu.craftok.jumpleague.listener.player;

import eu.craftok.core.common.user.User;
import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.manager.JLGame;
import eu.craftok.jumpleague.manager.player.JPlayer;
import eu.craftok.jumpleague.manager.player.JPlayerManager;
import eu.craftok.jumpleague.manager.ui.JLInventory;
import eu.craftok.jumpleague.manager.ui.JLInventoryManager;
import eu.craftok.jumpleague.manager.ui.TeamUI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.ArrayList;

/**
 * Project jumpleague Created by Sithey
 */

public class PlayerInventoryEvent implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (JLMain.getInstance().getGame().getState() == JLGame.STATE.LOBBY){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerPickupItemEvent event) {
        if (JLMain.getInstance().getGame().getState() == JLGame.STATE.LOBBY){
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (JPlayerManager.getJPlayerByPlayer(event.getPlayer()).getParcour() != null) {
            JPlayerManager.getJPlayerByPlayer(event.getPlayer()).getParcour().checkEndJump(event);
        }

        if (event.getAction() != Action.PHYSICAL) {
//            if (event.getPlayer().getItemInHand().getType() == Material.ENCHANTMENT_TABLE) {
////                event.getPlayer().openEnchanting(null, true);
//
//                  event.getPlayer().openInventory(Bukkit.getServer().createInventory(event.getPlayer(), InventoryType.ENCHANTING));
//            }
            if (JLMain.getInstance().getGame().getState() == JLGame.STATE.LOBBY){
                if (event.getPlayer().getItemInHand().getType() == Material.BANNER) {
                    new TeamUI(event.getPlayer()).openMenu();
                }

                if (event.getPlayer().getItemInHand().getType() == Material.BED) {
                    event.getPlayer().kickPlayer("§cRetour au lobby.");
                }
                if (event.getPlayer().getItemInHand().getType() == Material.NETHER_STAR) {
                    event.getPlayer().performCommand("stats");
                }
            }

            if (event.getPlayer().getItemInHand().getType() == Material.WORKBENCH) {
                event.getPlayer().openWorkbench(null, true);
            }
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
            if (JPlayerManager.getJPlayerByPlayer(event.getPlayer()).isPlaying()) {
                if (JLInventoryManager.getJLInventoryByBlock(event.getClickedBlock()) != null) {
                    event.setCancelled(true);
                    event.getPlayer().openInventory(JLInventoryManager.getJLInventoryByBlock(event.getClickedBlock()).getInventory());
                }
            }
        }
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

//        if (event.getInventory().getType().equals(InventoryType.ENCHANTING)){
//            if (event.getClick().isShiftClick()){
//                event.setCancelled(true);
//            }
//            return;
//        }

        JLInventory inventory = JLInventoryManager.getJLInventoryByInventory(event.getInventory());
        if (inventory != null && inventory.getInventory().equals(event.getClickedInventory()) && (event.getClick().isRightClick() || event.getClick().isLeftClick() || event.getClick().isShiftClick())) {
            if (inventory.getItemleft() >= 0) {
                event.setCancelled(true);
                inventory.takeItem(player, event.getCurrentItem());
                event.getInventory().setItem(event.getSlot(), null);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        if (JLMain.getInstance().getGame().getState() != JLGame.STATE.FIGHT) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        User user = JLMain.getAPI().getUserManager().getUserByUniqueId(event.getPlayer().getUniqueId());
        JPlayer p = JPlayerManager.getJPlayerByPlayer(event.getPlayer());
        if (JLMain.getInstance().getGame().getState() == JLGame.STATE.JUMP || JLMain.getInstance().getGame().getState() == JLGame.STATE.FIGHT) {
            if (!JPlayerManager.getJPlayerByPlayer(event.getPlayer()).isPlaying()) {
                event.setCancelled(true);
                JPlayerManager.getSpectatingJPlayers().forEach(s -> s.getPlayer().sendMessage(user.getDisplayName() + "§f: " + event.getMessage()));
                return;
            }
        }

        event.setFormat((p.getTeam() == null ? "" : p.getTeam().getPrefix() + " ") + user.getDisplayName() + "§f: %2$s");
    }

    private ArrayList<Block> cacheblock = new ArrayList<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.TNT)) {
            e.getBlock().setType(Material.AIR);
            e.getPlayer().getWorld().spawn(e.getBlock().getLocation(), TNTPrimed.class).setFuseTicks(40);
        } else if (e.getBlock().getType() != Material.WEB && e.getBlock().getType() != Material.CAKE_BLOCK  && e.getBlock().getType() != Material.FIRE) {
            e.setCancelled(true);
        }else{
            cacheblock.add(e.getBlock());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!cacheblock.contains(e.getBlock()))
        e.setCancelled(true);
        else cacheblock.remove(e.getBlock());
    }

    @EventHandler
    public void EntityExplodeEvent(EntityExplodeEvent e) {
        e.blockList().clear();
    }

    @EventHandler
    public void onMotd(ServerListPingEvent event) {
        if (JLMain.getInstance().getGame().getState() != JLGame.STATE.LOBBY || JPlayerManager.getPlayers().size() == JLMain.getInstance().getGame().getMaxplayers()) {
            event.setMotd("INGAME");
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event)
    {
        if (event.getRightClicked().getType() == EntityType.ARMOR_STAND)
            event.setCancelled(true);
    }

}
