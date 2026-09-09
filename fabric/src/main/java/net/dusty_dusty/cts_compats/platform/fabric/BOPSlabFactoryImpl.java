package net.dusty_dusty.cts_compats.platform.fabric;

import net.countered.terrainslabs.block.customslabs.specialslabs.GravityAffectedSlab;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.DriedSaltSlab;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.MossyBlackSandSlab;
import net.minecraft.world.level.block.Block;

public final class BOPSlabFactoryImpl {
    public static Block sand(Block originalBlock) {
        return new GravityAffectedSlab(originalBlock);
    }

    public static Block mossyBlackSand(Block originalBlock) {
        return new MossyBlackSandSlab(originalBlock);
    }

    public static Block driedSalt(Block originalBlock) {
        return new DriedSaltSlab(originalBlock);
    }
}
