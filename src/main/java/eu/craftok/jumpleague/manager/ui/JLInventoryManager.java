package eu.craftok.jumpleague.manager.ui;

import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Project jumpleague Created by Sithey
 */

public class JLInventoryManager {

    public static List<JLInventory> jlInventories = new ArrayList<>();

    public static JLInventory getJLInventoryByBlock(Block block) {
        for (JLInventory inventory : jlInventories) {
            if (inventory.getBlock().getX() == block.getX() && inventory.getBlock().getY() == block.getY() && inventory.getBlock().getZ() == block.getZ()) {
                return inventory;
            }
        }
        return null;
    }

    public static JLInventory getJLInventoryByInventory(Inventory inv) {
        for (JLInventory inventory : jlInventories) {
            if (inventory.getInventory().getName().equals(inv.getName())) {
                return inventory;
            }
        }
        return null;
    }

}
