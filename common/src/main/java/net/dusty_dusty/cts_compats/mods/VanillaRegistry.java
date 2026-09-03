package net.dusty_dusty.cts_compats.mods;

import dev.architectury.registry.registries.RegistrySupplier;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.dusty_dusty.cts_compats.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.registry.IColorRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;
import java.util.function.Supplier;

public final class VanillaRegistry extends AbstractRegistry {
    private static final VanillaRegistry INSTANCE = new VanillaRegistry("minecraft");

    public static final RegistrySupplier<Block> DRIPSTONE_SLAB = INSTANCE.registerBlock(
            "dripstone_slab",
            () -> new CustomSlab(Blocks.DRIPSTONE_BLOCK)
    );

    private VanillaRegistry(String modId) {
        super(modId);
    }

    public static AbstractRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Supplier<IColorRegistry>> getColorRegistry() {
        return Optional.empty();
    }
}
