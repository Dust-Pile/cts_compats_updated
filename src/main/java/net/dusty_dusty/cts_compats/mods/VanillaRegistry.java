package net.dusty_dusty.cts_compats.mods;

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

//    // Unique
//    public static final RegistryObject<Block> DRIPSTONE_SLAB =
//            INSTANCE.registerBlock( "dripstone_slab", () -> new CustomSlabBlock( Blocks.DRIPSTONE_BLOCK ) );

//    // Light Sources
//    public static final RegistryObject<Block> TORCH_ON_TOP =
//            INSTANCE.registerBlock( "torch_on_top", () -> new TorchOnTop( Blocks.TORCH, ParticleTypes.FLAME ) );
//    public static final RegistryObject<Block> SOUL_TORCH_ON_TOP =
//            INSTANCE.registerBlock( "soul_torch_on_top", () -> new TorchOnTop( Blocks.SOUL_TORCH, ParticleTypes.SOUL ) );
//    public static final RegistryObject<Block> LANTERN_ON_TOP =
//            INSTANCE.registerBlock( "lantern_on_top", () -> new LanternOnTop( Blocks.LANTERN ) );
//    public static final RegistryObject<Block> SOUL_LANTERN_ON_TOP =
//            INSTANCE.registerBlock( "soul_lantern_on_top", () -> new LanternOnTop( Blocks.SOUL_LANTERN ) );

    @Override
    protected void assignExtras() {
        // IAssignable.AssignUtil.putOnTopVegetation( Blocks.MOSS_CARPET, Blocks.AIR );
    }
}
