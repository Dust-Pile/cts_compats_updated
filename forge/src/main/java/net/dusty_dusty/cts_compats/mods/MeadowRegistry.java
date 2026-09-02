package net.dusty_dusty.cts_compats.mods;

import dev.orderedchaos.projectvibrantjourneys.common.blocks.*;
import dev.architectury.registry.registries.RegistrySupplier;
import net.countered.terrainslabs.api.OffsetClasses;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.registry.IColorRegistry;
import net.minecraft.world.level.block.Block;
import net.satisfy.meadow.core.block.*;
import net.satisfy.meadow.core.registry.ObjectRegistry;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class MeadowRegistry extends AbstractRegistry {
    private static final MeadowRegistry INSTANCE = new MeadowRegistry( CTSCompats.MEADOW_MODID );
    public static MeadowRegistry getInstance() {
        return INSTANCE;
    }

    private MeadowRegistry(String modId) {
        super(modId);
    }

    public static RegistrySupplier<Block> LIMESTONE_SLAB = INSTANCE.registerBlock( "limestone_slab",
            () -> new CustomSlab( ObjectRegistry.LIMESTONE.get() ) );

    @Override
    public Optional<Supplier<IColorRegistry>> getColorRegistry() {
        return Optional.empty();
    }

    static {
        registerOffsetClasses( OffsetClasses.Category.ONTOP_MISC, Set.of(
                OilLantern.class, StorageBlock.class, DoormatBlock.class, CameraBlock.class,
                FireLog.class, CanBlock.class
        ) );
    }
}
