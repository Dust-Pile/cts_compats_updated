package net.dusty_dusty.cts_compats.mods;

import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.dusty_dusty.cts_compats.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.registry.IColorRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public final class VanillaRegistry extends AbstractRegistry {
    private static final VanillaRegistry INSTANCE = new VanillaRegistry( "minecraft" );
    protected VanillaRegistry(String modId) {
        super(modId);
    }

    public static AbstractRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.empty();
    }

    public static final RegistryObject<Block> DRIPSTONE_SLAB = INSTANCE.registerBlock( "dripstone_slab",
            () -> new CustomSlab( Blocks.DRIPSTONE_BLOCK ) );
}
