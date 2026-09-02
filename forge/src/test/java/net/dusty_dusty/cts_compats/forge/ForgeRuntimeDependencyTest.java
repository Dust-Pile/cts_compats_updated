package net.dusty_dusty.cts_compats.forge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ForgeRuntimeDependencyTest {
    @Test
    void terrainSlabsConfigLibraryIsAvailable() {
        assertDoesNotThrow(() -> Class.forName("eu.midnightdust.lib.config.MidnightConfig"));
    }
}
