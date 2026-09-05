package net.dusty_dusty.cts_compats.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.countered.terrainslabs.block.interfaces.IDuelSlab;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

final class SlabLootTable {
    private SlabLootTable() {
    }

    static JsonObject create(ResourceLocation slabId, ResourceLocation sourceLootTableId) {
        JsonArray children = new JsonArray();
        children.add(silkTouchEntry(slabId));
        children.add(generatedEntry(slabId, sourceLootTableId));
        children.add(slabEntry(slabId));

        JsonObject alternatives = new JsonObject();
        alternatives.addProperty("type", "minecraft:alternatives");
        alternatives.add("children", children);

        JsonArray entries = new JsonArray();
        entries.add(alternatives);

        JsonObject pool = new JsonObject();
        pool.addProperty("bonus_rolls", 0.0F);
        pool.add("entries", entries);
        pool.addProperty("rolls", 1.0F);

        JsonArray pools = new JsonArray();
        pools.add(pool);

        JsonObject table = new JsonObject();
        table.addProperty("type", "minecraft:block");
        table.add("pools", pools);
        return table;
    }

    static Block lootSource(ISlabCopy slab) {
        if (slab instanceof IDuelSlab duelSlab) {
            return duelSlab.getDuelBlock();
        }
        return slab.getOriginBlock();
    }

    private static JsonObject silkTouchEntry(ResourceLocation slabId) {
        JsonObject entry = slabEntry(slabId);
        JsonArray conditions = new JsonArray();
        conditions.add(silkTouchCondition());
        entry.add("conditions", conditions);
        return entry;
    }

    private static JsonObject generatedEntry(ResourceLocation slabId, ResourceLocation sourceLootTableId) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:loot_table");
        entry.addProperty("name", sourceLootTableId.toString());

        JsonArray conditions = new JsonArray();
        conditions.add(blockStateCondition(slabId, "generated", "true"));
        entry.add("conditions", conditions);
        entry.add("functions", doubleCountFunctions(slabId));
        return entry;
    }

    private static JsonObject slabEntry(ResourceLocation slabId) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", slabId.toString());
        entry.add("functions", doubleCountFunctions(slabId));
        return entry;
    }

    private static JsonObject silkTouchCondition() {
        JsonObject levels = new JsonObject();
        levels.addProperty("min", 1);

        JsonObject enchantment = new JsonObject();
        enchantment.addProperty("enchantment", "minecraft:silk_touch");
        enchantment.add("levels", levels);

        JsonArray enchantments = new JsonArray();
        enchantments.add(enchantment);

        JsonObject predicate = new JsonObject();
        predicate.add("enchantments", enchantments);

        JsonObject condition = new JsonObject();
        condition.addProperty("condition", "minecraft:match_tool");
        condition.add("predicate", predicate);
        return condition;
    }

    private static JsonArray doubleCountFunctions(ResourceLocation slabId) {
        JsonArray conditions = new JsonArray();
        conditions.add(blockStateCondition(slabId, "type", "double"));

        JsonObject function = new JsonObject();
        function.addProperty("add", false);
        function.add("conditions", conditions);
        function.addProperty("count", 2.0F);
        function.addProperty("function", "minecraft:set_count");

        JsonArray functions = new JsonArray();
        functions.add(function);
        return functions;
    }

    private static JsonObject blockStateCondition(
            ResourceLocation slabId,
            String property,
            String value
    ) {
        JsonObject properties = new JsonObject();
        properties.addProperty(property, value);

        JsonObject condition = new JsonObject();
        condition.addProperty("block", slabId.toString());
        condition.addProperty("condition", "minecraft:block_state_property");
        condition.add("properties", properties);
        return condition;
    }
}
