package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public final class BOPReference {

    public static final Supplier<Block> WHITE_SAND = getBlock("white_sand");
    public static final Supplier<Block> ORANGE_SAND = getBlock("orange_sand");
    public static final Supplier<Block> MOSSY_BLACK_SAND = getBlock("mossy_black_sand");
    public static final Supplier<Block> BLACK_SAND = getBlock("black_sand");
    public static final Supplier<Block> DRIED_SALT = getBlock("dried_salt");
    public static final Supplier<Block> FLESH = getBlock("flesh");
    public static final Supplier<Block> POROUS_FLESH = getBlock("porous_flesh");
    public static final Supplier<Block> BRIMSTONE = getBlock("brimstone");

    private static Supplier<Block> getBlock(String name) {
        ResourceLocation id = new ResourceLocation(CTSCompats.BOP_MODID, name);
        return () -> BuiltInRegistries.BLOCK.get(id);
    }
}
