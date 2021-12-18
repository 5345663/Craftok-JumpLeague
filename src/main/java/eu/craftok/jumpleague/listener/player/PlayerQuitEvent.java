package eu.craftok.jumpleague.listener.player;

import eu.craftok.jumpleague.manager.player.JPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Project jumpleague Created by Sithey
 */

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event) {
        JPlayerManager.getJPlayerByPlayer(event.getPlayer()).logout(event);
    }
}
