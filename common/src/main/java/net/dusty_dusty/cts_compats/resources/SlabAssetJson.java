package net.dusty_dusty.cts_compats.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.dusty_dusty.cts_compats.registry.AbstractOptionRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

import java.util.*;
import java.util.regex.Pattern;

import static net.dusty_dusty.cts_compats.resources.AssetUtils.TextureType;
import static net.dusty_dusty.cts_compats.resources.AssetUtils.ModelData;
import static net.dusty_dusty.cts_compats.resources.ResourceOptions.BlockModelFlags;

final class SlabAssetJson {
    private static final Logger LOGGER = LogUtils.getLogger();

    final Block slabBlock;
    final JsonObject originBlockStates;
    final Map<String, ModelData> variants;
    final ResourceLocation slabId;
    final ResourceLocation originId;

    final Map<String, AssetUtils.TextureSet> textures;
    final Map<String, String> nameScheme;
    final Set<BlockModelFlags> options;

    private final Map<ResourceLocation, JsonObject> models = new HashMap<>();

    SlabAssetJson (Block slabBlock, ResourceManager manager, JsonObject originBlockStates, Map<String, ModelData> variants,
                   ResourceLocation slabId, ResourceLocation originId
    ) {
        this.slabBlock = slabBlock;
        this.originBlockStates = originBlockStates;
        this.variants = variants;
        this.slabId = slabId;
        this.originId = originId;
        textures = getAllTextures(manager);
        nameScheme = getNameScheme();
        options = AbstractOptionRegistry.getGlobalOptions().getOptions(BlockModelFlags.class, slabBlock);
    }

    SlabAssets create() {
        return new SlabAssets(blockState(), models, itemModel());
    }

    private JsonObject blockState() {
        JsonObject slabVariants = new JsonObject();
        for(Map.Entry<String, ModelData> variant : variants.entrySet()) {
            String variantLabel = variant.getKey();
            ResourceLocation bottomName = applyNameScheme(variantLabel);
            ResourceLocation topName = AssetUtils.topId(bottomName);

            models.putAll(generateModels(textures.get(variantLabel), bottomName, topName));

            slabVariants.add("type=bottom," + variantLabel, AssetUtils.variant(AssetUtils.blockModel(bottomName)));
            slabVariants.add("type=double," + variantLabel, AssetUtils.variant(AssetUtils.blockModel(variant.getValue().modelId())));
            slabVariants.add("type=top," + variantLabel, AssetUtils.variant(AssetUtils.blockModel(topName)));
        }

        JsonObject root = new JsonObject();
        root.add("variants", slabVariants);
        return root;
    }

    private Map<ResourceLocation, JsonObject> generateModels(AssetUtils.TextureSet textureSet,
            ResourceLocation bottomName, ResourceLocation topName
    ) {
        if (textureSet.isSimple()) {
            return Map.ofEntries( Map.entry(bottomName, simpleModel(AssetUtils.SLAB_PARENT, textureSet)),
                    Map.entry(topName, simpleModel(AssetUtils.SLAB_TOP_PARENT, textureSet)) );
        }

        JsonArray bottomElements = new JsonArray();
        bottomElements.add(getSlabElement(textureSet, false));

        JsonArray topElements = new JsonArray();
        topElements.add(getSlabElement(textureSet, true));

        if (textureSet.hasOverlay()) {
            bottomElements.add(getOverlayElement(textureSet, false));
            topElements.add(getOverlayElement(textureSet, true));
        }

        JsonObject model = new JsonObject();
        model.addProperty("parent", AssetUtils.BLOCK_PARENT);
        model.add("textures", getTexturesObject(textureSet));

        JsonObject topModel = new JsonObject();
        topModel.addProperty("parent", AssetUtils.BLOCK_PARENT);
        topModel.add("textures", getTexturesObject(textureSet));

        model.add("elements", bottomElements);
        topModel.add("elements", topElements);

        return Map.ofEntries( Map.entry(bottomName, model),
                Map.entry(topName, topModel) );
    }

