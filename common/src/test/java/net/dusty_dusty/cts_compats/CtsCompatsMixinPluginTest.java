package net.dusty_dusty.cts_compats;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CtsCompatsMixinPluginTest {
    @Test
    void checksTargetResourcesWithoutLoadingClasses() throws Exception {
        URL targetResource = URI.create("file:/present/Target.class").toURL();
        ClassLoader resourceOnlyLoader = new ClassLoader(null) {
            @Override
            public URL getResource(String name) {
                return name.equals("present/Target.class") ? targetResource : null;
            }

            @Override
            protected Class<?> loadClass(String name, boolean resolve) {
                throw new AssertionError(name);
            }
        };

        assertTrue(CtsCompatsMixinPlugin.isClassPresent("present.Target", resourceOnlyLoader));
        assertFalse(CtsCompatsMixinPlugin.isClassPresent("missing.Target", resourceOnlyLoader));
    }

    @Test
    void optionalChecksUseTargetAvailabilityBeforeLoaderState() {
        assertFalse(CtsCompatsMixinPlugin.shouldApplyMixin(
                "missing.Target",
                "net.dusty_dusty.cts_compats.mixins.biomesOPlenty.MixinBlocksBeta",
                target -> false
        ));
    }

    @Test
    void skipsOptionalMixinsWhenTheirTargetsAreAbsent() {
        assertFalse(CtsCompatsMixinPlugin.shouldApplyMixin(
                "missing.Target",
                "net.dusty_dusty.cts_compats.mixins.biomesOPlenty.MixinBlocksBeta",
                target -> false
        ));
        assertFalse(CtsCompatsMixinPlugin.shouldApplyMixin(
                "missing.Target",
                "net.dusty_dusty.cts_compats.mixins.MeadowAssigner",
                target -> false
        ));
        assertFalse(CtsCompatsMixinPlugin.shouldApplyMixin(
                "missing.Target",
                "net.dusty_dusty.cts_compats.mixins.vanillaBackport.MixinSpeleothemBlock",
                target -> false
        ));
        assertFalse(CtsCompatsMixinPlugin.shouldApplyMixin(
                "missing.Target",
                "net.dusty_dusty.cts_compats.mixins.projectVibrantJourneys.MixinIcicleBlock",
                target -> false
        ));
    }

    @Test
    void appliesOptionalMixinsWhenTheirTargetsArePresent() {
        assertTrue(CtsCompatsMixinPlugin.shouldApplyMixin(
                "present.Target",
                "net.dusty_dusty.cts_compats.mixins.biomesOPlenty.BOPAttachedFaceAssigner",
                target -> true
        ));
        assertTrue(CtsCompatsMixinPlugin.shouldApplyMixin(
                "present.Target",
                "net.dusty_dusty.cts_compats.mixins.MeadowAssigner",
                target -> true
        ));
        assertTrue(CtsCompatsMixinPlugin.shouldApplyMixin(
                "present.Target",
                "net.dusty_dusty.cts_compats.mixins.vanillaBackport.MixinSpeleothemBlock",
                target -> true
        ));
        assertTrue(CtsCompatsMixinPlugin.shouldApplyMixin(
                "present.Target",
                "net.dusty_dusty.cts_compats.mixins.projectVibrantJourneys.MixinBlocks",
                target -> true
        ));
    }

    @Test
    void appliesMixinsWithoutAnOptionalOwner() {
        assertTrue(CtsCompatsMixinPlugin.shouldApplyMixin(
                "missing.Target",
                "net.dusty_dusty.cts_compats.mixins.UnconditionalMixin",
                target -> false
        ));
    }
}
