package net.dusty_dusty.cts_compats.mods.weathering;

import net.dusty_dusty.cts_compats.registry.AbstractOptionRegistry;
import net.dusty_dusty.cts_compats.resources.ResourceOptions;

import static net.dusty_dusty.cts_compats.resources.ResourceOptions.BlockModelFlags;

public class WeatheringOptionRegistry extends AbstractOptionRegistry {
    @Override
    public void register(OptionProvider provider) {
        provider
                .addBlocksForOption(
                        BlockModelFlags.UV_TOP_EDGE_OVERLAY,
                        WeatheringRegistry.GRASSY_PERMAFROST.getId(),
                        WeatheringRegistry.GRASSY_SANDY_DIRT_SLAB.getId(),
                        WeatheringRegistry.GRASSY_SILT_SLAB.getId(),
                        WeatheringRegistry.ROOTED_GRASS_SLAB.getId(),
                        WeatheringRegistry.GRASSY_EARTHEN_CLAY_SLAB.getId()
                )
                .addOptions(WeatheringRegistry.ROOTED_GRASS_SLAB.getId(), BlockModelFlags.UV_TOP_EDGE)
                .addOptions(WeatheringRegistry.LOAM_SLAB.getId(), BlockModelFlags.UV_TOP_EDGE)
                .addOptions(WeatheringRegistry.GRASSY_PERMAFROST.getId(),
                        BlockModelFlags.UV_TOP_EDGE, BlockModelFlags.UV_OFF_BY_ONE)
                .addBlocksForOption(
                        ResourceOptions.variantModifierOf(s -> s.replace("waterlogged", "half_waterlogged")),
                        WeatheringRegistry.EARTHEN_CLAY_SLAB.getId(),
                        WeatheringRegistry.GRASSY_EARTHEN_CLAY_SLAB.getId()
                );
    }
}
