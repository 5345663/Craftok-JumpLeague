package eu.craftok.jumpleague.manager.parcour.jump;

import eu.craftok.jumpleague.manager.parcour.schematic.Schematic;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Project jumpleague Created by Sithey
 */

public class Jump {

    private final Schematic schematic;
    private final int miny;
    private final Block start;
    private final Block end;

    public Jump(Schematic schematic, int miny, Block start, Block end) {
        this.schematic = schematic;
        this.miny = miny;
        this.start = start;
        this.end = end;
        start.setType(Material.IRON_PLATE, true);
        end.setType(Material.IRON_PLATE, true);
    }

    public Schematic getSchematic() {
        return schematic;
    }

    public Block getStart() {
        return start;
    }

    public Block getEnd() {
        return end;
    }

    public int getMiny() {
        return miny;
    }
}
