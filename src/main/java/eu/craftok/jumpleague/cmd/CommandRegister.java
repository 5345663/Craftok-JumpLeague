package eu.craftok.jumpleague.cmd;

import eu.craftok.jumpleague.JLMain;

/**
 * Project jumpleague Created by Sithey
 */

public class CommandRegister {
    public static void registerCommand() {

        JLMain main = JLMain.getInstance();
        main.getCommand("start").setExecutor(new StartCommand());
        main.getCommand("stats").setExecutor(new StatsCommand());
    }

}
