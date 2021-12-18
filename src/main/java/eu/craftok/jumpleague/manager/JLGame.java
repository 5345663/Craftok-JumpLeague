package eu.craftok.jumpleague.manager;

import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.cmd.CommandRegister;
import eu.craftok.jumpleague.listener.EventRegister;
import eu.craftok.jumpleague.manager.parcour.Parcour;
import eu.craftok.jumpleague.manager.parcour.ParcourManager;
import eu.craftok.jumpleague.manager.parcour.schematic.ChestLoot;
import eu.craftok.jumpleague.manager.parcour.schematic.ESchematic;
import eu.craftok.jumpleague.manager.parcour.schematic.SchematicManager;
import eu.craftok.jumpleague.manager.player.JPlayer;
import eu.craftok.jumpleague.manager.player.JPlayerManager;
import eu.craftok.jumpleague.manager.task.GameTask;
import eu.craftok.jumpleague.manager.team.JTeam;
import eu.craftok.jumpleague.manager.team.JTeamManager;
import eu.craftok.jumpleague.utils.VoidGenerator;
import eu.craftok.utils.CConfig;
import eu.craftok.utils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Project jumpleague Created by Sithey
 */

public class JLGame {

    private Location spawn;
    private World jl_spawn, jl_jump, jl_fight;
    private STATE state;
    private List<Location> fightspawn, fightsbeacon;
    private CConfig configuration;
    private String map;
    private Location endPointLocation;
    private int minplayers, maxplayers, teamsize;

    public void setupGame() {
        EventRegister.registerEvent();
        CommandRegister.registerCommand();

        if (!JLMain.getInstance().getDataFolder().exists())
            JLMain.getInstance().getDataFolder().mkdir();

        this.state = STATE.LOBBY;

        WorldCreator jl_spawn = new WorldCreator("jl_spawn");
        jl_spawn.generator(new VoidGenerator(Biome.PLAINS));
        this.jl_spawn = jl_spawn.createWorld();
        WorldCreator jl_jump = new WorldCreator("jl_jump");
        jl_jump.generator(new VoidGenerator(Biome.PLAINS));
        this.jl_jump = jl_jump.createWorld();
        WorldCreator jl_fight = new WorldCreator("jl_fight");
        jl_fight.generator(new VoidGenerator(Biome.PLAINS));
        this.jl_fight = jl_fight.createWorld();
        for (World world : Bukkit.getWorlds()) {
            world.setTime(0);
            world.setDifficulty(Difficulty.EASY);
            world.setGameRuleValue("doFireTick", "false");
            world.setGameRuleValue("doDaylightCycle", "false");
        }

        this.configuration = new CConfig("configuration.yml", JLMain.getInstance());

        this.configuration.addValue("location.spawn", "jl_spawn:-0.5:6:66.5:0:0");
        this.spawn = CConfig.getLocationString(this.configuration.getValue("location.spawn").toString());



        this.configuration.addValue("map", "Dust");
        this.map = this.configuration.getValue("map").toString();

        this.configuration.addValue("minplayers", 4);
        this.minplayers = ((int) this.configuration.getValue("minplayers"));

        this.configuration.addValue("maxplayers", 10);
        this.maxplayers = ((int) this.configuration.getValue("maxplayers"));

        this.configuration.addValue("teamsize", 1);
        this.teamsize = ((int) this.configuration.getValue("teamsize"));

        if (this.teamsize > 1){
            JTeamManager.loadTeams();
        }

        this.configuration.addValue("schematics", Collections.singletonList("Test:Sithey:EASY:test.schematic"));

        for (String s : this.configuration.getConfig().getStringList("schematics")) {
            new ESchematic(s.split(":")[0], s.split(":")[1], ESchematic.Difficulty.valueOf(s.split(":")[2]), s.split(":")[3]).load();
        }

        SchematicManager.setSchematicOrder();

        ChestLoot.loadLoot();

        Location jumpspawn = new Location(this.jl_jump, 0, 50, 0);
        for (int i = 1; i <= this.maxplayers; i++) {
            Parcour parcour = new Parcour(jumpspawn);
            jumpspawn = new Location(this.jl_jump, jumpspawn.getX() + 50, 50, 0);
        }

        this.fightspawn = new ArrayList<>();
        this.fightsbeacon = new ArrayList<>();

        this.configuration.addValue("location.fightspawnpoints", Arrays.asList("jl_fight:267:51:1377:0:0", "jl_fight:251:51:1397:0:0", "jl_fight:196:55:1397:0:0"
                , "jl_fight:209:52:1343:0:0", "jl_fight:196:52:1294:0:0", "jl_fight:218:51:1307:0:0", "jl_fight:259:47:1309:0:0", "jl_fight:286:54:1296:0:0", "jl_fight:297:53:1323:0:0",
                "jl_fight:270:51:1362:0:0"));

        for (String s : this.configuration.getConfig().getStringList("location.fightspawnpoints")) {
            Location location = CConfig.getLocationString(s);
            this.fightspawn.add(location);
            location.getChunk().load();
        }

        this.configuration.addValue("location.fightsbeaconpoints", Arrays.asList("jl_fight:267:50:1377:0:0", "jl_fight:251:50:1397:0:0"));

        for (String s : this.configuration.getConfig().getStringList("location.fightsbeaconpoints")) {
            this.fightsbeacon.add(CConfig.getLocationString(s));
        }

        this.configuration.addValue("coins.kills", 10);
        this.configuration.addValue("coins.modules", 10);
        this.configuration.addValue("coins.finishparkour", 10);
        this.configuration.addValue("coins.win", 10);

    }

