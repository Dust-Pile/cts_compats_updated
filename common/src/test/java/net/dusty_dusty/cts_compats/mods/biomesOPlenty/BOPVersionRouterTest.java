package net.dusty_dusty.cts_compats.mods.biomesOPlenty;

import net.dusty_dusty.cts_compats.registry.Version;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class BOPVersionRouterTest {
    @Test
    void selectsReleaseBelowAndBetaAtBoundary() {
        assertSame(
                BOPVersionRouter.VersionRoutes.RELEASE,
                BOPVersionRouter.VersionRoutes.select(new Version("19.0.0.95"))
        );
        assertSame(
                BOPVersionRouter.VersionRoutes.BETA,
                BOPVersionRouter.VersionRoutes.select(new Version("19.0.0.96"))
        );
    }
}
