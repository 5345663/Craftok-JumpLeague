package eu.craftok.jumpleague.listener.player;

import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.manager.JLGame;
import eu.craftok.jumpleague.manager.player.JPlayer;
import eu.craftok.jumpleague.manager.player.JPlayerManager;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Project jumpleague Created by Sithey
 */

public class PlayerDamageEvent implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getEntity();

        JPlayer jPlayer = JPlayerManager.getJPlayerByPlayer(player);

        if (!jPlayer.isPlaying()) {
            event.setCancelled(true);
        }

        if (JLMain.getInstance().getGame().getState() != JLGame.STATE.FIGHT && !((Player) event.getEntity()).getGameMode().equals(GameMode.SURVIVAL)) {
            event.setCancelled(true);
        }
    }
}
