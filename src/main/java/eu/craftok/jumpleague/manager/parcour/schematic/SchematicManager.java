package eu.craftok.jumpleague.manager.parcour.schematic;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Project jumpleague Created by Sithey
 */

public class SchematicManager {

    public static List<Schematic> schematics = new ArrayList<>();
    public static List<Schematic> schematicend = new ArrayList<>();

    public static Schematic getSchematicByModule(int module) {
        if (schematicend.size() >= module)
            return schematicend.get(module - 1);
        else return null;
    }

    public static List<Schematic> getSchematicListByDifficulty(ESchematic.Difficulty difficulty) {
        List<Schematic> schematic = new ArrayList<>();
        schematics.forEach(s -> {
            if (s.getDifficulty() == difficulty) {
                schematic.add(s);
            }
        });

        return schematic;
    }

    public static void setSchematicOrder() {
        List<Schematic> easy = getSchematicListByDifficulty(ESchematic.Difficulty.EASY);
        List<Schematic> normal = getSchematicListByDifficulty(ESchematic.Difficulty.NORMAL);
        List<Schematic> hard = getSchematicListByDifficulty(ESchematic.Difficulty.HARD);
        List<Schematic> very_hard = getSchematicListByDifficulty(ESchematic.Difficulty.VERY_HARD);
        for (int i = 0; i < 4; i++) {
            if (easy.isEmpty()) {
                break;
            }
            Schematic schematic = easy.get(new Random(System.currentTimeMillis()).nextInt(easy.size()));
            easy.remove(schematic);
            schematicend.add(schematic);
        }
        for (int i = 0; i < 3; i++) {
            if (normal.isEmpty()) {
                break;
            }
            Schematic schematic = normal.get(new Random(System.currentTimeMillis()).nextInt(normal.size()));
            normal.remove(schematic);
            schematicend.add(schematic);
        }
        for (int i = 0; i < 2; i++) {
            if (hard.isEmpty()) {
                break;
            }
            Schematic schematic = hard.get(new Random(System.currentTimeMillis()).nextInt(hard.size()));
            hard.remove(schematic);
            schematicend.add(schematic);
        }

        if (!very_hard.isEmpty()) {
            Schematic schematic = very_hard.get(new Random(System.currentTimeMillis()).nextInt(very_hard.size()));
            very_hard.remove(schematic);
            schematicend.add(schematic);
        }
    }


}
