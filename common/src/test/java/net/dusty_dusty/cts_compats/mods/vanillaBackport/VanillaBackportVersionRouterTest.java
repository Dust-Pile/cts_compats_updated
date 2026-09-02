package net.dusty_dusty.cts_compats.mods.vanillaBackport;

import net.dusty_dusty.cts_compats.registry.Version;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class VanillaBackportVersionRouterTest {
    @Test
    void selectsBaseBelowAndSulfurAtBoundary() {
        assertSame(
                VanillaBackportVersionRouter.VersionRoutes.BASE,
                VanillaBackportVersionRouter.VersionRoutes.select(new Version("1.1.6"))
        );
        assertSame(
                VanillaBackportVersionRouter.VersionRoutes.SULFUR,
                VanillaBackportVersionRouter.VersionRoutes.select(new Version("1.1.7"))
        );
    }
}
