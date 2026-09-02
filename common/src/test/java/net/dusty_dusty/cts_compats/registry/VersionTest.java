package net.dusty_dusty.cts_compats.registry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Version.Range releaseRange = Version.Range.acceptCustom("18.0.0.592", "19.0.0.96", true, false);
        Version.Range betaRange = Version.Range.acceptLaterThanInclusive("19.0.0.96");

        assertEquals(0, releaseRange.compareTo(new Version("19.0.0.95")));
        assertTrue(betaRange.compareTo(new Version("19.0.0.95")) < 0);
        assertTrue(releaseRange.compareTo(new Version("19.0.0.96")) > 0);
        assertEquals(0, betaRange.compareTo(new Version("19.0.0.96")));
    }

    @Test
    void routesVanillaBackportBoundaryAtOnePointOnePointSeven() {
        Version.Range baseRange = Version.Range.acceptCustom("*", "1.1.7", true, false);
        Version.Range sulfurRange = Version.Range.acceptLaterThanInclusive("1.1.7");

        assertEquals(0, baseRange.compareTo(new Version("1.1.6")));
        assertTrue(sulfurRange.compareTo(new Version("1.1.6")) < 0);
        assertTrue(baseRange.compareTo(new Version("1.1.7")) > 0);
        assertEquals(0, sulfurRange.compareTo(new Version("1.1.7")));
    }
}
