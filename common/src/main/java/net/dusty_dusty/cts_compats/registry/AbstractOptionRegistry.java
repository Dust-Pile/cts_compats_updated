package net.dusty_dusty.cts_compats.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import net.dusty_dusty.cts_compats.resources.ResourceOptions;
import net.minecraft.world.level.block.Block;

import java.util.*;

import static net.dusty_dusty.cts_compats.resources.ResourceOptions.IResourceOption;

public abstract class AbstractOptionRegistry implements IResourceOptionRegistry {
    private static final Map<Class<?>, Map<Block, Set<IResourceOption>>> GLOBAL_OPTIONS =
            initializeOptionTypes(new HashMap<>());

    public static IResourceOptionRegistry getGlobalOptions() {
        return new AbstractOptionRegistry( GLOBAL_OPTIONS ){
            @Override
            public void register(OptionProvider provider) {}
        };
    }



    private final Map<Class<?>, Map<RegistrySupplier<Block>, Set<IResourceOption>>> options;
    private Map<Class<?>, Map<Block, Set<IResourceOption>>> convertedOptions;

    public AbstractOptionRegistry() {
        options = new HashMap<>();
        initializeOptionTypes(options);
    }
    private AbstractOptionRegistry(Map<Class<?>, Map<Block, Set<IResourceOption>>> convertedOptions) {
        options = null;
        this.convertedOptions = convertedOptions;
    }

    @SuppressWarnings("unchecked")
    public final <T> Map<Block, Set<T>> getOptionsOfType(Class<T> clazz) {
        throwUnsupported(clazz);
        Map<Block, Set<T>> unprotectedMap = new HashMap<>();
        for (Map.Entry<Block, Set<IResourceOption>> entry : convertedOptions.get(clazz).entrySet()) {
            unprotectedMap.put(entry.getKey(), new HashSet<>((Set<T>) entry.getValue()));
        }
        return unprotectedMap;
    }

    @SuppressWarnings("unchecked")
    public final <T> Set<T> getOptions(Class<?> clazz, Block block) {
        throwUnsupported(clazz);
        Set<T> options = (Set<T>) convertedOptions.get(clazz).get(block);
        return new HashSet<>(options == null ? Set.of() : options);
    }

    public final void register() {
        register(new OptionProvider(this));
        convertOptions();
    }

    public abstract void register(OptionProvider provider);



    public static final class OptionProvider {
        private final AbstractOptionRegistry owner;

        OptionProvider(AbstractOptionRegistry owner) {
            this.owner = owner;
        }

        @SafeVarargs
        public final <T extends IResourceOption> OptionProvider addOptions(RegistrySupplier<Block> block, T... options) {
            owner.addOptions(block, Set.of(options));
            return this;
        }

        @SafeVarargs
        public final <T extends IResourceOption> OptionProvider addBlocksForOption(T option, RegistrySupplier<Block>... blocks) {
            for (RegistrySupplier<Block> block : blocks) {
                owner.addOptions(block, Set.of(option));
            }
            return this;
        }

        @SafeVarargs
        public final <T extends IResourceOption> OptionProvider addBlocksForOptions(Set<T> options, RegistrySupplier<Block>... blocks) {
            for (RegistrySupplier<Block> block : blocks) {
                owner.addOptions(block, new HashSet<>(options));
            }
            return this;
        }
    }



    private static <K, V> Map<Class<?>, Map<K, V>> initializeOptionTypes(Map<Class<?>, Map<K, V>> options) {
        for (Class<? extends IResourceOption> option : ResourceOptions.availableTypes()) {
            options.put(option, new HashMap<>());
        }

        return options;
    }

    @SuppressWarnings("unchecked")
    private static <K, T, V extends Set<T>> void subMapUnion(Map<Class<?>, Map<K, V>> map, Class<?> clazz, K key, Object value) {
        Map<K, V> specific = map.get(clazz);
        if (!specific.containsKey(key)) {
            specific.put(key, (V) value);
        } else {
            specific.get(key).addAll((V) value);
        }
    }

    private <T extends IResourceOption> void addOptions(RegistrySupplier<Block> block, Set<T> options) {
        Class<?> inputType = options.getClass().arrayType();
        throwUnsupported(inputType);
        subMapUnion(this.options, inputType, block, options);
    }

    private void throwUnsupported(Class<?> clazz) {
        if (this.options == null ? !this.convertedOptions.containsKey(clazz) : !this.options.containsKey(clazz)) {
            throw new IllegalArgumentException("No Option Registered for type " + clazz.getName());
        }
    }

    private void convertOptions() {
        if (convertedOptions != null) {
            return;
        }

        Map<Class<?>, Map<Block, Set<IResourceOption>>> converted = new HashMap<>();
        initializeOptionTypes(converted);
        for (Map.Entry<Class<?>, Map<RegistrySupplier<Block>, Set<IResourceOption>>> optionEntry : options.entrySet()) {
            for (Map.Entry<RegistrySupplier<Block>, Set<IResourceOption>> entry : optionEntry.getValue().entrySet()) {
                Block block = entry.getKey().get();
                converted.get(optionEntry.getKey()).put(block, entry.getValue());
                subMapUnion(GLOBAL_OPTIONS, optionEntry.getKey(), block, entry.getValue());
            }
        }

        options.clear(); // Remove unused data
        convertedOptions = converted;
    }
}
