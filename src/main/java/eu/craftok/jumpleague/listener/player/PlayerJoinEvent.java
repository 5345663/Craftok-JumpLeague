package eu.craftok.jumpleague.listener.player;

import eu.craftok.jumpleague.manager.player.JPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Project jumpleague Created by Sithey
 */

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        (new JPlayer(event.getPlayer().getUniqueId())).login(event);
    }

}
