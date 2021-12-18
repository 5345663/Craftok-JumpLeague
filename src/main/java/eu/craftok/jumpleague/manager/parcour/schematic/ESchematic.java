package eu.craftok.jumpleague.manager.parcour.schematic;

import eu.craftok.jumpleague.manager.parcour.Parcour;
import org.bukkit.Location;

/**
 * Project jumpleague Created by Sithey
 */

public class ESchematic {

    private final String name;
    private final String author;
    private final String schematic;
    private final Difficulty difficulty;

    public ESchematic(String name, String author, Difficulty difficulty, String schematic) {
        this.name = name;
        this.author = author;
        this.difficulty = difficulty;
        this.schematic = schematic;
    }

    public static void loadSchematic(Parcour parcour, Location location, int module) {
        Schematic schematic = SchematicManager.getSchematicByModule(module);
        if (schematic == null)
            return;
        schematic.spawnSchematic(parcour, location);
    }

    public void load() {
        new Schematic(name, author, schematic, difficulty);
    }

    public enum Difficulty {EASY, NORMAL, HARD, VERY_HARD}

}
