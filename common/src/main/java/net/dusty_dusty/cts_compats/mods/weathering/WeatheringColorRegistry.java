package net.dusty_dusty.cts_compats.mods.weathering;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.dusty_dusty.cts_compats.registry.AbstractColorRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class WeatheringColorRegistry extends AbstractColorRegistry {

    @Override
    public void registerBlockColors() {
        ColorHandlerRegistry.registerBlockColors(getGrassColor(),
                WeatheringRegistry.GRASSY_EARTHEN_CLAY_SLAB,
                WeatheringRegistry.GRASSY_SANDY_DIRT_SLAB,
                WeatheringRegistry.GRASSY_SILT_SLAB,
                WeatheringRegistry.GRASSY_PERMAFROST,
                WeatheringRegistry.ROOTED_GRASS_SLAB
        );
    }

    @Override
    public void registerItemColors() {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();

        ColorHandlerRegistry.registerItemColors((itemstack, tintIndex) -> {
                    BlockState state = Blocks.GRASS.defaultBlockState();
                    return blockColors.getColor(state, null, null, tintIndex);
                },
                WeatheringRegistry.GRASSY_EARTHEN_CLAY_SLAB,
                WeatheringRegistry.GRASSY_SANDY_DIRT_SLAB,
                WeatheringRegistry.GRASSY_SILT_SLAB,
                WeatheringRegistry.GRASSY_PERMAFROST,
                WeatheringRegistry.ROOTED_GRASS_SLAB
        );
    }
}
