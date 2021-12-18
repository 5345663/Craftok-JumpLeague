package eu.craftok.jumpleague.manager.task;

import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.manager.JLGame;
import eu.craftok.jumpleague.manager.player.JPlayer;
import eu.craftok.jumpleague.manager.player.JPlayerManager;
import eu.craftok.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Project jumpleague Created by Sithey
 */

public class GameTask extends BukkitRunnable {

    public static int timer = 60;

    @Override
    public void run() {

        JLGame.STATE state = JLMain.getInstance().getGame().getState();
        PlayerUtils util = new PlayerUtils(JPlayerManager.getAllPlayers());
        if (state == JLGame.STATE.LOBBY) {
            if (timer == 30 || timer == 15 || timer == 10 || timer == 5 || timer == 3 || timer == 2) {
                util.sendMessage("§c§lCRAFTOK §8§l» §7La partie va commencer dans §c" +  timer + "§7 secondes");
                util.sendSound(Sound.ORB_PICKUP, 2f);
            }
            if (timer == 1) {
                util.sendMessage("§c§lCRAFTOK §8§l» §7La partie va commencer dans §c" +  timer + "§7 seconde");
                util.sendSound(Sound.ORB_PICKUP, 2f);
            }
            if (timer == 0) {
                util.sendMessage("§7§m-------------------------------");
                util.sendMessage("§fBienvenue en §cJump League §f!");
                util.sendMessage("§fLe but du jeu est simple : Récupérer du stuff");
                util.sendMessage("§fen finissant le maxiumum de jumps");
                util.sendMessage("§fpour être le dernier survivant et gagner la partie");
                util.sendMessage("§7§m-------------------------------");
                util.sendSound(Sound.ORB_PICKUP, 2f);
            }
            if (JPlayerManager.getPlayingJPlayers().size() < JLMain.getInstance().getGame().getMinplayers()) {
                cancel();
                timer = 60;
                return;
            }

            if (timer == 0) {
                timer = 10;
                JLMain.getInstance().getGame().startGame();
                return;
            }
        } else if (state == JLGame.STATE.SCATTER) {

            if (timer == 0) {
                util.sendTitle(5, 10, 5, "§c§lBonne chance", "§rQue le meilleur gagne");
                util.sendSound(Sound.LEVEL_UP, 2f);
                JPlayerManager.getPlayingJPlayers().forEach(jp -> {
                    if (jp.getParcour() != null) {
                        if (jp.getPlayer().getLocation().getBlock().getType() != Material.IRON_PLATE)
                            jp.getPlayer().teleport(jp.getParcour().getJumpactual().getStart().getLocation().add(0.5, 0, 0.5));
                    }
                });
                JLMain.getInstance().getGame().setState(JLGame.STATE.JUMP);
                timer = 10 * 60;
            } else {
                JPlayerManager.getPlayingJPlayers().forEach(jp -> {
                    if (jp.getParcour() != null) {
                        if (jp.getPlayer().getLocation().getBlock().getType() != Material.IRON_PLATE)
                        jp.getPlayer().teleport(jp.getParcour().getJumpactual().getStart().getLocation().add(0.5, 0, 0.5));
                    }
                });
                util.sendTitle(0, 20, 0, "§r§lDébut dans §b", timer + "");
                util.sendSound(Sound.CLICK, 2f);
            }
        } else if (state == JLGame.STATE.JUMP) {
            if (timer == 0) {
                timer = 5 * 60;
                JLMain.getInstance().getGame().startFight();
            } else {
                if (timer <= 10) {
                    util.sendSound(Sound.NOTE_STICKS, 2f);
                }
                JPlayerManager.getPlayingJPlayers().forEach(p -> p.getParcour().checkFailedJump());
            }
        } else if (state == JLGame.STATE.FIGHT) {
            if (timer == 1) {
                JPlayer p = JPlayerManager.getPlayingJPlayers().get(0);
                for (JPlayer jp : JPlayerManager.getPlayingJPlayers()) {
                    if (jp == p)
                        continue;
                    if (jp.getPlayer().getLocation().distance(JLMain.getInstance().getGame().getEndPointLocation()) < p.getPlayer().getLocation().distance(JLMain.getInstance().getGame().getEndPointLocation())) {
                        p.death(null, true);
                        p = jp;
                    }else{
                        jp.death(null, true);
                    }
                }
            }
            if (timer == 60) {
                JLMain.getInstance().getGame().createBeacon();
            }
        } else {
            if (timer == 0) {
                JPlayerManager.getAllPlayers().forEach(jp -> {
                    jp.kickPlayer(null);
                });
                Bukkit.getServer().shutdown();
            }
        }

        if (state != JLGame.STATE.LOBBY) {
            if (JPlayerManager.getPlayingJPlayers().size() == 0) {
                Bukkit.getServer().shutdown();
            }
        }


        timer--;
    }
}
