package net.dusty_dusty.cts_compats.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.countered.terrainslabs.api.OffsetClasses;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.MODID;

public abstract class AbstractRegistry implements IRegistry {
    private final List<RegistrySupplier<? extends Block>> registryBlocks = new ArrayList<>();
    private final List<Block> cutoutBlocks = new ArrayList<>();
    private final List<Block> cutoutMippedBlocks = new ArrayList<>();

    protected final DeferredRegister<Block> COMPAT_BLOCKS = DeferredRegister.create(MODID, Registries.BLOCK);
    protected final DeferredRegister<Item> COMPAT_ITEMS = DeferredRegister.create(MODID, Registries.ITEM);
    protected final String REGISTRY_ID;

    protected AbstractRegistry(String modId) {
        REGISTRY_ID = modId;
    }

    public static void registerOffsetClasses(OffsetClasses.Category category, Collection<Class<?>> classes) {
        classes.forEach(type -> OffsetClasses.addDefaultClass(type, category));
    }

    public static void registerOffsetClasses(String category, Collection<Class<?>> classes) {
        classes.forEach(type -> OffsetClasses.addDefaultClass(type, category));
    }

    @Override
    public String getModID() {
        return REGISTRY_ID;
    }

    public <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> output = COMPAT_BLOCKS.register(name, block);
        registryBlocks.add(output);
        registerBlockItem(name, output);
        return output;
    }

    protected RegistrySupplier<Item> registerBlockItem(String name, Supplier<? extends Block> block) {
        return COMPAT_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    @Override
    public void register() {
        COMPAT_BLOCKS.register();
        COMPAT_ITEMS.register();
    }

    @Override
    public void assign() {
    }

    public <T extends Block> RegistrySupplier<T> registerBlockCutout(String name, Supplier<T> block) {
        RegistrySupplier<T> output = registerBlock(name, block);
        output.listen(cutoutBlocks::add);
        return output;
    }

    public <T extends Block> RegistrySupplier<T> registerBlockCutoutMipped(String name, Supplier<T> block) {
        RegistrySupplier<T> output = registerBlock(name, block);
        output.listen(cutoutMippedBlocks::add);
        return output;
    }

    @Override
    public Collection<RegistrySupplier<? extends Block>> getRegistryBlocks() {
        return List.copyOf(registryBlocks);
    }

    @Override
    public Collection<Block> getCutoutBlocks() {
        return List.copyOf(cutoutBlocks);
    }

    @Override
    public Collection<Block> getCutoutMippedBlocks() {
        return List.copyOf(cutoutMippedBlocks);
    }
}
