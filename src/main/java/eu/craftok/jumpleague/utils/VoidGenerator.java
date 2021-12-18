package eu.craftok.jumpleague.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Project jumpleague Created by Sithey
 */

public class VoidGenerator extends ChunkGenerator {

    private BlockPopulator populator = null;

    public VoidGenerator() {
    }

    public VoidGenerator(Biome b) {
        populator = new BlockPopulator() {
            @Override
            public void populate(World world, Random rand, Chunk chunk) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        final Block block = chunk.getBlock(x, 0, z);
                        block.setBiome(b);
                    }
                }
            }
        };
    }

    public void populate(World world, Chunk chunk) {
        populator.populate(world, null, chunk);
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        if (populator != null) {
            return Arrays.asList(populator);
        }
        return Arrays.asList(new BlockPopulator[0]);
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public byte[] generate(World world, Random rand, int chunkx, int chunkz) {
        return new byte[32768];
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 0, 0);
    }

}
