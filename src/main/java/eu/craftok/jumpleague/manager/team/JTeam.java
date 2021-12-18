package eu.craftok.jumpleague.manager.team;

import eu.craftok.jumpleague.manager.player.JPlayer;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.scoreboard.Team;

/**
 * Project jumpleague Created by Sithey
 */

public class JTeam {

    private String nom, prefix;
    private DyeColor dyeColor;
    public JTeam(String nom, String prefix,DyeColor dyeColor) {
        this.nom = nom;
        this.prefix = prefix;
        this.dyeColor = dyeColor;
        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (scoreboard.getTeam(nom) == null){
            Team team = scoreboard.registerNewTeam(nom);
            team.setPrefix(prefix);
            team.setAllowFriendlyFire(false);
        }
    }

    public String getNom() {
        return nom;
    }

    public String getPrefix() {
        return prefix;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }
}
