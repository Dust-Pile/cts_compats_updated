package net.dusty_dusty.cts_compats;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public final class CtsCompatsMixinPlugin implements IMixinConfigPlugin {
    private static final Set<String> OPTIONAL_MIXINS = Set.of(
            ".mixins.biomesOPlenty.",
            ".mixins.MeadowAssigner",
            ".mixins.vanillaBackport.",
            ".mixins.projectVibrantJourneys."
    );

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return shouldApplyMixin(targetClassName, mixinClassName, CtsCompatsMixinPlugin::isClassPresent);
    }

    static boolean shouldApplyMixin(
            String targetClassName,
            String mixinClassName,
            Predicate<String> isClassPresent
    ) {
        boolean optional = OPTIONAL_MIXINS.stream().anyMatch(mixinClassName::contains);
        return !optional || isClassPresent.test(targetClassName);
    }

    private static boolean isClassPresent(String className) {
        return isClassPresent(className, CtsCompatsMixinPlugin.class.getClassLoader());
    }

    static boolean isClassPresent(String className, ClassLoader classLoader) {
        return classLoader.getResource(className.replace('.', '/') + ".class") != null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
