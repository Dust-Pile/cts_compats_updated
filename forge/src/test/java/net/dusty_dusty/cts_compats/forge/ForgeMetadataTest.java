package net.dusty_dusty.cts_compats.forge;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.toml.TomlParser;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ForgeMetadataTest {
    @Test
    void declaresRequiredRuntimeDependencies() throws Exception {
        UnmodifiableConfig metadata = readMetadata();

        assertAll(
                () -> assertRequiredDependency(metadata, "architectury", "[9.2.14,)"),
                () -> assertRequiredDependency(metadata, "terrain_slabs", "[4.0.3-beta,)")
        );
    }

    private static void assertRequiredDependency(UnmodifiableConfig metadata, String modId, String versionRange) {
        UnmodifiableConfig dependency = findDependency(metadata, modId);

        assertEquals(true, dependency.get("mandatory"));
        assertEquals(versionRange, dependency.get("versionRange"));
        assertEquals("AFTER", dependency.get("ordering"));
        assertEquals("BOTH", dependency.get("side"));
    }

    private static UnmodifiableConfig findDependency(UnmodifiableConfig metadata, String modId) {
        List<UnmodifiableConfig> dependencies = metadata.get("dependencies.cts_compats");
        return dependencies.stream()
                .filter(dependency -> modId.equals(dependency.get("modId")))
                .findFirst()
                .orElseThrow();
    }

    private static UnmodifiableConfig readMetadata() throws Exception {
        try (InputStream resource = ForgeMetadataTest.class.getResourceAsStream("/META-INF/mods.toml")) {
            assertNotNull(resource);
            return new TomlParser().parse(new InputStreamReader(resource, StandardCharsets.UTF_8));
        }
    }
}
