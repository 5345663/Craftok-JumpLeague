package eu.craftok.jumpleague.manager.parcour;

import eu.craftok.jumpleague.manager.parcour.jump.Jump;
import eu.craftok.jumpleague.manager.parcour.schematic.ESchematic;
import eu.craftok.jumpleague.manager.player.JPlayer;
import eu.craftok.jumpleague.manager.player.JPlayerManager;
import eu.craftok.jumpleague.manager.task.GameTask;
import eu.craftok.utils.ItemCreator;
import eu.craftok.utils.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Project jumpleague Created by Sithey
 */

public class Parcour {

    private final List<Jump> jump;
    private final Location spawn;
    private JPlayer player;
    private Jump jumpactual;
    private int percent;

    public Parcour(Location spawn) {
        this.jump = new ArrayList<>();
        this.spawn = spawn;
        this.player = null;
        this.jumpactual = null;
        this.percent = 0;
        ParcourManager.parcours.add(this);
        ESchematic.loadSchematic(this, spawn, 1);
    }

    public List<Jump> getJump() {
        return jump;
    }

    public Location getSpawn() {
        return spawn;
    }

    public JPlayer getJPlayer() {
        return player;
    }

    public void setPlayer(JPlayer player) {
        jumpactual = jump.get(0);

        player.getPlayer().teleport(jumpactual.getStart().getLocation().add(0.5, 0.5, 0.5));
        player.getPlayer().getInventory().clear();
        player.getPlayer().getInventory().setHelmet(new ItemCreator(Material.LEATHER_HELMET).setUnbreakable(true).getItemstack());
        player.getPlayer().getInventory().setChestplate(new ItemCreator(Material.LEATHER_CHESTPLATE).setUnbreakable(true).getItemstack());
        player.getPlayer().getInventory().setLeggings(new ItemCreator(Material.LEATHER_LEGGINGS).setUnbreakable(true).getItemstack());
        player.getPlayer().getInventory().setBoots(new ItemCreator(Material.LEATHER_BOOTS).setUnbreakable(true).getItemstack());
        player.getPlayer().getInventory().addItem(new ItemCreator(Material.WOOD_AXE).setUnbreakable(true).getItemstack());
        this.player = player;
    }

    public void checkFailedJump() {
        if (getJPlayer() == null || getJPlayer().getPlayer() == null)
            return;

        if (getJPlayer().getPlayer().getLocation().getY() < jumpactual.getMiny()) {
            getJPlayer().getPlayer().teleport(jumpactual.getStart().getLocation().add(0.5, 0, 0.5));
            getJPlayer().getStats().addJumpFail();
        }


    }

    public int getPercent() {
        if (getJPlayer() == null || getJPlayer().getPlayer() == null)
            return 0;
        int distance = ((int) getJPlayer().getPlayer().getLocation().distance(jumpactual.getStart().getLocation()));
        int distanceglobal = ((int) jumpactual.getStart().getLocation().distance(jumpactual.getEnd().getLocation()));
        return percent + ((distance * 100 / distanceglobal) / 10);
    }

    public void checkEndJump(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            PlayerUtils util = new PlayerUtils(JPlayerManager.getAllPlayers());
            if (((int) event.getClickedBlock().getLocation().getX()) == jumpactual.getEnd().getX() && ((int) event.getClickedBlock().getLocation().getY()) == jumpactual.getEnd().getY() && ((int) event.getClickedBlock().getLocation().getZ()) == jumpactual.getEnd().getZ()) {
                if (jump.size() == 0) {
                    return;
                }
                jump.remove(jumpactual);
                getJPlayer().getStats().addJumpSuccess();
                percent = percent + 10;

                if (jump.size() == 0) {
                    getJPlayer().getStats().addFinishedParkour();
                    getJPlayer().getPlayer().getInventory().addItem(new ItemCreator(Material.DIAMOND_BOOTS).setUnbreakable(true).getItemstack());
                    util.sendMessage("§c" + getJPlayer().getPlayer().getName() + "§7 a fini tous les modules !");
                    util.sendSound(Sound.SUCCESSFUL_HIT, 2f);
                    if (GameTask.timer > 10) {
                        GameTask.timer = 10;
                    }
                } else {
                    jumpactual = jump.get(0);
                }
            }
        }
    }

    public Jump getJumpactual() {
        return jumpactual;
    }
}
