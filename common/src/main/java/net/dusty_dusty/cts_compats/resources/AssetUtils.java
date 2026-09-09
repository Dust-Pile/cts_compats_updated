package net.dusty_dusty.cts_compats.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.Set;

public final class AssetUtils {
    static final String SLAB_PARENT = "minecraft:block/slab";
    static final String SLAB_TOP_PARENT = "minecraft:block/slab_top";
    static final String BLOCK_PARENT = "minecraft:block/block";

    record TextureSet(ResourceLocation modelId, Boolean isSimple, String particle, String side, String bottom, String top, String sideOverlay) {}

    record ModelData(ResourceLocation modelId, JsonObject model) {}

    static JsonObject addFaces(JsonObject faces, Set<String> directions, JsonArray uv, String texture) {
        return addFaces(faces, directions, uv, texture, -1);
    }

    static JsonObject addFaces(JsonObject faces, Set<String> directions, JsonArray uv, String texture, int tintIndex) {
        for (String direction : directions) {
            JsonObject face = new JsonObject();
            face.add("uv", uv);
            face.addProperty("texture", texture);
            face.addProperty("cullface", direction);
            if (tintIndex >= 0) {
                face.addProperty("tintindex", tintIndex);
            }
            faces.add(direction, face);
        }
        return faces;
    }

    static JsonObject getSlabCuboid(boolean isTop) {
        JsonObject cuboid = new JsonObject();
        cuboid.add("from", fillArray(new JsonArray(), 0, isTop ? 8 : 0, 0));
        cuboid.add("to", fillArray(new JsonArray(), 16, isTop ? 16 : 8, 16));
        return cuboid;
    }

    static JsonArray fillArray(JsonArray array, Object... members) {
        for (Object member : members) {
            if (member instanceof Number) {
                array.add((Number) member);
            } else if (member instanceof String) {
                array.add((String) member);
            } else if (member instanceof Boolean) {
                array.add((Boolean) member);
            } else if (member instanceof Character) {
                array.add((Character) member);
            } else if (member instanceof JsonElement) {
                array.add((JsonElement) member);
            } else {
                throw new UnsupportedOperationException("JsonArray only supports members of types Number, String, Boolean, Character, and JsonElement");
            }
        }
        return array;
    }

    static String blockModel(ResourceLocation id) {
        return id.getNamespace() + ":block/" + id.getPath();
    }

    static ResourceLocation topId(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), id.getPath() + "_top");
    }

    static JsonObject variant(String model) {
        JsonObject variant = new JsonObject();
        variant.addProperty("model", model);
        return variant;
    }

    @SuppressWarnings("DataFlowIssue")
    static ModelData modelDataFromString(ResourceManager manager, String modelId) {
        ResourceLocation modelLoc = ResourceLocation.tryParse( modelId.replace("block/", "") );
        return new ModelData(modelLoc, StaticResource.getOrThrow(manager,
                ResType.BLOCK_MODELS.getPath( modelLoc ) ).toJson());
    }

    static TextureSet getTextures(ModelData modelData, ResourceManager manager) {
        JsonObject model = modelData.model();
        JsonObject textures = model.getAsJsonObject("textures");
        String parent = model.get("parent") != null ? model.get("parent").getAsString() : null;

        if (parent != null && parent.contains("cube_all")) {
            String all = textures.get("all").getAsString();
            return new TextureSet(modelData.modelId(), true, all, all, all, all, null);
        }

        String particle = textures.get("particle") != null ? textures.get("particle").getAsString() : null;
        String side = textures.get("side") != null ? textures.get("side").getAsString() : null;
        String bottom = textures.get("bottom") != null ? textures.get("bottom").getAsString() : null;
        String top = textures.get("top") != null ? textures.get("top").getAsString() : null;
        String overlay = textures.get("overlay") != null ? textures.get("overlay").getAsString()
                : textures.get("side_overlay") != null ? textures.get("side_overlay").getAsString() : null;

        if ((side == null || bottom == null || top == null) // Don't dive for overlay or particle exclusively.
                && parent != null && !parent.contains("block/block")
        ) {
            TextureSet parentSet = getTextures(modelDataFromString(manager, parent), manager);
            particle = particle == null ? parentSet.particle : particle;
            side = side == null ? parentSet.side : side;
            bottom = bottom == null ? parentSet.bottom : bottom;
            top = top == null ? parentSet.top : top;
            overlay = overlay == null ? parentSet.sideOverlay : overlay;
        }

        particle = particle == null ? side : particle;

        return new TextureSet(
                modelData.modelId(),
                false,
                particle,
                side,
                bottom,
                top,
                overlay
        );
    }
}
