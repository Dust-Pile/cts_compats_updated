package net.dusty_dusty.cts_compats;

import dev.architectury.platform.Platform;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final class CtsCompatsMixinPlugin implements IMixinConfigPlugin {
    private static final Map<String, String> OPTIONAL_MIXINS = Map.of(
            ".mixins.biomesOPlenty.", CTSCompats.BOP_MODID,
            ".mixins.MeadowAssigner", CTSCompats.MEADOW_MODID,
            ".mixins.vanillaBackport.", CTSCompats.VB_MODID,
            ".mixins.projectVibrantJourneys.", CTSCompats.PVJ_MODID
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
        return shouldApplyMixin(mixinClassName, Platform::isModLoaded);
    }

    static boolean shouldApplyMixin(String mixinClassName, Predicate<String> isModLoaded) {
        return OPTIONAL_MIXINS.entrySet().stream()
                .filter(entry -> mixinClassName.contains(entry.getKey()))
                .findFirst()
                .map(Map.Entry::getValue)
                .map(isModLoaded::test)
                .orElse(true);
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
