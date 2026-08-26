package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.block.*;
import net.countered.terrainslabs.api.OffsetClasses;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

import static net.dusty_dusty.cts_compats.registry.AbstractRegistry.registerOffsetClasses;

@SuppressWarnings("unused")
public final class BOPBetaRegistry {
    public static BOPBaseRegistry INSTANCE = BOPBaseRegistry.getInstance();
    public static BOPBaseRegistry getInstance() {
        return INSTANCE;
    }

    private static Block getBlock( String name ) {
        return getBlock( CTSCompats.BOP_MODID, name );
    }
    private static Block getBlock( String modId, String name ) {
        return ForgeRegistries.BLOCKS.getValue( ResourceLocation.fromNamespaceAndPath( modId, name ) );
    }

    // Slabs
    public static RegistryObject<Block> ALGAL_END_STONE_SLAB = INSTANCE.registerBlock( "algal_end_stone_slab",
            () -> new AlgalEndStoneSlab( BOPBlocks.ALGAL_END_STONE, getBlock( "terrainslabs", "endstone_slab" ) ) );
    public static RegistryObject<Block> THERMAL_CALCITE_SLAB = INSTANCE.registerBlock( "thermal_calcite_slab",
            () -> new ThermalCalciteSlab( BOPBlocks.THERMAL_CALCITE ) );
    public static RegistryObject<Block> THERMAL_CALCITE_VENT_SLAB = INSTANCE.registerBlock( "thermal_calcite_vent_slab",
            () -> new ThermalCalciteVentSlab( BOPBlocks.THERMAL_CALCITE_VENT ) );

    static {
        registerOffsetClasses( OffsetClasses.Category.ONTOP_VEGETATION, Set.of(
                BlackstoneDecorationBlock.class,
                BrimstoneBudBlock.class,
                BrimstoneFumaroleBlock.class,
                HairBlock.class,
                PusBubbleBlock.class,
                SpiderEggBlock.class,
                BrambleLeavesBlock.class
        ) );

        registerOffsetClasses( OffsetClasses.Category.ONBOTTOM_VEGETATION, Set.of(
                BrambleLeavesBlock.class
        ) );
    }
}