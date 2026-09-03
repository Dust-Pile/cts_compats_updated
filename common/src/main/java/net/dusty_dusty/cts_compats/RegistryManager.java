package net.dusty_dusty.cts_compats;

import dev.architectury.platform.Platform;
import net.dusty_dusty.cts_compats.registry.IRegistry;
import net.dusty_dusty.cts_compats.registry.Version;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class RegistryManager {
    private static final RegistryManager INSTANCE = new RegistryManager();
    private static final Map<String, Version> LOADED_VERSIONS = new HashMap<>();
    private static final Map<String, IRegistry> LOADED_MODS = new HashMap<>();

    private final List<IRegistry> registries = new ArrayList<>();

    private RegistryManager() {
    }

    public static RegistryManager getInstance() {
        return INSTANCE;
    }

    public static IRegistry getRegistry(String modId) {
        return LOADED_MODS.get(modId);
    }

    public static Version getVersion(String modId) {
        return LOADED_VERSIONS.get(modId);
    }

    public static void forEachRegistry(Consumer<IRegistry> consumer) {
        INSTANCE.registries.forEach(consumer);
    }

    public static void forEachRegistryAndID(BiConsumer<String, IRegistry> consumer) {
        LOADED_MODS.forEach(consumer);
    }

    public static List<Block> getAllBlocks() {
        List<Block> blocks = new ArrayList<>();
        forEachRegistry(registry -> registry.getRegistryBlocks().forEach(block -> blocks.add(block.get())));
        return blocks;
    }

    public boolean register(String modId, Supplier<IRegistry> registrySupplier) {
        if (!Platform.isModLoaded(modId)) {
            return false;
        }

        Version version = new Version(Platform.getMod(modId).getVersion());
        LOADED_VERSIONS.put(modId, version);
        CTSCompats.LOGGER.info("Loading compatibility for {} version {}.", modId, version);
        register(modId, registrySupplier.get());
        return true;
    }

    void register(String modId, IRegistry registry) {
        registries.add(registry);
        LOADED_MODS.put(modId, registry);
        registry.register();
    }

    public void assign() {
        registries.forEach(IRegistry::assign);
    }
}
