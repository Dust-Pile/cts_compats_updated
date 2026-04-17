package net.dusty_dusty.cts_compats.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Optional;

public interface IRegistry {
    String PVJ_MODID = "projectvibrantjourneys";
    String BOP_MODID = "biomesoplenty";
    String VB_MODID = "vanillabackport";
    String MEADOW_MODID = "meadow";
    String IW_MODID = "immersive_weathering";
    String QUARK_MODID = "quark";

    String getModID();

    void assign();

    void register( IEventBus modEventBus );

    Collection<RegistryObject<Block>> getRegistryBlocks();

    Optional<IColorRegistry> getColorRegistry();

    default void clientSetup() {}

    default Block getBlock( String name ) {
        return getBlock( getModID(), name );
    }

    static Block getBlock( String modId, String name ) {
        return ForgeRegistries.BLOCKS.getValue( ResourceLocation.fromNamespaceAndPath( modId, name ) );
    }
}