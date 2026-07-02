package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BOPReference {

//    public static SimpleParticleType PUS = getParticle( "pus" );

    public static Block WHITE_SAND = getBlock( "white_sand" );
    public static Block ORANGE_SAND = getBlock( "orange_sand" );
    public static Block MOSSY_BLACK_SAND = getBlock( "mossy_black_sand" );
    public static Block BLACK_SAND = getBlock( "black_sand" );
    public static Block DRIED_SALT = getBlock( "dried_salt" );
    public static Block FLESH = getBlock( "flesh" );
    public static Block POROUS_FLESH = getBlock( "porous_flesh" );
//    public static Block EYEBULB = getBlock( "eyebulb" );
//    public static Block HAIR = getBlock( "hair" );
//    public static Block PUS_BUBBLE = getBlock( "pus_bubble" );
    public static Block BRIMSTONE = getBlock( "brimstone" );
//    public static Block BRIMSTONE_FUMAROLE = getBlock( "brimstone_fumarole" );
//    public static Block BRIMSTONE_CLUSTER = getBlock( "brimstone_cluster" );
//    public static Block BRIMSTONE_BUD = getBlock( "brimstone_bud" );
//    public static Block BLACKSTONE_SPINES = getBlock( "blackstone_spines" );
//    public static Block BLACKSTONE_BULB = getBlock( "blackstone_bulb" );
//    public static Block TOADSTOOL = getBlock( "toadstool" );
//    public static Block ROSE = getBlock( "rose" );
//    public static Block VIOLET = getBlock( "violet" );
//    public static Block LAVENDER = getBlock( "lavender" );
//    public static Block TALL_LAVENDER = getBlock( "tall_lavender" );
//    public static Block BLUE_HYDRANGEA = getBlock( "blue_hydrangea" );
//    public static Block WILDFLOWER = getBlock( "wildflower" );
//    public static Block GOLDENROD = getBlock( "goldenrod" );
//    public static Block ORANGE_COSMOS = getBlock( "orange_cosmos" );
//    public static Block PINK_DAFFODIL = getBlock( "pink_daffodil" );
//    public static Block PINK_HIBISCUS = getBlock( "pink_hibiscus" );
//    public static Block WHITE_PETALS = getBlock( "white_petals" );
//    public static Block ICY_IRIS = getBlock( "icy_iris" );
//    public static Block GLOWFLOWER = getBlock( "glowflower" );
//    public static Block WILTED_LILY = getBlock( "wilted_lily" );
//    public static Block BURNING_BLOSSOM = getBlock( "burning_blossom" );
//    public static Block SPROUT = getBlock( "sprout" );
//    public static Block BUSH = getBlock( "bush" );
//    public static Block CLOVER = getBlock( "clover" );
//    public static Block DUNE_GRASS = getBlock( "dune_grass" );
//    public static Block DESERT_GRASS = getBlock( "desert_grass" );
//    public static Block DEAD_GRASS = getBlock( "dead_grass" );
//    public static Block TUNDRA_SHRUB = getBlock( "tundra_shrub" );
//    public static Block BARLEY = getBlock( "barley" );
//    public static Block SEA_OATS = getBlock( "sea_oats" );
//    public static Block CATTAIL = getBlock( "cattail" );
//    public static Block REED = getBlock( "reed" );
//    public static Block WATERGRASS = getBlock( "watergrass" );

    private static Block getBlock( String name ) {
        return ForgeRegistries.BLOCKS.getValue( ResourceLocation.fromNamespaceAndPath( CTSCompats.BOP_MODID, name ) );
    }

//    @SuppressWarnings("SameParameterValue")
//    private static SimpleParticleType getParticle(String name ) {
//        return (SimpleParticleType) ForgeRegistries.PARTICLE_TYPES.getValue( ResourceLocation.fromNamespaceAndPath(
//                CTSCompats.BOP_MODID, name )
//        );
//    }

}