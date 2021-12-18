package eu.craftok.jumpleague.manager.ui;

import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.manager.player.JPlayer;
import eu.craftok.jumpleague.manager.player.JPlayerManager;
import eu.craftok.jumpleague.manager.team.JTeam;
import eu.craftok.jumpleague.manager.team.JTeamManager;
import eu.craftok.utils.ItemCreator;
import eu.craftok.utils.inventory.CustomInventory;
import eu.craftok.utils.inventory.item.ActionItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Project jumpleague Created by Sithey
 */

public class TeamUI extends CustomInventory {
    public TeamUI(Player p) {
        super(p, "§7Choissisez une §cequipe", 6, 1);
    }

    @Override
    public void setupMenu() {
        remplirCadreInventory(new ItemCreator(Material.STAINED_GLASS_PANE).setName("").setDurability((short) 6).getItemstack());
        int slot = 10;

        for (JTeam team : JTeamManager.teams){
            if (slot == 8 || slot == 17 ||  slot == 26 || slot == 35 || slot == 44){
                slot = slot + 2;
            }
            List<JPlayer> players = JTeamManager.getPlayersAliveByTeam(team);
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            for (int i = 0; i < JLMain.getInstance().getGame().getTeamsize(); i++){
                lore.add("§b➤ " + (players.size() < i + 1 ? "" : players.get(i).getPlayer().getName()));
            }
            lore.add("");
            addActionItem(new ActionItem(slot, new ItemCreator(Material.BANNER).setLores(lore).setName(team.getNom()).setAmount(players.size()).setBasecolor(team.getDyeColor()).getItemstack()) {
                              @Override
                              public void onClick(InventoryClickEvent inventoryClickEvent) {
                                  JPlayerManager.getJPlayerByPlayer(player).setTeam(team);
                                  player.getInventory().setItem(4, new ItemCreator(Material.BANNER).setBasecolor(team.getDyeColor()).setName("§7Choissisez une equipe").getItemstack());
                                  openMenuAll();
                              }
                          });
            slot++;
        }


    }
}
