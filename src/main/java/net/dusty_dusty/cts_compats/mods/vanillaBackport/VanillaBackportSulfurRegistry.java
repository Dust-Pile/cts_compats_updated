package net.dusty_dusty.cts_compats.mods.vanillaBackport;

import com.blackgear.vanillabackport.common.registries.ModBlocks;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.registry.IColorRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

@SuppressWarnings("unused")
public class VanillaBackportSulfurRegistry extends AbstractRegistry {
    private static final VanillaBackportBaseRegistry INSTANCE = VanillaBackportBaseRegistry.getInstance();
    public static VanillaBackportBaseRegistry getInstance() {
        return INSTANCE;
    }

    protected VanillaBackportSulfurRegistry(String modId) {
        super(modId);
    }

    public static final RegistryObject<Block> SULFUR_SLAB = INSTANCE.registerBlock( "sulfur_slab",
            () -> new CustomSlab( ModBlocks.SULFUR.get() ) );
    public static final RegistryObject<Block> CINNABAR_SLAB = INSTANCE.registerBlock( "cinnabar_slab",
            () -> new CustomSlab( ModBlocks.CINNABAR.get() ) );

    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.empty();
    }
}
