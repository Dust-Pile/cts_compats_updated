package net.dusty_dusty.cts_compats.mods;

import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.registry.IColorRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.satisfy.meadow.core.registry.ObjectRegistry;

import java.util.Optional;

public final class QuarkRegistry extends AbstractRegistry {
    private static final QuarkRegistry INSTANCE = new QuarkRegistry( CTSCompats.QUARK_MODID );
    public static QuarkRegistry getInstance() {
        return INSTANCE;
    }

    private QuarkRegistry(String modId) {
        super(modId);
    }

    public static final RegistryObject<Block> LIMESTONE_SLAB = INSTANCE.registerBlock( "quark_limestone_slab",
            () -> new CustomSlab(
                    ForgeRegistries.BLOCKS.getValue(
                            ResourceLocation.fromNamespaceAndPath( CTSCompats.QUARK_MODID, "limestone" ) )
            ) );

    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.empty();
    }
}
