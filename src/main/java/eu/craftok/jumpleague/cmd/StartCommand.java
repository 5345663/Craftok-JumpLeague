package eu.craftok.jumpleague.cmd;

import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.manager.JLGame;
import eu.craftok.jumpleague.manager.player.JPlayerManager;
import eu.craftok.jumpleague.manager.task.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project jumpleague Created by Sithey
 */

public class StartCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                if (JLMain.getInstance().getGame().getState() == JLGame.STATE.LOBBY) {
                    if (JPlayerManager.getPlayingPlayers().size() > 1) {
                        player.sendMessage("§aVous venez de lancer la partie §e" + Bukkit.getServerName() + " §a!");
                        JLMain.getInstance().getGame().setMinplayers(2);
                        if (GameTask.timer == 60) {
                            new GameTask().runTaskTimer(JLMain.getInstance(), 0, 20);
                        }
                        GameTask.timer = 3;
                    } else {
                        player.sendMessage("§cVous ne pouvez pas lancer la partie car il faut 2 joueurs minimum !");
                    }
                }
            } else {
                player.sendMessage("§cVous n'avez pas l'autorisation de faire ceci !");
            }
        }
        return false;
    }
}
