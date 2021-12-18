package eu.craftok.jumpleague.utils.sb;

import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.manager.JLGame;
import eu.craftok.jumpleague.manager.player.JPlayer;
import eu.craftok.jumpleague.manager.player.JPlayerManager;
import eu.craftok.jumpleague.manager.task.GameTask;
import eu.craftok.utils.PlayerUtils;
import org.bukkit.Bukkit;

import java.text.SimpleDateFormat;

/**
 * Project jumpleague Created by Sithey
 */

public class Scoreboard extends SidebarManager {

    @Override
    public void build(JPlayer player, SidebarEditor e) {
        JLGame.STATE state = JLMain.getInstance().getGame().getState();
        e.clear();
        PlayerUtils util = new PlayerUtils(player.getPlayer());
        if (state == JLGame.STATE.LOBBY) {
            e.setTitle("§c§lJUMPLEAGUE");
            e.add("");
            e.add("§c§LSERVEUR");
            e.add(" §fServeur §3» §b" + Bukkit.getServerName());
            e.add(" §fEn jeu §3» §b" + JPlayerManager.getPlayingPlayers().size() + "/" + JLMain.getInstance().getGame().getMaxplayers());

            if (GameTask.timer != 60) {
                util.sendActionBar("§fDébut dans §b" + GameTask.timer + "§bs");
            } else {
                util.sendActionBar("§cManque de joueurs pour commencer...");
            }
            e.add("");
            e.add("§c§lJEU");
            e.add(" §fMode §3» §b" + (JLMain.getInstance().getGame().getTeamsize() == 1 ? "Solo" : "Team de " + JLMain.getInstance().getGame().getTeamsize()));
            e.add(" §fMap §3» §b" + JLMain.getInstance().getGame().getMap());
            e.add("");
            e.add("§b[§fcraftok.fr§c]");
        } else if (state == JLGame.STATE.SCATTER) {
            e.setTitle("§c§lJUMPLEAGUE");
            e.add("");
            e.add(" §fLancement dans §b" + GameTask.timer + "§bs");
            util.sendActionBar(" §b§oTeleportation des joueurs en cours...");
            e.add("");
            e.add("§b[§fcraftok.fr§c]");

        } else if (state == JLGame.STATE.JUMP) {
            e.setTitle("§c§lDISTANCE");
            e.setByScore(" ", 200);
            JPlayerManager.getPlayingJPlayers().forEach(p -> {
                e.setByScore(" §f" + (p.getTeam() == null ? "" : p.getTeam().getPrefix() + " ") + p.getPlayer().getName() + " §3» §b" + p.getParcour().getPercent(), p.getParcour().getPercent());
            });
            util.sendActionBar("§fFin du jump dans §3» §b" + new SimpleDateFormat("mm:ss").format(GameTask.timer * 1000));
            e.setByScore("", -1);
            e.setByScore("§b[§fcraftok.fr§c]", -2);
        } else if (state == JLGame.STATE.FIGHT) {
            e.setTitle("§c§LVIES");
            e.setByScore(" ", 100);
            JPlayerManager.getPlayingJPlayers().forEach(p -> {
                e.setByScore(" §f" + (p.getTeam() == null ? "" : p.getTeam().getPrefix() + " ")+ p.getPlayer().getName() + " §3» §b" + p.getHealth(), p.getHealth());
            });
            util.sendActionBar("§fFin du fight dans §3» §b" + new SimpleDateFormat("mm:ss").format(GameTask.timer * 1000));
            e.setByScore("", -1);
            e.setByScore("§b[§fcraftok.fr§c]", -2);
        } else {
            e.setTitle("§c§lJUMPLEAGUE");
            e.add("");
            if (player.isPlaying())
            e.add(" §aVous êtes le vainqueur");
            else e.add(" §cVous avez perdu");
            e.add("");
            e.add("§b[§fcraftok.fr§c]");
        }
    }


    public void uptadeAllTime() {
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(JLMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (JPlayerManager.getPlayers().size() != 0) {
                    JLMain.getInstance().getScoreboard().update();
                }
            }
        }, 0L, 5L);
    }


}
