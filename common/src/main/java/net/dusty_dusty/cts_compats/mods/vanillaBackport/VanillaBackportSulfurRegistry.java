package net.dusty_dusty.cts_compats.mods.vanillaBackport;

import com.blackgear.vanillabackport.common.level.blocks.SpeleothemBlock;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import dev.architectury.registry.registries.RegistrySupplier;
import net.countered.terrainslabs.api.OffsetClasses;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.registry.IColorRegistry;
import net.minecraft.world.level.block.Block;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class VanillaBackportSulfurRegistry extends AbstractRegistry {
    private static final VanillaBackportBaseRegistry INSTANCE = VanillaBackportBaseRegistry.getInstance();
    public static VanillaBackportBaseRegistry getInstance() {
        return INSTANCE;
    }

    private VanillaBackportSulfurRegistry(String modId) {
        super(modId);
    }

    public static final RegistrySupplier<Block> SULFUR_SLAB = INSTANCE.registerBlock( "sulfur_slab",
            () -> new CustomSlab( ModBlocks.SULFUR.get() ) );
    public static final RegistrySupplier<Block> CINNABAR_SLAB = INSTANCE.registerBlock( "cinnabar_slab",
            () -> new CustomSlab( ModBlocks.CINNABAR.get() ) );

    @Override
    public Optional<Supplier<IColorRegistry>> getColorRegistry() {
        return Optional.empty();
    }

    static {
        registerOffsetClasses( OffsetClasses.Category.ONTOP_VEGETATION, Set.of(
                SpeleothemBlock.class
        ) );

        registerOffsetClasses( OffsetClasses.Category.ONBOTTOM_VEGETATION, Set.of(
                SpeleothemBlock.class
        ) );
    }
}
