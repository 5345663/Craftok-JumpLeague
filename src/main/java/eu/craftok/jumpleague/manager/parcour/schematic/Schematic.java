package eu.craftok.jumpleague.manager.parcour.schematic;

import eu.craftok.jumpleague.JLMain;
import eu.craftok.jumpleague.manager.parcour.Parcour;
import eu.craftok.jumpleague.manager.parcour.jump.Jump;
import eu.craftok.utils.HologramUtil;
import eu.craftok.utils.SchematicUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.File;

import static org.bukkit.Material.ENDER_CHEST;

/**
 * Project jumpleague Created by Sithey
 */

public class Schematic {

    private final String name;
    private final String author;
    private final SchematicUtils schematicUtils;
    private final ESchematic.Difficulty difficulty;

    public Schematic(String name, String author, String schematic, ESchematic.Difficulty difficulty) {
        this.name = name;
        this.author = author;
        this.schematicUtils = new SchematicUtils(new File(JLMain.getInstance().getDataFolder() + "/" + schematic));
        this.difficulty = difficulty;
        SchematicManager.schematics.add(this);
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public ESchematic.Difficulty getDifficulty() {
        return difficulty;
    }

    public void spawnSchematic(Parcour parcour, Location location) {
        Schematic schematic = this;
        schematicUtils.paste(location, false, new SchematicUtils.SchematicEvent() {
            Block goldblock1, goldblock2 = null;
            int miny = 255;

            @Override
            public SchematicUtils.BlockInfo onPaste(Block block, SchematicUtils.BlockInfo blockInfo) {
                if (block.getType() == ENDER_CHEST) {
                    ChestLoot.setChest(schematic, block);
                }
                if (block.getY() < miny)
                    miny = (block.getY());

                if (block.getType() == Material.IRON_PLATE) {
                    block.getChunk().load();
                    if (goldblock1 == null) {
                        goldblock1 = block;
                    } else if (goldblock2 == null) {
                        goldblock2 = block;
                    }
                    block.getState().update(true);
                }
                return blockInfo;
            }

            @Override
            public void onPasteEnd(Short aShort, Short aShort1) {
                if (goldblock1 == null || goldblock2 == null) {
                    System.out.println("MANQUE LE START OU LEND");
                    return;
                }
                Block start, end;
                if (goldblock1.getY() < goldblock2.getY()) {
                    start = goldblock1;
                    end = goldblock2;
                } else {
                    start = goldblock2;
                    end = goldblock1;
                }


                int distancex = -((int) Math.max(location.getX(), start.getX()) - (int) Math.min(location.getX(), start.getX()));
                int distancey = -((int) Math.max(location.getY(), start.getY()) - (int) Math.min(location.getY(), start.getY()));
                int distancez = -((int) Math.max(location.getZ(), start.getZ()) - (int) Math.min(location.getZ(), start.getZ()));

                parcour.getJump().add(new Jump(schematic, miny, start, end));
                spawnHologram(parcour.getJump().size(), start);
                if (parcour.getJump().size() <= SchematicManager.schematics.size()) {
                    ESchematic.loadSchematic(parcour, end.getLocation().add(distancex, distancey, distancez), parcour.getJump().size() + 1);
                }
            }

            @Override
            public void onFileNotFound() {
                System.out.println("SCHEMATIC INTROUVABLE");
            }
        });
    }

    private void spawnHologram(int module, Block start) {
        Location location = start.getLocation().add(0.5, 1, 2);

        new HologramUtil(location, "§fModule §3» §b" + module).spawn();
        location.add(0, -0.66, 0);
        new HologramUtil(location, "§fNom §3» §b" + name).spawn();
        location.add(0, -0.33, 0);
        new HologramUtil(location, "§fAuteur §3» §b" + author).spawn();
        location.add(0, -0.33, 0);
        new HologramUtil(location, "§fDifficulté §3» §b" + (difficulty == ESchematic.Difficulty.EASY ? "§aFacile" : difficulty == ESchematic.Difficulty.NORMAL ? "§eNormal" :
                difficulty == ESchematic.Difficulty.HARD ? "§cDifficile" : "§4Très Difficile")).spawn();

    }

}
