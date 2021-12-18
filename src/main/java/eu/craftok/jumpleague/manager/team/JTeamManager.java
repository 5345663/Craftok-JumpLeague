package eu.craftok.jumpleague.manager.team;

import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.manager.player.JPlayer;
import eu.craftok.jumpleague.manager.player.JPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Project jumpleague Created by Sithey
 */

public class JTeamManager {



    public static List<JTeam> teams = new ArrayList<>();

    public static List<JPlayer> getPlayersByTeam(JTeam team){
        List<JPlayer> players = new ArrayList<>();
        JPlayerManager.jPlayers.forEach(t -> {
            if (t.getTeam() != null && t.getTeam() == team){
                players.add(t);
            }
        });
        return players;
    }

    public static List<JPlayer> getPlayersAliveByTeam(JTeam team){
        List<JPlayer> players = new ArrayList<>();
        getPlayersByTeam(team).forEach(t -> {
            if (t.isPlaying()){
                players.add(t);
            }
        });
        return players;
    }

    public static void joinRandomTeam(){
        for (JPlayer player : JPlayerManager.getPlayingJPlayers()) {
            if (player.getTeam() != null)
                continue;
            for (JTeam team : teams) {
                if (getPlayersByTeam(team).size() < JLMain.getInstance().getGame().getTeamsize()) {
                    player.setTeam(team);
                    break;
                }
            }
        }
    }

    public static void loadTeams(){
        int colort = 0, symbolt = 0;
        DyeColor[] colors = { DyeColor.RED, DyeColor.YELLOW, DyeColor.ORANGE, DyeColor.PURPLE,
                DyeColor.GREEN, DyeColor.BLACK, DyeColor.BLUE, DyeColor.GREEN, DyeColor.GRAY };
        String[] symbols = {  "", "§m",  "§n",
                "§o",  "§l",  "§o§m",
                "§o§n", "§o§l", "§l§m",  "§l§n"};
        for (int a = 0; a < JLMain.getInstance().getGame().getMaxplayers() / JLMain.getInstance().getGame().getTeamsize(); a++){
            DyeColor color = colors[colort];
            String symbol = symbols[symbolt];
                if (color == DyeColor.RED)
                    teams.add(new JTeam(ChatColor.RED + symbol + "Rouge", ChatColor.RED + symbol, color));
                if (color == DyeColor.YELLOW)
                    teams.add(new JTeam(ChatColor.YELLOW + symbol + "Jaune", ChatColor.YELLOW + symbol, color));
                if (color == DyeColor.ORANGE)
                    teams.add(new JTeam(ChatColor.GOLD + symbol + "Orange", ChatColor.GOLD + symbol, color));
                if (color == DyeColor.PURPLE)
                    teams.add(new JTeam(ChatColor.DARK_PURPLE + symbol + "Violet", ChatColor.DARK_PURPLE + symbol, color));
                if (color == DyeColor.GREEN)
                    teams.add(new JTeam(ChatColor.GREEN + symbol + "Vert", ChatColor.GREEN + symbol, color));
                if (color == DyeColor.BLACK)
                    teams.add(new JTeam(ChatColor.BLACK + symbol + "Noir", ChatColor.BLACK + symbol, color));
                if (color == DyeColor.BLUE)
                    teams.add(new JTeam(ChatColor.BLUE + symbol + "Bleu", ChatColor.BLUE + symbol, color));
                if (color == DyeColor.GREEN)
                    teams.add(new JTeam(ChatColor.GREEN + symbol + "Vert", ChatColor.GREEN + symbol, color));
                if (color == DyeColor.GRAY)
                    teams.add(new JTeam(ChatColor.GRAY + symbol + "Gris", ChatColor.GRAY + symbol, color));

            colort++;
            if (colort == colors.length){
                colort = 0;
                symbolt++;
                if (symbolt == symbols.length){
                    return;
                }
            }
        }
    }

    public static void deleteTeam(){
        teams.forEach(t -> {
            org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            if (scoreboard.getTeam(t.getNom()) != null){
                scoreboard.getTeam(t.getNom()).unregister();
            }
        });
    }
}
