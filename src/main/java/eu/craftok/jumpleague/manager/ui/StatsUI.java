package eu.craftok.jumpleague.manager.ui;

import eu.craftok.jumpleague.manager.player.stats.JStats;
import eu.craftok.utils.ItemCreator;
import eu.craftok.utils.inventory.CustomInventory;
import eu.craftok.utils.inventory.item.StaticItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Project jumpleague Created by Sithey
 */

public class StatsUI extends CustomInventory {
    private JStats stats;
    public StatsUI(Player p, JStats target) {
        super(p, "§fStatistiques de §3» §b" + target.getPlayer().getOfflinePlayer().getName(), 4, 1);
        stats = target;
    }

    @Override
    public void setupMenu() {
        remplirCornerInventory(new ItemCreator(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)).setName("§c").getItemstack());

        addItem(new StaticItem(10, (new ItemCreator(Material.WATCH))
                .setName("§c§lTemps de jeu")
                .addLore("§7Vous avez joué §b" + stats.getPlayingtime()).getItemstack()));

        addItem(new StaticItem(14, (new ItemCreator(Material.BED))
                .setName("§c§lJumps ratés")
                .addLore("§7Vous avez raté §b" + stats.getTotaljumpfail() +  "§7 jumps !").getItemstack()));

        addItem(new StaticItem(16, (new ItemCreator(Material.IRON_BOOTS))
                .setName("§c§lJumps réussi")
                .addLore("§7Vous avez réussi §b" + stats.getTotaljumpsuccess() + " §7jumps !").getItemstack()));

        addItem(new StaticItem(20, (new ItemCreator(Material.IRON_SWORD))
                .setName("§c§lKills")
                .addLore("§7Vous avez tué §b" + stats.getTotalkills() + " §7joueurs !").getItemstack()));

        addItem(new StaticItem(22, (new ItemCreator(Material.DIAMOND_BOOTS))
                .setName("§c§lJump terminé")
                .addLore("§7Vous avez terminé §b" + stats.getTotalfinishedparkour() + " §7jumps !").getItemstack()));

        addItem(new StaticItem(24, (new ItemCreator(Material.GOLD_INGOT))
                .setName("§c§lVictoires")
                .addLore("§7Vous avez gagné §b" + stats.getTotalwins() + " §7parties !").getItemstack()));

        addItem(new StaticItem(12, (new ItemCreator(Material.COAL))
                .setName("§c§lMorts")
                .addLore("§7Vous êtes mort §b" + stats.getTotaldeaths() + " §7fois !").getItemstack()));
    }
}
