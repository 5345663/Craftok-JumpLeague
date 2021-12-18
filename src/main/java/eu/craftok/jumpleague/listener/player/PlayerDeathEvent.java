package eu.craftok.jumpleague.listener.player;

import eu.craftok.jumpleague.manager.player.JPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Project jumpleague Created by Sithey
 */

public class PlayerDeathEvent implements Listener {

    @EventHandler
    public void onDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
        event.setKeepInventory(true);
        event.setDeathMessage("");
        Player killer = event.getEntity().getKiller();
        JPlayerManager.getJPlayerByPlayer(event.getEntity()).death(killer, false);
    }
}
