package eu.craftok.jumpleague.manager.parcour;

import eu.craftok.jumpleague.manager.player.JPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Project jumpleague Created by Sithey
 */

public class ParcourManager {
    public static List<Parcour> parcours = new ArrayList<>();

    public static Parcour getParcourByJPlayer(JPlayer player) {
        for (Parcour p : parcours) {
            if (p.getJPlayer() == player) {
                return p;
            }
        }
        return null;
    }
}
