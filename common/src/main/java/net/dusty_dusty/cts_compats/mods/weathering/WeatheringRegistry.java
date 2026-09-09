package net.dusty_dusty.cts_compats.mods.weathering;

import com.ordana.immersive_weathering.reg.ModBlocks;
import dev.architectury.registry.registries.RegistrySupplier;
import net.countered.terrainslabs.block.customslabs.apiSlabs.FallableSnowyGrassySlab;
import net.countered.terrainslabs.block.customslabs.soilslabs.SnowyGrassySlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.block.customslabs.specialslabs.GravityAffectedSlab;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.countered.terrainslabs.registries.ModBlocksRegistry;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.common.CustomDuelSlab;
import net.dusty_dusty.cts_compats.mods.weathering.block.*;
import net.dusty_dusty.cts_compats.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.registry.IColorRegistry;
import net.dusty_dusty.cts_compats.registry.IResourceOptionRegistry;
import net.minecraft.world.level.block.Block;

import java.util.Optional;
import java.util.function.Supplier;

public class WeatheringRegistry extends AbstractRegistry {
    private static final WeatheringRegistry INSTANCE = new WeatheringRegistry( CTSCompats.IW_MODID );
    public static WeatheringRegistry getInstance() {
        return INSTANCE;
    }

    protected WeatheringRegistry( String modId ) {
        super(modId);
    }

//    public static final RegistrySupplier<Block> EARTHEN_CLAY_SLAB = INSTANCE.registerBlock( "earthen_clay_slab",
//            () -> WeatheringSlabFactory.earthenClay(ModBlocks.EARTHEN_CLAY.get()) );
//    public static final RegistrySupplier<Block> GRASSY_EARTHEN_CLAY_SLAB = INSTANCE.registerBlock( "grassy_earthen_clay_slab",
//            () -> WeatheringSlabFactory.grassyEarthenClay(ModBlocks.GRASSY_EARTHEN_CLAY.get()) );

    public static final RegistrySupplier<Block> SANDY_DIRT_SLAB = INSTANCE.registerBlock( "sandy_dirt_slab",
            () -> new GravityAffectedSlab(ModBlocks.SANDY_DIRT.get()) );
    public static final RegistrySupplier<Block> GRASSY_SANDY_DIRT_SLAB = INSTANCE.registerBlockCutoutMipped( "grassy_sandy_dirt_slab",
            () -> new FallableSnowyGrassySlab(ModBlocks.GRASSY_SANDY_DIRT.get(), (ISlabCopy) WeatheringRegistry.SANDY_DIRT_SLAB.get()) );

    public static final RegistrySupplier<Block> SILT_SLAB = INSTANCE.registerBlock( "silt_slab",
            () -> new CustomSlab(ModBlocks.SILT.get()) );
    public static final RegistrySupplier<Block> GRASSY_SILT_SLAB = INSTANCE.registerBlockCutoutMipped( "grassy_silt_slab",
            () -> new SnowyGrassySlab(ModBlocks.GRASSY_SILT.get(), (ISlabCopy) WeatheringRegistry.SILT_SLAB.get()) );

    public static final RegistrySupplier<Block> PERMAFROST_SLAB = INSTANCE.registerBlock( "permafrost_slab",
            () -> new PermafrostSlab(ModBlocks.PERMAFROST.get()) );
    public static final RegistrySupplier<Block> GRASSY_PERMAFROST = INSTANCE.registerBlockCutoutMipped( "grassy_permafrost_slab",
            () -> new GrassyPermafrostSlab(ModBlocks.GRASSY_PERMAFROST.get(), (ISlabCopy) WeatheringRegistry.PERMAFROST_SLAB.get()) );

    public static final RegistrySupplier<Block> LOAM_SLAB = INSTANCE.registerBlock( "loam_slab",
            () -> new CustomDuelSlab(ModBlocks.LOAM.get(), (ISlabCopy) ModBlocksRegistry.DIRT_SLAB.get()) );
    public static final RegistrySupplier<Block> ROOTED_GRASS_SLAB = INSTANCE.registerBlockCutoutMipped( "rooted_grass_slab",
            () -> new RootedGrassSlab(ModBlocks.ROOTED_GRASS_BLOCK.get(), (ISlabCopy) ModBlocksRegistry.ROOTED_DIRT_SLAB.get()) );



    @Override
    public Optional<Supplier<IColorRegistry>> getColorRegistry() {
        return Optional.of( () -> new WeatheringColorRegistry() );
    }

    @Override
    public Optional<Supplier<IResourceOptionRegistry>> getResourceOptions() {
        return Optional.of( () -> new WeatheringOptionRegistry() );
    }
}
