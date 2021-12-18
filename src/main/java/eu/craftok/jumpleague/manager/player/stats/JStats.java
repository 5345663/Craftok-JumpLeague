package eu.craftok.jumpleague.manager.player.stats;

import eu.craftok.core.common.user.User;
import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.manager.JLGame;
import eu.craftok.jumpleague.manager.player.JPlayer;
import eu.craftok.utils.PlayerUtils;
import eu.craftok.utils.TimeUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Project jumpleague Created by Sithey
 */

public class JStats {

    private final JPlayer player;
    private int kills, deaths, jumpsuccess, jumpfail;


    public JStats(JPlayer player) {

        this.player = player;
        this.kills = 0;
        this.deaths = 0;
        this.jumpsuccess = 0;
        this.jumpfail = 0;
        
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer().getPlayer() == null) {
                    cancel();
                    return;
                }
                if (JLMain.getInstance().getGame().getState() == JLGame.STATE.JUMP || JLMain.getInstance().getGame().getState() == JLGame.STATE.FIGHT) {
                    if (getPlayer().isPlaying()){
                        User user = JLMain.getAPI().getUserManager().getUserByUniqueId(player.getUniqueId());
                        user.setStat("jumpleague.playingtime", Integer.parseInt(user.getStat("jumpleague.playingtime", 0)) + 1);
                    }
                }

            }
        }.runTaskTimerAsynchronously(JLMain.getInstance(), 20, 20);
    }

    public void addKill() {
        this.kills++;
        player.getUser().setStat("jumpleague.totalkills", String.valueOf(getTotalkills() + 1));
        int gain = JLMain.getCoinsBoosterAPI().getMoneyWithBooster(((int) JLMain.getInstance().getGame().getConfiguration().getValue("coins.kills")));
        PlayerUtils utils = new PlayerUtils(player.getPlayer());
        utils.sendSound(Sound.LEVEL_UP, 1);
        addCoin(gain);
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true, (float) (player.getPlayer().getLocation().getX()), (float) (player.getPlayer().getLocation().getY()), (float) (player.getPlayer().getLocation().getZ()), 1, 1, 1, 0, 1);
        ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(packet);
        utils.sendMessage("§c§lCRAFTOK §8§l» §eCoins §7+§6" + gain + "§6 (§dJoueur tué§6)");
    }

    public void addDeath() {
        this.deaths++;
        player.getUser().setStat("jumpleague.totaldeaths", String.valueOf(getTotaldeaths() + 1));
    }

    public void addJumpSuccess() {
        this.jumpsuccess++;

        player.getUser().setStat("jumpleague.totaljumpsuccess", String.valueOf(getTotaljumpsuccess() + 1));

        int gain = JLMain.getCoinsBoosterAPI().getMoneyWithBooster(((int) JLMain.getInstance().getGame().getConfiguration().getValue("coins.modules")));
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true, (float) (player.getPlayer().getLocation().getX()), (float) (player.getPlayer().getLocation().getY()), (float) (player.getPlayer().getLocation().getZ()), 1, 1, 1, 0, 1);
        PlayerUtils utils = new PlayerUtils(player.getPlayer());
        addCoin(gain);

        ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(packet);
        utils.sendSound(Sound.ORB_PICKUP, 1);
        utils.sendMessage("§c§lCRAFTOK §8§l» §eCoins §7+§6" + gain + "§6 (§dModule terminé§6)");
    }

    public void addFinishedParkour() {
        player.getUser().setStat("jumpleague.totalfinishedparkour", String.valueOf(getTotalfinishedparkour() + 1));

        int gain = JLMain.getCoinsBoosterAPI().getMoneyWithBooster(((int) JLMain.getInstance().getGame().getConfiguration().getValue("coins.finishparkour")));
        PlayerUtils utils = new PlayerUtils(player.getPlayer());
        addCoin(gain);
        utils.sendSound(Sound.LEVEL_UP, 1);
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true, (float) (player.getPlayer().getLocation().getX()), (float) (player.getPlayer().getLocation().getY()), (float) (player.getPlayer().getLocation().getZ()), 1, 1, 1, 0, 1);
        ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(packet);
        utils.sendMessage("§c§lCRAFTOK §8§l» §eCoins §7+§6" + gain + "§6 (§dParkour terminé§6)");
    }

    public void addJumpFail() {
        this.jumpfail++;
        player.getUser().setStat("jumpleague.totaljumpfail", String.valueOf(getTotaljumpfail() + 1));
    }

    public void addTotalWin() {
        player.getUser().setStat("jumpleague.totalwins", String.valueOf(getTotalwins() + 1));

        int gain = JLMain.getCoinsBoosterAPI().getMoneyWithBooster(((int) JLMain.getInstance().getGame().getConfiguration().getValue("coins.win")));
        addCoin(gain);
        PlayerUtils utils = new PlayerUtils(player.getPlayer());
        utils.sendSound(Sound.CAT_HISS, 1);
        utils.sendMessage("§c§lCRAFTOK §8§l» §eCoins §7+§6" + gain + "§6 (§dPartie gagnée§6)");
    }

    public void addCoin(int coin) {
        player.getUser().addCoins(coin);
    }

    public int getKills() {
        return kills;
    }


    public int getDeaths() {
        return deaths;
    }

    public int getTotaldeaths() {
        return Integer.parseInt(player.getUser().getStat("jumpleague.totaldeaths", 0));
    }

    public int getTotalfinishedparkour() {
        return Integer.parseInt(player.getUser().getStat("jumpleague.totalfinishedparkour", 0));
    }

    public int getJumpfail() {
        return jumpfail;
    }

    public int getJumpsuccess() {
        return jumpsuccess;
    }

    public int getTotaljumpfail() {
        return Integer.parseInt(player.getUser().getStat("jumpleague.totaljumpfail", 0));
    }

    public int getTotaljumpsuccess() {
        return Integer.parseInt(player.getUser().getStat("jumpleague.totaljumpsuccess", 0));
    }

    public int getTotalkills() {
        return Integer.parseInt(player.getUser().getStat("jumpleague.totalkills", 0));
    }

    public int getTotalwins() {
        return Integer.parseInt(player.getUser().getStat("jumpleague.totalwins", 0));
    }

    public String getPlayingtime() {
        return TimeUtils.getDurationBreakdown(Integer.parseInt(player.getUser().getStat("jumpleague.playingtime", 0))  * 1000L);
    }

    public JPlayer getPlayer() {
        return player;
    }
}
