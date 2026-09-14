package net.dusty_dusty.cts_compats.registry;

import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.Set;

public interface IResourceOptionRegistry {
    <T> Map<String, Set<T>> getOptionsOfType(Class<T> clazz);

    <T> Set<T> getOptions(Class<?> clazz, String blockId);

    void register();

    default <T> Set<T> getOptions(Class<T> clazz, Block block) {
        String[] components = block.getDescriptionId().split("\\.");
        return this.getOptions(clazz, components[1] + ":" + components[2]);
    }
}
