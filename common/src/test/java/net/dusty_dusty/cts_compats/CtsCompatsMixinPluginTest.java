package net.dusty_dusty.cts_compats;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CtsCompatsMixinPluginTest {
    @Test
    void skipsOptionalMixinsWhenTheirModsAreAbsent() {
        Set<String> loadedMods = Set.of();

        assertFalse(CtsCompatsMixinPlugin.shouldApplyMixin(
                "net.dusty_dusty.cts_compats.mixins.biomesOPlenty.MixinBlocksBeta",
                loadedMods::contains
        ));
        assertFalse(CtsCompatsMixinPlugin.shouldApplyMixin(
                "net.dusty_dusty.cts_compats.mixins.MeadowAssigner",
                loadedMods::contains
        ));
        assertFalse(CtsCompatsMixinPlugin.shouldApplyMixin(
                "net.dusty_dusty.cts_compats.mixins.vanillaBackport.MixinSpeleothemBlock",
                loadedMods::contains
        ));
        assertFalse(CtsCompatsMixinPlugin.shouldApplyMixin(
                "net.dusty_dusty.cts_compats.mixins.projectVibrantJourneys.MixinIcicleBlock",
                loadedMods::contains
        ));
    }

    @Test
    void appliesOptionalMixinsWhenTheirModsAreLoaded() {
        Set<String> loadedMods = Set.of(
                CTSCompats.BOP_MODID,
                CTSCompats.MEADOW_MODID,
                CTSCompats.VB_MODID,
                CTSCompats.PVJ_MODID
        );

        assertTrue(CtsCompatsMixinPlugin.shouldApplyMixin(
                "net.dusty_dusty.cts_compats.mixins.biomesOPlenty.BOPAttachedFaceAssigner",
                loadedMods::contains
        ));
        assertTrue(CtsCompatsMixinPlugin.shouldApplyMixin(
                "net.dusty_dusty.cts_compats.mixins.MeadowAssigner",
                loadedMods::contains
        ));
        assertTrue(CtsCompatsMixinPlugin.shouldApplyMixin(
                "net.dusty_dusty.cts_compats.mixins.vanillaBackport.MixinSpeleothemBlock",
                loadedMods::contains
        ));
        assertTrue(CtsCompatsMixinPlugin.shouldApplyMixin(
                "net.dusty_dusty.cts_compats.mixins.projectVibrantJourneys.MixinBlocks",
                loadedMods::contains
        ));
    }

    @Test
    void appliesMixinsWithoutAnOptionalOwner() {
        assertTrue(CtsCompatsMixinPlugin.shouldApplyMixin(
                "net.dusty_dusty.cts_compats.mixins.UnconditionalMixin",
                modId -> false
        ));
    }
}
