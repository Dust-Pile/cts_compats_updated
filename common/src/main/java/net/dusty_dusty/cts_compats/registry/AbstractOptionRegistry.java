package net.dusty_dusty.cts_compats.registry;

import com.mojang.logging.LogUtils;
import net.dusty_dusty.cts_compats.resources.ResourceOptions;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.*;

import static net.dusty_dusty.cts_compats.resources.ResourceOptions.IResourceOption;

public abstract class AbstractOptionRegistry implements IResourceOptionRegistry {
    private static final Map<Class<?>, Map<String, Set<IResourceOption>>> GLOBAL_OPTIONS =
            initializeOptionTypes(new HashMap<>());

    public static IResourceOptionRegistry getGlobalOptions() {
        return new AbstractOptionRegistry( GLOBAL_OPTIONS ){
            @Override
            public void register(OptionProvider provider) {}
        };
    }

    private final Map<Class<?>, Map<String, Set<IResourceOption>>> options;

    public AbstractOptionRegistry() {
        options = new HashMap<>();
        initializeOptionTypes(options);
    }
    private AbstractOptionRegistry(Map<Class<?>, Map<String, Set<IResourceOption>>> options) {
        this.options = options;
    }

    @SuppressWarnings("unchecked")
    public final <T> Map<String, Set<T>> getOptionsOfType(Class<T> clazz) {
        throwUnsupported(clazz);
        Map<String, Set<T>> unprotectedMap = new HashMap<>();
        for (Map.Entry<String, Set<IResourceOption>> entry : options.get(clazz).entrySet()) {
            unprotectedMap.put(entry.getKey(), new HashSet<>((Set<T>) entry.getValue()));
        }
        return unprotectedMap;
    }

    @SuppressWarnings("unchecked")
    public final <T> Set<T> getOptions(Class<?> clazz, String blockId) {
        throwUnsupported(clazz);
        Set<T> blockOptions = (Set<T>) options.get(clazz).get(blockId);
        return new HashSet<>(blockOptions == null ? Set.of() : blockOptions);
    }

    public final void register() {
        register(new OptionProvider(this));
    }

    public abstract void register(OptionProvider provider);



    public static final class OptionProvider {
        private final AbstractOptionRegistry owner;

        OptionProvider(AbstractOptionRegistry owner) {
            this.owner = owner;
        }

        @SafeVarargs
        public final <T extends IResourceOption> OptionProvider addOptions(ResourceLocation block, T... options) {
            owner.addOptions(block.toString(), Set.of(options));
            return this;
        }

        public final <T extends IResourceOption> OptionProvider addBlocksForOption(T option, ResourceLocation... blocks) {
            for (ResourceLocation block : blocks) {
                owner.addOptions(block.toString(), Set.of(option));
            }
            return this;
        }

        public final <T extends IResourceOption> OptionProvider addBlocksForOptions(Set<T> options, ResourceLocation... blocks) {
            for (ResourceLocation block : blocks) {
                owner.addOptions(block.toString(), new HashSet<>(options));
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

    private <T extends IResourceOption> void addOptions(String blockId, Set<T> options) {
        if (options == null || options.isEmpty()) {
            return;
        }

        Class<?> inputType = options.iterator().next().getClass();
        throwUnsupported(inputType);

        subMapUnion(this.options, inputType, blockId, options);
        subMapUnion(GLOBAL_OPTIONS, inputType, blockId, options);
    }

    private void throwUnsupported(Class<?> clazz) {
        if (!this.options.containsKey(clazz)) {
            throw new IllegalArgumentException("No Option Registered for type " + clazz.getName());
        }
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
}
