package eu.craftok.jumpleague;

import ch.verttigo.craftok.coinsbooster.API.CoinsBoosterAPI;
import ch.verttigo.craftok.coinsbooster.CoinsBooster;
import eu.craftok.core.common.CoreCommon;
import eu.craftok.jumpleague.manager.JLGame;
import eu.craftok.jumpleague.manager.team.JTeamManager;
import eu.craftok.jumpleague.utils.sb.Scoreboard;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Project jumpleague Created by Sithey
 */

public class JLMain extends JavaPlugin {
    private static JLMain instance;
    private static CoreCommon API;
    private static CoinsBoosterAPI cbAPI;
    private JLGame game;
    private Scoreboard scoreboard;

    public static CoreCommon getAPI() {
        return API;
    }

    public static JLMain getInstance() {
        return instance;
    }

    public static CoinsBoosterAPI getCoinsBoosterAPI() {
        return cbAPI;
    }

    @Override
    public void onEnable() {
        instance = this;
        API = CoreCommon.getCommon();
        cbAPI = CoinsBooster.getCoinsBoosterAPI();
        (this.game = new JLGame()).setupGame();
        (this.scoreboard = new Scoreboard()).uptadeAllTime();
    }

    @Override
    public void onDisable() {
        if (JLMain.getInstance().game.getTeamsize() > 1){
            JTeamManager.deleteTeam();
        }
    }

    public JLGame getGame() {
        return game;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
