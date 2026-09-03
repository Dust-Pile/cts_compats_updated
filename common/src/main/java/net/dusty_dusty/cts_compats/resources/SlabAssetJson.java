package net.dusty_dusty.cts_compats.resources;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

final class SlabAssetJson {
    private static final String SLAB_PARENT = "minecraft:block/slab";
    private static final String SLAB_TOP_PARENT = "minecraft:block/slab_top";

    private SlabAssetJson() {
    }

    static SlabAssets create(JsonObject originModel, ResourceLocation slabId, ResourceLocation originId) {
        TextureSet textures = textures(originModel);
        return new SlabAssets(
                blockState(slabId, originId),
                model(SLAB_PARENT, textures.side(), textures.bottom(), textures.top()),
                model(SLAB_TOP_PARENT, textures.side(), textures.bottom(), textures.top()),
                itemModel(slabId)
        );
    }

    private static TextureSet textures(JsonObject originModel) {
        JsonObject textures = originModel.getAsJsonObject("textures");
        if (originModel.get("parent").getAsString().contains("cube_all")) {
            String all = textures.get("all").getAsString();
            return new TextureSet(all, all, all);
        }
        return new TextureSet(
                textures.get("side").getAsString(),
                textures.get("bottom").getAsString(),
                textures.get("top").getAsString()
        );
    }

    private static JsonObject blockState(ResourceLocation slabId, ResourceLocation originId) {
        JsonObject variants = new JsonObject();
        variants.add("type=bottom", variant(blockModel(slabId)));
        variants.add("type=double", variant(blockModel(originId)));
        variants.add("type=top", variant(blockModel(topId(slabId))));
        JsonObject root = new JsonObject();
        root.add("variants", variants);
        return root;
    }

    private static JsonObject variant(String model) {
        JsonObject variant = new JsonObject();
        variant.addProperty("model", model);
        return variant;
    }

    private static JsonObject model(String parent, String side, String bottom, String top) {
        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", bottom);
        textures.addProperty("side", side);
        textures.addProperty("top", top);
        JsonObject model = new JsonObject();
        model.addProperty("parent", parent);
        model.add("textures", textures);
        return model;
    }

    private static JsonObject itemModel(ResourceLocation slabId) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", blockModel(slabId));
        return model;
    }

    private static String blockModel(ResourceLocation id) {
        return id.getNamespace() + ":block/" + id.getPath();
    }

    private static ResourceLocation topId(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), id.getPath() + "_top");
    }

    record SlabAssets(JsonObject blockState, JsonObject bottomModel, JsonObject topModel, JsonObject itemModel) {
    }

    private record TextureSet(String side, String bottom, String top) {
    }
}
