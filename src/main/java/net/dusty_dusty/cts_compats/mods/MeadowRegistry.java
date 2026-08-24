package net.dusty_dusty.cts_compats.mods;

import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.registry.IColorRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.satisfy.meadow.core.registry.ObjectRegistry;

import java.util.Optional;

@SuppressWarnings("unused")
public class MeadowRegistry extends AbstractRegistry {
    private static final MeadowRegistry INSTANCE = new MeadowRegistry( CTSCompats.MEADOW_MODID );
    public static MeadowRegistry getInstance() {
        return INSTANCE;
    }

    protected MeadowRegistry(String modId) {
        super(modId);
    }

    public static RegistryObject<Block> LIMESTONE_SLAB = INSTANCE.registerBlockCutout( "limestone_slab",
            () -> new CustomSlab( ObjectRegistry.LIMESTONE.get() ) );

    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.empty();
    }
}
