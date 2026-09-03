package net.dusty_dusty.cts_compats.resources;

import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.mehvahdjukaar.moonlight.api.resources.SimpleTagBuilder;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynServerResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicDataPack;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceGenTask;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceSink;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagLoader;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public final class SlabServerData extends DynServerResourcesGenerator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SlabServerData INSTANCE = new SlabServerData();

    private SlabServerData() {
        super(new DynamicDataPack(new ResourceLocation(CTSCompats.MODID, "generated_data")));
        dynamicPack.addNamespaces("minecraft", "forge", "fabric");
    }

    public static void init() {
        INSTANCE.register();
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public void regenerateDynamicAssets(Consumer<ResourceGenTask> executor) {
        executor.accept(SlabServerData::generateTags);
    }

    private static void generateTags(ResourceManager manager, ResourceSink sink) {
        TagLoader<ResourceLocation> loader = new TagLoader<>(id -> Optional.of(id), "tags/blocks");
        Map<ResourceLocation, Collection<ResourceLocation>> sourceTags = loader.loadAndBuild(manager);
        SlabTagInheritance.inherit(sourceTags, slabOrigins()).forEach((tagId, slabs) -> {
            SimpleTagBuilder tag = SimpleTagBuilder.of(tagId);
            slabs.forEach(tag::add);
            sink.addTag(tag, Registries.BLOCK);
        });
    }

    private static Map<ResourceLocation, ResourceLocation> slabOrigins() {
        Map<ResourceLocation, ResourceLocation> origins = new LinkedHashMap<>();
        for (Block block : RegistryManager.getAllBlocks()) {
            if (block instanceof ISlabCopy slab) {
                origins.put(
                        BuiltInRegistries.BLOCK.getKey(block),
                        BuiltInRegistries.BLOCK.getKey(slab.getOriginBlock())
                );
            }
        }
        return origins;
    }
}
