package net.dusty_dusty.cts_compats.fabric;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FabricMetadataTest {
    @Test
    void declaresRequiredAndSuggestedMods() throws Exception {
        JsonObject metadata = readMetadata();
        JsonObject depends = metadata.getAsJsonObject("depends");

        assertEquals(">=0.17.3", depends.get("fabricloader").getAsString());
        assertEquals(">=0.92.6+1.20.1", depends.get("fabric-api").getAsString());
        assertEquals(">=9.2.14", depends.get("architectury").getAsString());
        assertEquals("1.20.1", depends.get("minecraft").getAsString());
        assertEquals(">=17", depends.get("java").getAsString());
        assertEquals(">=4.0.3-beta", depends.get("terrain_slabs").getAsString());
        assertFalse(depends.has("biomesoplenty"));
        assertFalse(depends.has("meadow"));
        assertFalse(depends.has("vanillabackport"));

        JsonObject suggests = metadata.getAsJsonObject("suggests");
        assertNotNull(suggests);
        assertEquals(">=19.0.0.96", suggests.get("biomesoplenty").getAsString());
        assertEquals(">=1.3.25", suggests.get("meadow").getAsString());
        assertEquals(">=1.1.7.10", suggests.get("vanillabackport").getAsString());
    }

    private static JsonObject readMetadata() throws Exception {
        try (InputStream resource = FabricMetadataTest.class.getResourceAsStream("/fabric.mod.json")) {
            assertNotNull(resource);
            return JsonParser.parseReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }
}
