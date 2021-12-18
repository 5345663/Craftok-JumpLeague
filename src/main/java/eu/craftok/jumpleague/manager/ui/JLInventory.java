package eu.craftok.jumpleague.manager.ui;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Project jumpleague Created by Sithey
 */

public class JLInventory {

    private final Block block;
    private final Inventory inventory;
    private int itemleft;

    public JLInventory(Block block, Inventory inventory, int itemleft) {
        this.block = block;
        this.inventory = inventory;
        this.itemleft = itemleft;
    }

    public JLInventory(Block block, Inventory inventory) {
        this.block = block;
        this.inventory = inventory;
        this.itemleft = -1;
    }

    public Block getBlock() {
        return block;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getItemleft() {
        return itemleft;
    }

    public void takeItem(Player player, ItemStack itemStack) {
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 3);
        player.getInventory().addItem(itemStack);

        itemleft--;

        if (itemleft == 0) {
            getBlock().setType(Material.AIR);
            getBlock().getWorld().playEffect(getBlock().getLocation(), Effect.SMOKE, 10);
            getInventory().clear();
        }
    }
}
