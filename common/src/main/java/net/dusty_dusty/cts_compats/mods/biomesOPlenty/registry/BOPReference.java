package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public final class BOPReference {

    public static Block WHITE_SAND = getBlock( "white_sand" );
    public static Block ORANGE_SAND = getBlock( "orange_sand" );
    public static Block MOSSY_BLACK_SAND = getBlock( "mossy_black_sand" );
    public static Block BLACK_SAND = getBlock( "black_sand" );
    public static Block DRIED_SALT = getBlock( "dried_salt" );
    public static Block FLESH = getBlock( "flesh" );
    public static Block POROUS_FLESH = getBlock( "porous_flesh" );
    public static Block BRIMSTONE = getBlock( "brimstone" );

    private static Block getBlock( String name ) {
        return BuiltInRegistries.BLOCK.get(new ResourceLocation(CTSCompats.BOP_MODID, name));
    }
}