    public void startGame() {
        setState(STATE.SCATTER);
        if (getTeamsize() > 1)
        JTeamManager.joinRandomTeam();
        List<JPlayer> player = new ArrayList<>(JPlayerManager.getPlayingJPlayers());
        List<Parcour> parcour = new ArrayList<>(ParcourManager.parcours);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isEmpty() || parcour.isEmpty()) {
                    cancel();
                    return;
                }
                parcour.remove(0).setPlayer(player.remove(0));
            }
        }.runTaskTimer(JLMain.getInstance(), 0, 5);
    }

    public void startFight() {
        setState(STATE.FIGHT);
        PlayerUtils utils = new PlayerUtils(JPlayerManager.getPlayingPlayers());

        List<JPlayer> player = new ArrayList<>(JPlayerManager.getPlayingJPlayers());
        List<Location> spawn = new ArrayList<>(fightspawn);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (spawn.isEmpty())
                    spawn.addAll(fightspawn);
                if (player.isEmpty()) {
                    cancel();
                    return;
                }
                JPlayer p = player.remove(0);
                Location s = spawn.remove(0);
                if (p.getTeam() != null){
                    JTeamManager.getPlayersAliveByTeam(p.getTeam()).forEach(jp -> {
                        player.remove(jp);
                        jp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 255, false, false));
                        jp.getPlayer().teleport(s);
                        jp.getPlayer().setGameMode(GameMode.SURVIVAL);
                    });
                    return;
                }
                p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 255, false, false));
                p.getPlayer().teleport(s);
                p.getPlayer().setGameMode(GameMode.SURVIVAL);
            }
        }.runTaskTimer(JLMain.getInstance(), 0, 5);
        utils.sendTitle(5, 10, 5, "§lDébut du Combat", "§cBonne chance");
    }

    public void checkWin() {
        if (state == STATE.LOBBY || state == STATE.FINISH)
            return;

            boolean win = false;
            List<JPlayer> alive = JPlayerManager.getPlayingJPlayers();
            if (getTeamsize() == 1){
                if (alive.size() == 1){
                    win = true;
                }
            }else{
                List<JTeam> teams = new ArrayList<>();
                for (JPlayer ps : alive){
                    if (!teams.contains(ps.getTeam())){
                        teams.add(ps.getTeam());
                    }
                }
                if (teams.size() == 1){
                    win = true;
                }
            }

            if (win) {
                setState(STATE.FINISH);
                GameTask.timer = 15;
                StringBuilder playerst = new StringBuilder();
                JPlayerManager.getPlayingJPlayers().forEach(winner -> {
                    playerst.append(winner.getPlayer().getName()).append(", ");
                    winner.getStats().addTotalWin();
                    PlayerUtils utils = new PlayerUtils(winner.getPlayer());
                    utils.sendMessage("§7§m----------------------------------------------");
                    utils.sendMessage("          §c§lVos Statistiques de partie          \n" + " \n" +
                            "§fVos Kills §3» §b" + winner.getStats().getKills() + "\n" + "\n" +
                            "§fNombres de modules réussis §3» §b" + winner.getStats().getJumpsuccess() + "\n" +
                            "§fNombres de sauts ratés §3» §b" + winner.getStats().getJumpfail());
                    utils.sendMessage("§7§m----------------------------------------------");
                });
                String players = playerst.substring(0, playerst.toString().length() - 2);
                new PlayerUtils(JPlayerManager.getAllPlayers()).sendTitle(5, 100, 5, "§c§LVICTOIRE DE", "§r" + players);
            }
    }

    public void createBeacon() {
        endPointLocation = this.fightsbeacon.get(new Random().nextInt(this.fightsbeacon.size()));
        endPointLocation.getBlock().setType(Material.BEACON);
        setBeaconBase(this.endPointLocation.clone().add(1.0, -1.0, 1.0));
        new PlayerUtils(JPlayerManager.getPlayers()).sendTitle(5, 40, 5, "§lRapprochez vous de la balise", "§cpour etre le vainqueur");
    }

    private void setBeaconBase(Location firstBlock) {
        for (int x = firstBlock.getBlockX(); x > (firstBlock.getBlockX() - 3); x--) {
            for (int z = firstBlock.getBlockZ(); z > (firstBlock.getBlockZ() - 3); z--)
                firstBlock.getWorld().getBlockAt(x, firstBlock.getBlockY(), z).setType(Material.IRON_BLOCK);
        }
    }


    public Location getSpawn() {
        return spawn;
    }

    public World getJl_fight() {
        return jl_fight;
    }

    public World getJl_jump() {
        return jl_jump;
    }

    public World getJl_spawn() {
        return jl_spawn;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public List<Location> getFightspawn() {
        return fightspawn;
    }

    public String getMap() {
        return map;
    }

    public Location getEndPointLocation() {
        return endPointLocation;
    }

    public CConfig getConfiguration() {
        return configuration;
    }

    public int getMinplayers() {
        return minplayers;
    }

    public int getMaxplayers() {
        return maxplayers;
    }

    public int getTeamsize() {
        return teamsize;
    }

    public void setMinplayers(int minplayers) {
        this.minplayers = minplayers;
    }

    public enum STATE {LOBBY, SCATTER, JUMP, FIGHT, FINISH}
}
