package eu.craftok.jumpleague.listener;

import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.listener.player.*;
import eu.craftok.jumpleague.listener.world.WorldCancelEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * Project jumpleague Created by Sithey
 */

public class EventRegister {

    public static void registerEvent() {
        PluginManager pm = Bukkit.getPluginManager();
        JLMain main = JLMain.getInstance();
        pm.registerEvents(new WorldCancelEvent(), main);
        pm.registerEvents(new PlayerDamageEvent(), main);
        pm.registerEvents(new PlayerJoinEvent(), main);
        pm.registerEvents(new PlayerQuitEvent(), main);
        pm.registerEvents(new PlayerDeathEvent(), main);
        pm.registerEvents(new PlayerInventoryEvent(), main);
    }

}