    private static JsonObject simpleModel(String parent, AssetUtils.TextureSet textureSet) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", parent);
        model.add("textures", getTexturesObject(textureSet));
        return model;
    }

    private static JsonObject getTexturesObject(AssetUtils.TextureSet textureSet) {
        JsonObject textures = new JsonObject();
        for (Map.Entry<AssetUtils.TextureType, AssetUtils.CuboidTexture> ident : textureSet.textures().entrySet()) {
            textures.addProperty(ident.getKey().toString(), ident.getValue().name());
        }
        return textures;
    }

    private JsonObject itemModel() {
        JsonObject model = new JsonObject();
        model.addProperty("parent", AssetUtils.blockModel(slabId));
        return model;
    }

    private Map<String, AssetUtils.TextureSet> getAllTextures(ResourceManager manager) {
        Map<String, AssetUtils.TextureSet> textures = new HashMap<>();
        for (Map.Entry<String, ModelData> variant : variants.entrySet()) {
            textures.put(variant.getKey(), AssetUtils.getTextures(variant.getValue(), manager));
        }
        return textures;
    }

    private ResourceLocation applyNameScheme(String variantLabel) {
        return ResourceLocation.tryBuild(slabId.getNamespace(), nameScheme.get(variantLabel).replace("*", slabId.getPath()));
    }

    // Deterministic naming engine for variants.
    private Map<String, String> getNameScheme() {
        if ( variants.size() == 1 ) {
            return Map.of("", "*");
        }
        Map<String, String> scheme = new HashMap<>();
        for (Map.Entry<String, ModelData> variant : variants.entrySet()) {
            // When possible, just slot in where the id is.
            Pattern containsName = Pattern.compile(Pattern.quote(originId.getPath()));
            if (containsName.asPredicate().test(variant.getValue().modelId().getPath())) {
                scheme.put( variant.getKey(),
                        variant.getValue().modelId().getPath().replace(originId.getPath(), "*"));
                continue;
            }

            // Backup labeling system if block id not found
            StringBuilder schemeString = new StringBuilder("*");
            for (String property : variant.getValue().modelId().getPath().split(",")) {
                String[] parts = property.split("=");
                if (parts[1].equals("true")) {
                    schemeString.append("_").append(parts[0].toLowerCase());
                } else if (parts[1].equals("false")) {
                    // Filter clause
                } else {
                    schemeString.append("_").append(parts[1].toLowerCase());
                }
            }
            scheme.put(variant.getKey(), schemeString.toString());
        }

        return scheme;
    }

    private JsonObject getSlabElement(AssetUtils.TextureSet textureSet, boolean isTop) {
        JsonObject slabElement = AssetUtils.getSlabCuboid(isTop);
        JsonArray verticalUV = getFaceUV("top", isTop);

        JsonObject faces = new JsonObject();
        AssetUtils.addFaces(faces, Set.of("up"), verticalUV, "#top",
                textureSet.get(TextureType.TOP).tintIndex());
        AssetUtils.addFaces(faces, Set.of("down"), verticalUV, "#bottom",
                textureSet.get(TextureType.BOTTOM).tintIndex());
        AssetUtils.addFaces(faces, Set.of("north", "south", "east", "west"), getFaceUV("side", isTop), "#side",
                textureSet.get(TextureType.SIDE).tintIndex());

        slabElement.add("faces", faces);
        return slabElement;
    }

    private JsonObject getOverlayElement(AssetUtils.TextureSet textureSet, boolean isTop) {
        JsonObject overlayElement = AssetUtils.getSlabCuboid(isTop);
        JsonArray verticalUV = getFaceUV("top", isTop);
        JsonObject faces = new JsonObject();
        if (textureSet.get(TextureType.TOP_OVERLAY) != null) {
            AssetUtils.addFaces(faces, Set.of("up"), verticalUV, "#top_overlay",
                    textureSet.get(TextureType.TOP_OVERLAY).tintIndex());
        }
        if (textureSet.get(TextureType.BOTTOM_OVERLAY) != null) {
            AssetUtils.addFaces(faces, Set.of("down"), verticalUV, "#bottom_overlay",
                    textureSet.get(TextureType.BOTTOM_OVERLAY).tintIndex());
        }
        if (textureSet.get(TextureType.SIDE_OVERLAY) != null) {
            AssetUtils.addFaces(faces, Set.of("north", "south", "east", "west"),
                    getFaceUV("side_overlay", isTop), "#side_overlay",
                    textureSet.get(TextureType.SIDE_OVERLAY).tintIndex());

        } else if (textureSet.get(TextureType.OVERLAY) != null) {
            AssetUtils.addFaces(faces, Set.of("north", "south", "east", "west"),
                    getFaceUV("overlay", isTop), "#overlay",
                    textureSet.get(TextureType.OVERLAY).tintIndex());
        }

        overlayElement.add("faces", faces);
        return overlayElement;
    }

    private JsonArray getFaceUV(String type, boolean isTop) {
        if (type.equals("top") || type.equals("bottom")) {
            return AssetUtils.fillArray(new JsonArray(), 0, 0, 16, 16);
        }
        boolean offset = type.equals("side") ? options.contains(BlockModelFlags.UV_OFF_BY_ONE)
                : options.contains(BlockModelFlags.UV_OFF_BY_ONE_OVERLAY);
        boolean topEdge = type.equals("side") ? options.contains(BlockModelFlags.UV_TOP_EDGE)
                : options.contains(BlockModelFlags.UV_TOP_EDGE_OVERLAY);
        if (topEdge || isTop) {
            return AssetUtils.fillArray(new JsonArray(), 0, offset ? 1 : 0, 16, offset ? 9 : 8);
        } else {
            return AssetUtils.fillArray(new JsonArray(), 0, offset ? 7 : 8, 16, offset ? 15 : 16);
        }
    }

    record SlabAssets(JsonObject blockState, Map<ResourceLocation, JsonObject> models, JsonObject itemModel) {}
}
