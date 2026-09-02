package net.dusty_dusty.cts_compats.mods;

import dev.orderedchaos.projectvibrantjourneys.common.blocks.*;
import net.countered.terrainslabs.api.OffsetClasses;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.registry.AbstractRegistry;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class PVJRegistry extends AbstractRegistry {
    private static final PVJRegistry INSTANCE = new PVJRegistry( CTSCompats.PVJ_MODID );
    private PVJRegistry(String modId) {
        super(modId);
    }

    public static PVJRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Supplier<net.dusty_dusty.cts_compats.registry.IColorRegistry>> getColorRegistry() {
        return Optional.empty();
    }

    static {
        registerOffsetClasses( OffsetClasses.Category.ONTOP_VEGETATION, Set.of(
                CindercaneBlock.class, FallenLeavesBlock.class, GroundcoverBlock.class,
                BeachedKelpBlock.class, IcicleBlock.class
        ) );

        registerOffsetClasses( OffsetClasses.Category.ONBOTTOM_VEGETATION, Set.of(
                IcicleBlock.class
        ) );
    }
}
