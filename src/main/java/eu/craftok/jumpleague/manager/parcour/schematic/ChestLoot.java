package eu.craftok.jumpleague.manager.parcour.schematic;

import com.google.common.collect.Lists;
import eu.craftok.jumpleague.manager.ui.JLInventory;
import eu.craftok.jumpleague.manager.ui.JLInventoryManager;
import eu.craftok.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChestLoot {

    private static final int MAX_ITEMS = 5;
    private static final int MIN_ITEMS = 3;
    private static final Map<ESchematic.Difficulty, List<ItemStack>> registeredLoot = new HashMap<>();

    public static void loadLoot() {
        Arrays.stream(ESchematic.Difficulty.values()).forEach(moduleDifficulty -> registeredLoot.put(moduleDifficulty, Lists.newCopyOnWriteArrayList()));

        //=================================================//

        addLoot(ESchematic.Difficulty.EASY, new ItemCreator(Material.GOLD_HELMET).setUnbreakable(true).getItemstack(), 25);
        addLoot(ESchematic.Difficulty.EASY, new ItemCreator(Material.GOLD_CHESTPLATE).setUnbreakable(true).getItemstack(), 25);
        addLoot(ESchematic.Difficulty.EASY, new ItemCreator(Material.GOLD_LEGGINGS).setUnbreakable(true).getItemstack(), 25);
        addLoot(ESchematic.Difficulty.EASY, new ItemCreator(Material.GOLD_BOOTS).setUnbreakable(true).getItemstack(), 25);
        addLoot(ESchematic.Difficulty.EASY, new ItemCreator(Material.WOOD_SWORD).setUnbreakable(true).getItemstack(), 15);
        addLoot(ESchematic.Difficulty.EASY, new ItemStack(Material.GOLD_INGOT, 2), 10);
        addLoot(ESchematic.Difficulty.EASY, new ItemStack(Material.GOLD_INGOT, 3), 10);
        addLoot(ESchematic.Difficulty.EASY, new ItemStack(Material.COOKED_BEEF, 3), 50);
        addLoot(ESchematic.Difficulty.EASY, new ItemStack(Material.FLINT), 20);
        addLoot(ESchematic.Difficulty.EASY, new ItemStack(Material.FEATHER), 20);
        addLoot(ESchematic.Difficulty.EASY, new ItemCreator(Material.WOOD_SWORD).setUnbreakable(true).getItemstack(), 20);
        addLoot(ESchematic.Difficulty.EASY, new ItemStack(Material.TNT), 15);
        addLoot(ESchematic.Difficulty.EASY, new ItemStack(Material.WEB), 15);
        addLoot(ESchematic.Difficulty.EASY, new ItemStack(Material.CAKE), 15);

        //=================================================//

        addLoot(ESchematic.Difficulty.NORMAL, new ItemCreator(Material.CHAINMAIL_HELMET).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemCreator(Material.CHAINMAIL_CHESTPLATE).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemCreator(Material.CHAINMAIL_LEGGINGS).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemCreator(Material.CHAINMAIL_BOOTS).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemCreator(Material.STONE_SWORD).setUnbreakable(true).getItemstack(), 25);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemCreator(Material.IRON_INGOT).setUnbreakable(true).getItemstack(), 10);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemStack(Material.APPLE), 30);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemStack(Material.STICK), 20);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemStack(Material.FEATHER), 15);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemStack(Material.FLINT), 15);
//        addLoot(ESchematic.Difficulty.NORMAL, new ItemStack(Material.EXP_BOTTLE, 2), 25);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemStack(Material.TNT), 20);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemStack(Material.WEB), 20);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemStack(Material.CAKE), 15);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemCreator(Material.BOW).setUnbreakable(true).getItemstack(), 15);
        addLoot(ESchematic.Difficulty.NORMAL, new ItemCreator(Material.ARROW).setAmount(new Random().nextInt(3) + 1).getItemstack(), 20);

        //=================================================//

        addLoot(ESchematic.Difficulty.HARD, new ItemCreator(Material.IRON_HELMET).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.HARD, new ItemCreator(Material.IRON_CHESTPLATE).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.HARD, new ItemCreator(Material.IRON_LEGGINGS).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.HARD, new ItemCreator(Material.IRON_BOOTS).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.HARD, new ItemCreator(Material.STONE_SWORD).setUnbreakable(true).getItemstack(), 10);
        addLoot(ESchematic.Difficulty.HARD, new ItemStack(Material.DIAMOND), 15);
