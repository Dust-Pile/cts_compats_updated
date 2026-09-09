package net.dusty_dusty.cts_compats.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.Set;

public interface IResourceOptionRegistry {
    <T> Map<Block, Set<T>> getOptionsOfType(Class<T> clazz);

    <T> Set<T> getOptions(Class<?> clazz, Block block);

    void register();
}
