package net.dusty_dusty.cts_compats.registry;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Version implements Comparable<Version> {
    private final String version;
    private final boolean matchesAll;
    private final List<Long> parts = new ArrayList<>();

    public Version(String version) {
        this.version = version;
        matchesAll = version.equals("*");
        if (matchesAll) {
            return;
        }

        String[] sections = version.replaceAll("[^a-zA-Z0-9.-]", "").split("-");
        for (String section : sections) {
            String[] components = section.split("\\.");
            for (String component : components) {
                parts.add(getAlphanumericNumber(component));
            }
        }
    }

    public String version() {
        return version;
    }

    @Override
    public int compareTo(@NotNull Version version) {
        if (matchesAll || version.matchesAll) {
            return 0;
        }
        for (int i = 0; i < parts.size() && i < version.parts.size(); i++) {
            int comparison = Long.compareUnsigned(parts.get(i), version.parts.get(i));
            if (comparison != 0) {
                return comparison;
            }
        }
        if (parts.size() == version.parts.size()) {
            return 0;
        }
        return parts.size() > version.parts.size() ? 1 : -1;
    }

    private static long getAlphanumericNumber(String alphanumeric) {
        if (!alphanumeric.matches("^[a-zA-Z0-9]*$")) {
            throw new IllegalArgumentException("Version part " + alphanumeric + " is not alphanumeric.");
        } else if (alphanumeric.length() > 20) {
            throw new ArithmeticException("Alphanumeric strings larger than 20 characters are not safe version parts (too large).");
        }

        long sum = 0;
        char[] characters = alphanumeric.toCharArray();
        int multiplier = 1;
        for (int i = characters.length - 1; i >= 0; i--) {
            char character = characters[i];

            if (Character.isLowerCase(character)) {
                sum += (long) (character - 'a' + 10) * multiplier;
            } else if (Character.isUpperCase(character)) {
                sum += (long) (character - 'A' + 36) * multiplier;
            } else {
                sum += (long) (character - '0') * multiplier;
            }
            multiplier *= 62;
        }

        return sum;
    }

    @Override
    public @NotNull String toString() {
        return version();
    }

    public static class Range implements Comparable<Version> {
        private boolean hasBounds = false;
        private boolean isLowerInclusive = true;
        private boolean isUpperInclusive = true;
        final Version upperBound;
        final Version lowerBound;

        protected Range() {
            lowerBound = new Version("*");
            upperBound = null;
        }

        protected Range(Version version) {
            lowerBound = version;
            upperBound = null;
        }

        protected Range(Version lowerBound, Version upperBound, boolean isLowerInclusive, boolean isUpperInclusive) {
            hasBounds = true;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.isLowerInclusive = isLowerInclusive;
            this.isUpperInclusive = isUpperInclusive;
            if (lowerBound.compareTo(upperBound) > -1 && !(lowerBound.matchesAll || upperBound.matchesAll)) {
                throw new IllegalArgumentException("Lower bound " + lowerBound
                        + " of a VersionFilter must be less than upper bound " + upperBound);
            }
        }

        public int compareTo(@NotNull Version version) {
            if (!hasBounds) {
                return version.compareTo(lowerBound);
            }
            int lowerCompare = version.compareTo(lowerBound);
            int upperCompare = version.compareTo(upperBound);
            lowerCompare = lowerCompare == 0 && !isLowerInclusive && !lowerBound.matchesAll ? -1 : lowerCompare;
            upperCompare = upperCompare == 0 && !isUpperInclusive && !upperBound.matchesAll ? 1 : upperCompare;

            if (lowerCompare < 0) {
                return -1;
            } else if (upperCompare > 0) {
                return 1;
            } else {
                return 0;
            }
        }

        public static Range acceptsAll() {
            return new Range();
        }

        public static Range acceptsExactly(String version) {
            return new Range(new Version(version));
        }

        public static Range acceptLaterThanInclusive(String version) {
            return new Range(new Version(version), new Version("*"), true, false);
        }

        public static Range acceptLaterThanExclusive(String version) {
            return new Range(new Version(version), new Version("*"), false, false);
        }

        public static Range acceptBetweenInclusive(String lowerBound, String upperBound) {
            return new Range(new Version(lowerBound), new Version(upperBound), true, true);
        }

        public static Range acceptBetweenExclusive(String lowerBound, String upperBound) {
            return new Range(new Version(lowerBound), new Version(upperBound), false, false);
        }

        public static Range acceptCustom(String lowerBound, String upperBound, boolean isLowerInclusive, boolean isUpperInclusive) {
            return new Range(new Version(lowerBound), new Version(upperBound), isLowerInclusive, isUpperInclusive);
        }
    }
}
