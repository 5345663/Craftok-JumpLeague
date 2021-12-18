package eu.craftok.jumpleague.cmd;

import eu.craftok.jumpleague.manager.player.JPlayerManager;
import eu.craftok.jumpleague.manager.player.stats.JStats;
import eu.craftok.jumpleague.manager.ui.StatsUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project jumpleague Created by Sithey
 */

public class StatsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.performCommand("stats " + player.getName());
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage("§cLe joueur est hors ligne !");
                    return false;
                }
                openStats(player, target);
            } else {
                player.performCommand("stats " + args[0]);
            }
        }
        return false;
    }

    private void openStats(Player sender, Player target) {
        JStats stats = JPlayerManager.getJPlayerByPlayer(target).getStats();
        new StatsUI(sender, stats).openMenu();
    }
}
