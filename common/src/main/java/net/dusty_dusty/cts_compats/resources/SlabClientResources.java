package net.dusty_dusty.cts_compats.resources;

import com.google.gson.JsonObject;
import dev.architectury.registry.registries.RegistrySupplier;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.mehvahdjukaar.moonlight.api.events.AfterLanguageLoadEvent;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynClientResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicTexturePack;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceGenTask;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceSink;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public final class SlabClientResources extends DynClientResourcesGenerator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SlabClientResources INSTANCE = new SlabClientResources();

    private SlabClientResources() {
        super(new DynamicTexturePack(new ResourceLocation(CTSCompats.MODID, "generated_assets")));
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
        RegistryManager.forEachRegistry(registry -> registry.getRegistryBlocks().stream()
                .map(RegistrySupplier::get)
                .filter(ISlabCopy.class::isInstance)
                .forEach(block -> executor.accept((manager, sink) -> generateSlab(block, manager, sink))));
    }

    @Override
    public void addDynamicTranslations(AfterLanguageLoadEvent languageEvent) {
        RegistryManager.getAllBlocks().forEach(block -> {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
            languageEvent.addEntry(block.getDescriptionId(), SlabTranslation.englishName(id));
        });
    }

    private static void generateSlab(Block block, ResourceManager manager, ResourceSink sink) {
        ISlabCopy slab = (ISlabCopy) block;
        ResourceLocation slabId = BuiltInRegistries.BLOCK.getKey(block);
        ResourceLocation originId = BuiltInRegistries.BLOCK.getKey(slab.getOriginBlock());
        if (hasAllResources(manager, sink, slabId)) {
            return;
        }
        SlabAssetJson.SlabAssets assets = SlabAssetJson.create(originModel(manager, originId), slabId, originId);
        addMissingResources(manager, sink, slabId, assets);
    }

    private static boolean hasAllResources(ResourceManager manager, ResourceSink sink, ResourceLocation slabId) {
        return sink.alreadyHasAssetAtLocation(manager, slabId, ResType.BLOCKSTATES)
                && sink.alreadyHasAssetAtLocation(manager, slabId, ResType.BLOCK_MODELS)
                && sink.alreadyHasAssetAtLocation(manager, topId(slabId), ResType.BLOCK_MODELS)
                && sink.alreadyHasAssetAtLocation(manager, slabId, ResType.ITEM_MODELS);
    }

    private static void addMissingResources(
            ResourceManager manager,
            ResourceSink sink,
            ResourceLocation slabId,
            SlabAssetJson.SlabAssets assets
    ) {
        if (!sink.alreadyHasAssetAtLocation(manager, slabId, ResType.BLOCKSTATES)) {
            sink.addBlockState(slabId, assets.blockState());
        }
        if (!sink.alreadyHasAssetAtLocation(manager, slabId, ResType.BLOCK_MODELS)) {
            sink.addBlockModel(slabId, assets.bottomModel());
        }
        if (!sink.alreadyHasAssetAtLocation(manager, topId(slabId), ResType.BLOCK_MODELS)) {
            sink.addBlockModel(topId(slabId), assets.topModel());
        }
        if (!sink.alreadyHasAssetAtLocation(manager, slabId, ResType.ITEM_MODELS)) {
            sink.addItemModel(slabId, assets.itemModel());
        }
    }

    private static JsonObject originModel(ResourceManager manager, ResourceLocation originId) {
        return StaticResource.getOrThrow(manager, ResType.BLOCK_MODELS.getPath(originId)).toJson();
    }

    private static ResourceLocation topId(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), id.getPath() + "_top");
    }
}
