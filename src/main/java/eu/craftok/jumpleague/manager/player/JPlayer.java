package eu.craftok.jumpleague.manager.player;

import eu.craftok.core.common.CoreCommon;
import eu.craftok.core.common.user.User;
import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.manager.JLGame;
import eu.craftok.jumpleague.manager.parcour.Parcour;
import eu.craftok.jumpleague.manager.parcour.ParcourManager;
import eu.craftok.jumpleague.manager.player.stats.JStats;
import eu.craftok.jumpleague.manager.task.GameTask;
import eu.craftok.jumpleague.manager.team.JTeam;
import eu.craftok.jumpleague.manager.team.JTeamManager;
import eu.craftok.jumpleague.manager.ui.JLInventory;
import eu.craftok.jumpleague.manager.ui.JLInventoryManager;
import eu.craftok.utils.ItemCreator;
import eu.craftok.utils.PlayerUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

/**
 * Project jumpleague Created by Sithey
 */

public class JPlayer {

    private final UUID uuid;
    private final User user;
    private boolean playing;
    private int health;
    private final JStats stats;
    private JTeam team;

    public JPlayer(UUID uuid) {
        this.uuid = uuid;
        this.user = CoreCommon.getCommon().getUserManager().getUserByUniqueId(uuid);
        this.playing = false;
        this.health = 3;
        this.stats = new JStats(this);
        this.team = null;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public User getUser() {
        return user;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(getUniqueId());
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(getUniqueId());
    }

    public Parcour getParcour() {
        return ParcourManager.getParcourByJPlayer(this);
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public JStats getStats() {
        return stats;
    }

    public JTeam getTeam() {
        return team;
    }

    public void setTeam(JTeam team) {
        if (JTeamManager.getPlayersAliveByTeam(team).size() < JLMain.getInstance().getGame().getTeamsize()) {
            if (this.team != null) {
                leaveTeam();
            }
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam(team.getNom()).addPlayer(getOfflinePlayer());
            this.team = team;
        }else {
            PlayerUtils utils = new PlayerUtils(getPlayer());
            utils.sendSound(Sound.VILLAGER_NO, 2f);
            utils.sendMessage("§c§lCRAFTOK §8§l» &7L'equipe est §ccomplet");
        }
    }

    public void leaveTeam(){
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(team.getNom()).removePlayer(getOfflinePlayer());
        this.team = null;
    }

    public void login(PlayerJoinEvent event) {
        JPlayerManager.jPlayers.add(this);
        event.setJoinMessage(null);
        JLMain.getInstance().getScoreboard().join(this);
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
          player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setSaturation(12.8F);
        player.setMaximumNoDamageTicks(20);
        player.setFireTicks(0);
        player.setFallDistance(0.0F);
        player.setLevel(0);
        player.setExp(0.0F);
        player.setWalkSpeed(0.2F);
        player.getInventory().setHeldItemSlot(0);
        player.setAllowFlight(false);
        player.closeInventory();
        player.getActivePotionEffects().stream().map(PotionEffect::getType).forEach(player::removePotionEffect);
        ((CraftPlayer) player).getHandle().getDataWatcher().watch(9, (byte) 0);
        player.getInventory().setHeldItemSlot(4);
        if (JLMain.getInstance().getGame().getState() != JLGame.STATE.LOBBY) {
            player.setGameMode(GameMode.SPECTATOR);
        } else {
            playing = true;
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().setItem(0, new ItemCreator(Material.NETHER_STAR).setName("§e§lStatistiques").getItemstack());
            player.getInventory().setItem(8, new ItemCreator(Material.BED).setName("§c§lQuitter").getItemstack());
            if (GameTask.timer == 60) {
                GameTask.timer--;
                new GameTask().runTaskTimer(JLMain.getInstance(), 0, 20);
            }
            if (JPlayerManager.getAllPlayers().size() == JLMain.getInstance().getGame().getMaxplayers()){
                GameTask.timer = 3;
            }
            if (JLMain.getInstance().getGame().getTeamsize() > 1){
                player.getInventory().setItem(4, new ItemCreator(Material.BANNER).setName("§7Choissisez une equipe").getItemstack());
            }else{
                new PlayerUtils(player).sendTitle(5, 40, 5, "§fLES EQUIPES SONT", "§4INTERDITES !");
            }
        }
        if (isPlaying())
            Bukkit.broadcastMessage("§c§lCRAFTOK §8§l» §c" + event.getPlayer().getName() + " §7vient de rejoindre la partie §a(" + JPlayerManager.getPlayingPlayers().size() + "/" + JLMain.getInstance().getGame().getMaxplayers()  +")");
        new BukkitRunnable() {
            @Override
            public void run() {
                player.updateInventory();
                player.teleport(JLMain.getInstance().getGame().getSpawn());
            }
        }.runTaskLater(JLMain.getInstance(), 2);


    }

    public void logout(PlayerQuitEvent event) {
        JPlayerManager.jPlayers.remove(this);
        event.setQuitMessage(null);
        if (isPlaying())
        Bukkit.broadcastMessage("§c§lCRAFTOK §8§l» §c" + event.getPlayer().getName() + " §7vient de quitter la partie §c(" + JPlayerManager.getPlayingPlayers().size() + "/" + JLMain.getInstance().getGame().getMaxplayers()  +")");
        JLMain.getInstance().getGame().checkWin();
        JLMain.getInstance().getScoreboard().leave(this);
    }

    public void death(Player killer, boolean force) {
        PlayerUtils utils = new PlayerUtils(getPlayer());

        health--;

        getStats().addDeath();

        if (killer != null) {
            PlayerUtils utilskiller = new PlayerUtils(killer);

            utilskiller.sendSound(Sound.ORB_PICKUP, 1);

            new PlayerUtils(JPlayerManager.getPlayers()).sendMessage("§a" + killer.getName() + " §7vient de tuer §c" + getPlayer().getName());

            for (Player allplayer : JPlayerManager.getPlayers()) {
                PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float) (getPlayer().getLocation().getX()), (float) (getPlayer().getLocation().getY()), (float) (getPlayer().getLocation().getZ()), 1, 1, 1, 0, 1);
                ((CraftPlayer) allplayer).getHandle().playerConnection.sendPacket(packet);
            }

            killer.removePotionEffect(PotionEffectType.REGENERATION);
            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 1));
            JPlayerManager.getJPlayerByPlayer(killer).getStats().addKill();
        } else {
            new PlayerUtils(JPlayerManager.getPlayers()).sendMessage("§c§lCRAFTOK §8§l» §c" + getPlayer().getName() + " §7est mort ");
        }

        if (health == 0 || force) {

            setPlaying(false);

            JLMain.getInstance().getGame().checkWin();

            getPlayer().setGameMode(GameMode.SPECTATOR);

            Block block = getPlayer().getLocation().getBlock();
            block.setType(Material.ENDER_CHEST);

            Inventory inventory = Bukkit.createInventory(null, 36, "Coffre de " + getPlayer().getName());
            for (ItemStack stack : getPlayer().getInventory().getContents()) {
                if (stack != null)
                    inventory.addItem(new ItemStack(stack));
            }
            for (ItemStack stack : getPlayer().getInventory().getArmorContents()) {
                if (stack != null)
                    inventory.addItem(new ItemStack(stack));
            }
            JLInventoryManager.jlInventories.add(new JLInventory(block, inventory, 3));

            utils.sendMessage("§7§m----------------------------------------------");
            utils.sendMessage("          §c§lVos Statistiques de partie          \n" + " \n" +
                    "§fVos Kills §3» §b" + getStats().getKills() + "\n" + "\n" +
                    "§fNombres de modules réussis §3» §b" + getStats().getJumpsuccess() + "\n" +
                    "§fNombres de sauts ratés §3» §b" + getStats().getJumpfail());
            utils.sendMessage("§7§m----------------------------------------------");

            utils.sendTitle(5, 20, 5, "§c§LPERDU", "§rVous venez de perdre la partie");
        }
        utils.sendForceRespawn();

        getPlayer().teleport(JLMain.getInstance().getGame().getFightspawn().get(new Random().nextInt(JLMain.getInstance().getGame().getFightspawn().size())));

    }
}
