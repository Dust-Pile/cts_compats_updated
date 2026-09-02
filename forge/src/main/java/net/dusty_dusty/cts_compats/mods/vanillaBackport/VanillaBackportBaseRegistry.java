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
public final class VanillaBackportBaseRegistry extends AbstractRegistry {
    private static final VanillaBackportBaseRegistry INSTANCE = new VanillaBackportBaseRegistry( CTSCompats.VB_MODID );
    public static VanillaBackportBaseRegistry getInstance() {
        return INSTANCE;
    }

    private VanillaBackportBaseRegistry(String modId) {
        super(modId);
    }

    public static final RegistryObject<Block> PALE_MOSS_SLAB = INSTANCE.registerBlock( "pale_moss_slab",
            () -> new CustomSlab( ModBlocks.PALE_MOSS_BLOCK.get() ) );

    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.empty();
    }
}
