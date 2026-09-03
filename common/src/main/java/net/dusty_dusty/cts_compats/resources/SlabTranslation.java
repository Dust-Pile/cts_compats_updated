package net.dusty_dusty.cts_compats.resources;

import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.stream.Collectors;

final class SlabTranslation {
    private SlabTranslation() {
    }

    static String englishName(ResourceLocation id) {
        return Arrays.stream(id.getPath().split("_"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
