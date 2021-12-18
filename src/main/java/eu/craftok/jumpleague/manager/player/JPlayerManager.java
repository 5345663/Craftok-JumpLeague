package eu.craftok.jumpleague.manager.player;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Project jumpleague Created by Sithey
 */

public class JPlayerManager {

    public static List<JPlayer> jPlayers = new ArrayList<>();

    public static List<JPlayer> getPlayingJPlayers() {
        List<JPlayer> values = new ArrayList<>();
        jPlayers.forEach(j -> {
            if (j.isPlaying()) values.add(j);
        });
        return values;
    }

    public static List<Player> getPlayingPlayers() {
        List<Player> values = new ArrayList<>();
        jPlayers.forEach(j -> {
            if (j.isPlaying()) values.add(j.getPlayer());
        });
        return values;
    }

    public static List<Player> getPlayers() {
        List<Player> values = new ArrayList<>();
        jPlayers.forEach(j -> values.add(j.getPlayer()));
        return values;
    }

    public static List<JPlayer> getSpectatingJPlayers() {
        List<JPlayer> values = new ArrayList<>();
        jPlayers.forEach(j -> {
            if (!j.isPlaying()) values.add(j);
        });
        return values;
    }

    public static List<Player> getSpectatingPlayers() {
        List<Player> values = new ArrayList<>();
        jPlayers.forEach(j -> {
            if (!j.isPlaying()) values.add(j.getPlayer());
        });
        return values;
    }

    public static JPlayer getJPlayerByUniqueId(UUID uuid) {
        for (JPlayer jPlayers : jPlayers) {
            if (jPlayers.getPlayer().getUniqueId().compareTo(uuid) == 0) {
                return jPlayers;
            }
        }

        return null;
    }

    public static List<Player> getAllPlayers() {
        List<Player> values = new ArrayList<>();
        jPlayers.forEach(j -> {
            values.add(j.getPlayer());
        });
        return values;
    }

    public static JPlayer getJPlayerByPlayer(Player player) {
        for (JPlayer jPlayers : jPlayers) {
            if (jPlayers.getPlayer().getUniqueId().compareTo(player.getUniqueId()) == 0) {
                return jPlayers;
            }
        }

        return null;
    }
}
