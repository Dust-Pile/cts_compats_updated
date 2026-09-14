package net.dusty_dusty.cts_compats.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.logging.LogUtils;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class AssetUtils {
    static final String SLAB_PARENT = "minecraft:block/slab";
    static final String SLAB_TOP_PARENT = "minecraft:block/slab_top";
    static final String BLOCK_PARENT = "minecraft:block/block";
    private static final Logger LOGGER = LogUtils.getLogger();

    record TextureSet(ResourceLocation modelId, Boolean isSimple, Map<TextureType, CuboidTexture> textures) {
        CuboidTexture get(TextureType type) {
            return this.textures().get(type);
        }

        boolean hasOverlay() {
            return this.get(TextureType.TOP_OVERLAY) != null || this.get(TextureType.BOTTOM_OVERLAY) != null
                    || this.get(TextureType.SIDE_OVERLAY) != null || this.get(TextureType.OVERLAY) != null;
        }
    }

    record ModelData(ResourceLocation modelId, JsonObject model) {}

    record CuboidTexture(String name, int tintIndex) {}

    enum TextureType {
        PARTICLE("null"),
        TOP("up"),
        BOTTOM("down"),
        SIDE("north"),
        TOP_OVERLAY("up"),
        BOTTOM_OVERLAY("down"),
        SIDE_OVERLAY("north"),
        OVERLAY("north");

        private final String dir;

        TextureType(String direction) {
            dir = direction;
        }

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }

        public String getDirection() {
            return dir;
        }
    }

    static void addFaces(JsonObject faces, Set<String> directions, JsonArray uv, String texture, int tintIndex) {
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
                array.add(new JsonPrimitive((Number) member));
            } else if (member instanceof String) {
                array.add(new JsonPrimitive((String) member));
            } else if (member instanceof Boolean) {
                array.add(new JsonPrimitive((Boolean) member));
            } else if (member instanceof Character) {
                array.add(new JsonPrimitive((Character) member));
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
        if (textures == null) {
            return null;
        }

        String parent = model.get("parent") != null ? model.get("parent").getAsString() : null;
        if (parent == null) {
            return null;
        }

        if (parent.contains("cube_all")) {
            String all = textures.get("all").getAsString();
            return new TextureSet(modelData.modelId(), true, Map.ofEntries(
                    Map.entry(TextureType.PARTICLE, new CuboidTexture(all, -1)),
                    Map.entry(TextureType.TOP, new CuboidTexture(all, -1)),
                    Map.entry(TextureType.BOTTOM, new CuboidTexture(all, -1)),
                    Map.entry(TextureType.SIDE, new CuboidTexture(all, -1))
            ));
        }

        Map<TextureType, CuboidTexture> map = addNewTextures( new HashMap<>(), modelData);

        // Dive deep recursive to get all textures
        if (!parent.contains("block/block")) {
            TextureSet parentSet = getTextures(modelDataFromString(manager, parent), manager);
            if (parentSet != null) {
                for (Map.Entry<TextureType, CuboidTexture> texture : parentSet.textures().entrySet()) {
                    map.merge(texture.getKey(), texture.getValue(), (oldValue, value) -> {
                        if (oldValue.tintIndex == -2 && value.tintIndex >= 0) {
                            return new CuboidTexture(oldValue.name, value.tintIndex);
                        }
                        return oldValue;
                    });
                }
            }
        }

        if (!map.containsKey(TextureType.PARTICLE)) map.put(TextureType.PARTICLE, map.get(TextureType.SIDE));
        return new TextureSet( modelData.modelId(), false, map );
    }

    private static Map<TextureType, CuboidTexture> addNewTextures(Map<TextureType, CuboidTexture> map, ModelData modelData) {
        JsonObject textures = modelData.model().getAsJsonObject("textures");
        for (Map.Entry<String, JsonElement> texture: textures.entrySet()) {
            try {
                TextureType type = TextureType.valueOf(texture.getKey().toUpperCase());
                map.putIfAbsent(type, new CuboidTexture(texture.getValue().getAsString(), getTintIndex(modelData, type)));
            } catch (IllegalArgumentException ignored) {}
        }

        return map;
    }

    private static int getTintIndex(ModelData modelData, TextureType type) {
        JsonObject model = modelData.model();
        JsonArray elements = model.getAsJsonArray("elements");
        if (elements == null) {
            return -2; // Separate identifier for "No Data" vs "No Tintindex"
        }
        for (JsonElement element : elements) {
            for (Map.Entry<String, JsonElement> faceEntry : element.getAsJsonObject().get("faces").getAsJsonObject().entrySet()) {
                JsonElement face = faceEntry.getValue();
                JsonElement texture = face.getAsJsonObject().get("texture");
                if (texture != null && texture.getAsString().equals("#" + type.toString())
                        && face.getAsJsonObject().has("tintindex")
                ) {
                    return face.getAsJsonObject().get("tintindex").getAsInt();
                }
            }
        }

        return -1;
    }
}
