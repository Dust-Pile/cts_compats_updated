package net.dusty_dusty.cts_compats.mods.weathering;

import net.dusty_dusty.cts_compats.registry.AbstractOptionRegistry;

import java.util.Set;

import static net.dusty_dusty.cts_compats.resources.ResourceOptions.BlockModelOption;

public class WeatheringOptionRegistry extends AbstractOptionRegistry {
    @Override
    public void register(OptionProvider provider) {
        provider
                .addBlocksForOptions(
                        Set.of(
                            BlockModelOption.UV_TOP_EDGE_OVERLAY,
                            BlockModelOption.TINT_INDEX_TOP,
                            BlockModelOption.TINT_INDEX_OVERLAY
                        ),
        //                WeatheringRegistry.GRASSY_EARTHEN_CLAY_SLAB,
                        WeatheringRegistry.GRASSY_SANDY_DIRT_SLAB,
                        WeatheringRegistry.GRASSY_SILT_SLAB,
                        WeatheringRegistry.GRASSY_PERMAFROST,
                        WeatheringRegistry.ROOTED_GRASS_SLAB
                )
                .addOptions(WeatheringRegistry.LOAM_SLAB, BlockModelOption.UV_TOP_EDGE);
    }
}
