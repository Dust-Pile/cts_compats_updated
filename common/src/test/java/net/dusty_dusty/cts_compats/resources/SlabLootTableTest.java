package net.dusty_dusty.cts_compats.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.countered.terrainslabs.block.interfaces.IDuelSlab;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SlabLootTableTest {
    private static final ResourceLocation SLAB_ID = new ResourceLocation("cts_compats", "test_slab");
    private static final ResourceLocation SOURCE_LOOT = new ResourceLocation("example", "blocks/test_block");

    @BeforeAll
    static void bootstrapMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    void generatedStatesReferenceTheSourceLootTable() {
        JsonObject generated = children(SlabLootTable.create(SLAB_ID, SOURCE_LOOT)).get(1).getAsJsonObject();

        assertEquals("minecraft:loot_table", generated.get("type").getAsString());
        assertEquals("example:blocks/test_block", generated.get("name").getAsString());
        assertEquals(
                "true",
                generated.getAsJsonArray("conditions").get(0).getAsJsonObject()
                        .getAsJsonObject("properties").get("generated").getAsString()
        );
    }

    @Test
    void silkTouchAndFallbackDropCurrentSlabs() {
        JsonArray children = children(SlabLootTable.create(SLAB_ID, SOURCE_LOOT));
        JsonObject silkTouch = children.get(0).getAsJsonObject();
        JsonObject fallback = children.get(2).getAsJsonObject();

        assertEquals("cts_compats:test_slab", silkTouch.get("name").getAsString());
        assertEquals("minecraft:match_tool", silkTouch.getAsJsonArray("conditions").get(0)
                .getAsJsonObject().get("condition").getAsString());
        assertEquals("cts_compats:test_slab", fallback.get("name").getAsString());
        assertDoubleSlabCount(silkTouch);
        assertDoubleSlabCount(fallback);
    }

    @Test
    void generatedDoubleStatesProduceTwoSourceDrops() {
        JsonObject generated = children(SlabLootTable.create(SLAB_ID, SOURCE_LOOT)).get(1).getAsJsonObject();

        assertDoubleSlabCount(generated);
    }

    @Test
    void duelSlabsUseTheDuelOriginForGeneratedDrops() {
        ISlabCopy duel = () -> Blocks.DIRT;
        IDuelSlab slab = new IDuelSlab() {
            @Override
            public ISlabCopy getDuel() {
                return duel;
            }

            @Override
            public Block getOriginBlock() {
                return Blocks.GRASS_BLOCK;
            }
        };

        assertEquals(Blocks.DIRT, SlabLootTable.lootSource(slab));
    }

    private static JsonArray children(JsonObject table) {
        return table.getAsJsonArray("pools").get(0).getAsJsonObject()
                .getAsJsonArray("entries").get(0).getAsJsonObject()
                .getAsJsonArray("children");
    }

    private static void assertDoubleSlabCount(JsonObject entry) {
        JsonObject function = entry.getAsJsonArray("functions").get(0).getAsJsonObject();
        JsonObject condition = function.getAsJsonArray("conditions").get(0).getAsJsonObject();

        assertEquals(2.0F, function.get("count").getAsFloat());
        assertEquals("double", condition.getAsJsonObject("properties").get("type").getAsString());
    }
}