//        addLoot(ESchematic.Difficulty.HARD, new ItemStack(Material.ENCHANTMENT_TABLE), 20);
        addLoot(ESchematic.Difficulty.HARD, new ItemStack(Material.WORKBENCH), 30);
//        addLoot(ESchematic.Difficulty.HARD, new ItemStack(Material.EXP_BOTTLE, 3), 25);
//        addLoot(ESchematic.Difficulty.HARD, new ItemCreator(Material.INK_SACK).setDurability((short) 4).setAmount(3).getItemstack(), 40);
        addLoot(ESchematic.Difficulty.HARD, new ItemStack(Material.TNT), 15);
        addLoot(ESchematic.Difficulty.HARD, new ItemStack(Material.WEB), 15);
        addLoot(ESchematic.Difficulty.HARD, new ItemStack(Material.BOW), 20);
        addLoot(ESchematic.Difficulty.HARD, new ItemCreator(Material.ARROW).setAmount(new Random().nextInt(4) + 1).getItemstack(), 15);

        //=================================================//

        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemCreator(Material.IRON_HELMET).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemCreator(Material.IRON_CHESTPLATE).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemCreator(Material.IRON_LEGGINGS).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemCreator(Material.IRON_BOOTS).setUnbreakable(true).getItemstack(), 30);
        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemCreator(Material.IRON_SWORD).setUnbreakable(true).getItemstack(), 25);
        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemStack(Material.DIAMOND, 2), 25);
//        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemStack(Material.ENCHANTMENT_TABLE), 20);
        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemStack(Material.WORKBENCH), 30);
//        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemStack(Material.EXP_BOTTLE, 4), 25);
//        addLoot(ESchematic.Difficulty.HARD, new ItemCreator(Material.INK_SACK).setDurability((short) 4).setAmount(3).getItemstack(), 40);
        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemStack(Material.TNT), 10);
        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemStack(Material.WEB), 10);
        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemStack(Material.BOW), 10);
        addLoot(ESchematic.Difficulty.VERY_HARD, new ItemCreator(Material.ARROW).setAmount(new Random().nextInt(5) + 1).getItemstack(), 10);

        //=================================================//
    }

    private static void addLoot(ESchematic.Difficulty moduleDifficulty, ItemStack itemStack, int percentage) {

        for (int i = 1; i < percentage + 1; i++)
            registeredLoot.get(moduleDifficulty).add(itemStack);
    }

    public static void setChest(Schematic schematic, Block block) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST);

        List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            slots.add(i);
        }
        for (ItemStack items : getRandomLoot(schematic.getDifficulty()).toArray(new ItemStack[]{})) {
            inventory.setItem(slots.remove(new Random().nextInt(slots.size() - 1)), items);
        }
        JLInventoryManager.jlInventories.add(new JLInventory(block, inventory));
    }

    private static List<ItemStack> getRandomLoot(ESchematic.Difficulty moduleDifficulty) {
        List<ItemStack> lootFromDifficulty = Lists.newCopyOnWriteArrayList();
        List<ItemStack> loot = Lists.newCopyOnWriteArrayList();

        registeredLoot.forEach((difficulty, list) -> {
            if (moduleDifficulty == ESchematic.Difficulty.EASY) {
                if (difficulty == ESchematic.Difficulty.EASY) {
                    lootFromDifficulty.addAll(list);
                }
            }
            if (moduleDifficulty == ESchematic.Difficulty.NORMAL) {
                if (difficulty == ESchematic.Difficulty.EASY || difficulty == ESchematic.Difficulty.NORMAL) {
                    lootFromDifficulty.addAll(list);
                }
            }
            if (moduleDifficulty == ESchematic.Difficulty.HARD) {
                if (difficulty == ESchematic.Difficulty.EASY || difficulty == ESchematic.Difficulty.NORMAL || difficulty == ESchematic.Difficulty.HARD) {
                    lootFromDifficulty.addAll(list);
                }
            }
            if (moduleDifficulty == ESchematic.Difficulty.VERY_HARD) {
                if (difficulty == ESchematic.Difficulty.EASY || difficulty == ESchematic.Difficulty.NORMAL || difficulty == ESchematic.Difficulty.HARD || difficulty == ESchematic.Difficulty.VERY_HARD) {
                    lootFromDifficulty.addAll(list);
                }
            }
        });

        int amount = new Random().nextInt(MAX_ITEMS - MIN_ITEMS + 1) + MIN_ITEMS;

        for (int i = 0; i < amount; i++)
            loot.add(lootFromDifficulty.get(new Random().nextInt(lootFromDifficulty.size())));

        return loot;
    }
}
