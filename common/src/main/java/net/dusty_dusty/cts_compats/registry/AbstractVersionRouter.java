package net.dusty_dusty.cts_compats.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.minecraft.world.level.block.Block;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractVersionRouter implements IRegistry {
    public static final IRegistry EMPTY_REGISTRY = new EmptyRegistry();

    private final Map<? extends Comparable<Version>, Supplier<IRegistry>> versionFilter;
    final IRegistry registry;
    protected final String registryId;

    protected AbstractVersionRouter(String modId, Map<? extends Comparable<Version>, Supplier<IRegistry>> versionFilter) {
        this.versionFilter = versionFilter;
        registry = getRegistryFromVersion(modId, RegistryManager.getVersion(modId));
        registryId = modId;
    }

    public static boolean isEmpty(IRegistry registry) {
        return registry == EMPTY_REGISTRY;
    }

    @Override
    public String getModID() {
        return registryId;
    }

    private IRegistry getRegistryFromVersion(String modId, Version version) {
        Set<? extends Comparable<Version>> filters = versionFilter.keySet();
        for (Comparable<Version> filter : filters) {
            if (filter.compareTo(version) == 0) {
                return versionFilter.get(filter).get();
            }
        }
        CTSCompats.LOGGER.error(
                "No filter matches version {} of mod {} in registered version router (no compatibility available for mod version).",
                version,
                modId
        );
        return EMPTY_REGISTRY;
    }

    @Override
    public void clientSetup() {
        registry.clientSetup();
    }

    @Override
    public void register() {
        registry.register();
    }

    @Override
    public void assign() {
        registry.assign();
    }

    @Override
    public Optional<Supplier<IColorRegistry>> getColorRegistry() {
        return registry.getColorRegistry();
    }

    @Override
    public Collection<RegistrySupplier<? extends Block>> getRegistryBlocks() {
        return registry.getRegistryBlocks();
    }

    @Override
    public Collection<Block> getCutoutBlocks() {
        return registry.getCutoutBlocks();
    }

    @Override
    public Collection<Block> getCutoutMippedBlocks() {
        return registry.getCutoutMippedBlocks();
    }

    private static final class EmptyRegistry extends AbstractRegistry {
        private EmptyRegistry() {
            super(" ");
        }

        @Override
        public Optional<Supplier<IColorRegistry>> getColorRegistry() {
            return Optional.empty();
        }
    }
}
