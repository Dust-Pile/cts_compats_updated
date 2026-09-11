package net.dusty_dusty.cts_compats.mods.weathering;

import net.dusty_dusty.cts_compats.registry.AbstractOptionRegistry;

import java.util.Set;

import static net.dusty_dusty.cts_compats.resources.ResourceOptions.BlockModelOption;

public class WeatheringOptionRegistry extends AbstractOptionRegistry {
    @Override
    public void register(OptionProvider provider) {
        provider
                .addBlocksForOption(
                        BlockModelOption.UV_TOP_EDGE_OVERLAY,
                        WeatheringRegistry.GRASSY_PERMAFROST.getId(),
                        WeatheringRegistry.GRASSY_SANDY_DIRT_SLAB.getId(),
                        WeatheringRegistry.GRASSY_SILT_SLAB.getId(),
                        WeatheringRegistry.ROOTED_GRASS_SLAB.getId()
                )
                .addOptions(WeatheringRegistry.ROOTED_GRASS_SLAB.getId(), BlockModelOption.UV_TOP_EDGE)
                .addOptions(WeatheringRegistry.LOAM_SLAB.getId(), BlockModelOption.UV_TOP_EDGE)
                .addOptions(WeatheringRegistry.GRASSY_PERMAFROST.getId(),
                        BlockModelOption.UV_TOP_EDGE, BlockModelOption.UV_OFF_BY_ONE);
    }
}
