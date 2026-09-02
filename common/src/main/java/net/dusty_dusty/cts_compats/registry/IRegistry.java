package net.dusty_dusty.cts_compats.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public interface IRegistry {
    String getModID();

    void assign();

    void register();

    Collection<RegistrySupplier<? extends Block>> getRegistryBlocks();

    Collection<Block> getCutoutBlocks();

    Collection<Block> getCutoutMippedBlocks();

    Optional<Supplier<IColorRegistry>> getColorRegistry();

    default void clientSetup() {
    }

    default Block getBlock(String name) {
        return getBlock(getModID(), name);
    }

    static Block getBlock(String modId, String name) {
        return BuiltInRegistries.BLOCK.get(new ResourceLocation(modId, name));
    }
}
