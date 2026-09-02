package net.dusty_dusty.cts_compats.registry;

import net.dusty_dusty.cts_compats.mods.biomesOPlenty.BOPVersionRouter;
import net.dusty_dusty.cts_compats.mods.vanillaBackport.VanillaBackportVersionRouter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VersionTest {
    @Test
    void comparesNumericAndAlphanumericPartsInOrder() {
        assertTrue(new Version("1.20.1").compareTo(new Version("1.20.2")) < 0);
        assertTrue(new Version("1.20.10").compareTo(new Version("1.20.2")) > 0);
        assertTrue(new Version("1.13.5b").compareTo(new Version("1.13.5a")) > 0);
    }

    @Test
    void wildcardMatchesEveryVersionFromEitherSide() {
        Version wildcard = new Version("*");
        Version release = new Version("19.0.0.96");

        assertEquals(0, wildcard.compareTo(release));
        assertEquals(0, release.compareTo(wildcard));
    }

    @Test
    void inclusiveRangeAcceptsBothBounds() {
        Version.Range range = Version.Range.acceptBetweenInclusive("1.1.6", "1.1.7");

        assertEquals(0, range.compareTo(new Version("1.1.6")));
        assertEquals(0, range.compareTo(new Version("1.1.7")));
    }

    @Test
    void exclusiveRangeRejectsBothBounds() {
        Version.Range range = Version.Range.acceptBetweenExclusive("1.1.6", "1.1.7");

        assertTrue(range.compareTo(new Version("1.1.6")) < 0);
        assertTrue(range.compareTo(new Version("1.1.7")) > 0);
        assertEquals(0, range.compareTo(new Version("1.1.6.5")));
    }

    @Test
    void routesBiomesOPlentyBoundaryAtNineteenPointZeroPointZeroPointNinetySix() {
        List<Version.Range> releaseRoutes = BOPVersionRouter.VersionRoutes.matching(new Version("19.0.0.95"));
        List<Version.Range> betaRoutes = BOPVersionRouter.VersionRoutes.matching(new Version("19.0.0.96"));

        assertEquals(1, releaseRoutes.size());
        assertEquals(1, betaRoutes.size());
        assertNotEquals(releaseRoutes, betaRoutes);
    }

    @Test
    void routesVanillaBackportBoundaryAtOnePointOnePointSeven() {
        List<Version.Range> baseRoutes = VanillaBackportVersionRouter.VersionRoutes.matching(new Version("1.1.6"));
        List<Version.Range> sulfurRoutes = VanillaBackportVersionRouter.VersionRoutes.matching(new Version("1.1.7"));

        assertEquals(1, baseRoutes.size());
        assertEquals(1, sulfurRoutes.size());
        assertNotEquals(baseRoutes, sulfurRoutes);
    }
}
