package net.dusty_dusty.cts_compats.resources;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public final class ResourceOptions {
    public static Set<Class<? extends IResourceOption>> availableTypes() {
        return Set.of(BlockModelFlags.class, VariantModifier.class, ColorFlags.class);
    }

    public interface IResourceOption {}

    public enum BlockModelFlags implements IResourceOption {
        UV_TOP_EDGE, // Use texture with top uv
        UV_OFF_BY_ONE, // Offset uv by 1 pixel relative to edge
        UV_TOP_EDGE_OVERLAY,
        UV_OFF_BY_ONE_OVERLAY,
        STATE_ALL_ROTATIONS
    }

    public enum ColorFlags implements IResourceOption {
        GREENER_GRASS
    }

    public static class VariantModifier implements IResourceOption {
        private final Function<String, String> variantModifier;

        VariantModifier(Function<String, String> variantModifier) {
            this.variantModifier = variantModifier;
        }

        public String modify(String variantString) {
            return variantModifier.apply(variantString);
        }

        static VariantModifier variantModifierOf(Function<String, String> modifier) { return new VariantModifier(modifier);}
        static String modifyWith(String label, Set<VariantModifier> options) {
            if (options.size() > 1) {
                throw new UnsupportedOperationException("Cannot apply multiple Variant Modifiers.");
            }
            return options.stream().findFirst().orElse(variantModifierOf(s->s)).modify(label);
        }
    }
    public static VariantModifier variantModifierOf(Function<String, String> modifier) {
        return VariantModifier.variantModifierOf(modifier);
    }
    public static String modifyWith(String label, Set<VariantModifier> options) {
        return VariantModifier.modifyWith(label, options);
    }
}
