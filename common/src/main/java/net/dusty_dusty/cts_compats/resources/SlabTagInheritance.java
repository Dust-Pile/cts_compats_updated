package net.dusty_dusty.cts_compats.resources;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class SlabTagInheritance {
    private SlabTagInheritance() {
    }

    static Map<ResourceLocation, List<ResourceLocation>> inherit(
            Map<ResourceLocation, Collection<ResourceLocation>> sourceTags,
            Map<ResourceLocation, ResourceLocation> slabOrigins
    ) {
        Map<ResourceLocation, List<ResourceLocation>> inherited = new LinkedHashMap<>();
        sourceTags.forEach((tagId, entries) -> {
            if (!isMiningTag(tagId)) {
                return;
            }
            List<ResourceLocation> slabs = slabOrigins.entrySet().stream()
                    .filter(entry -> entries.contains(entry.getValue()))
                    .map(Map.Entry::getKey)
                    .toList();
            if (!slabs.isEmpty()) {
                inherited.put(tagId, slabs);
            }
        });
        return inherited;
    }

    private static boolean isMiningTag(ResourceLocation tagId) {
        String namespace = tagId.getNamespace();
        String path = tagId.getPath();
        if (namespace.equals("minecraft") || namespace.equals("forge")) {
            return path.startsWith("mineable/") || path.startsWith("needs_");
        }
        return namespace.equals("fabric") && path.startsWith("needs_tool_level_");
    }
}
