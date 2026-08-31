package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import biomesoplenty.block.*;
import net.countered.terrainslabs.api.OffsetClasses;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.DriedSaltSlab;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.FleshSlab;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.MossyBlackSandSlab;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.SandSlabBlockBOP;
import net.dusty_dusty.cts_compats.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.registry.IColorRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

@SuppressWarnings("unused")
public final class BOPBaseRegistry extends AbstractRegistry {
    public static final BOPBaseRegistry INSTANCE = new BOPBaseRegistry( CTSCompats.BOP_MODID );
    public Optional<IColorRegistry> colorRegistry;
    public BOPBaseRegistry( String modId ) {
        super(modId);
        colorRegistry = Optional.of( new BOPBaseColorRegistry() );
    }

    public static BOPBaseRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return colorRegistry;
    }

    // Overworld Blocks
    public static final RegistryObject<Block> WHITE_SAND_SLAB = INSTANCE.registerBlock( "white_sand_slab",
            () -> new SandSlabBlockBOP(  BOPReference.WHITE_SAND ) );
    public static final RegistryObject<Block> ORANGE_SAND_SLAB = INSTANCE.registerBlock( "orange_sand_slab",
            () -> new SandSlabBlockBOP(  BOPReference.ORANGE_SAND ) );
    public static final RegistryObject<Block> BLACK_SAND_SLAB = INSTANCE.registerBlock( "black_sand_slab",
            () -> new SandSlabBlockBOP(  BOPReference.BLACK_SAND ) );
    // TODO: mossy black sand texture
    public static final RegistryObject<Block> MOSSY_BLACK_SAND_SLAB = INSTANCE.registerBlock( "mossy_black_sand_slab",
            () -> new MossyBlackSandSlab(  BOPReference.MOSSY_BLACK_SAND ) );
    public static final RegistryObject<Block> DRIED_SALT_SLAB = INSTANCE.registerBlock( "dried_salt_slab",
            () -> new DriedSaltSlab(  BOPReference.DRIED_SALT ) );

        // Nether Blocks
    public static final RegistryObject<Block> FLESH_SLAB = INSTANCE.registerBlock( "flesh_slab",
            () -> new FleshSlab( BOPReference.FLESH ) );
    public static final RegistryObject<Block> POROUS_FLESH_SLAB = INSTANCE.registerBlock( "porous_flesh_slab",
            () -> new FleshSlab( BOPReference.POROUS_FLESH ) );
    public static final RegistryObject<Block> BRIMSTONE_SLAB = INSTANCE.registerBlock( "brimstone_slab",
            () -> new CustomSlab( BOPReference.BRIMSTONE ) );

    @Override
    @SuppressWarnings("removal")
    public void clientSetup() {
        ItemBlockRenderTypes.setRenderLayer( MOSSY_BLACK_SAND_SLAB.get(), RenderType.cutoutMipped() );
    }

}