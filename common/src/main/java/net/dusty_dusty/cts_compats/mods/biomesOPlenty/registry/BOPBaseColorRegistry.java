package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.dusty_dusty.cts_compats.registry.AbstractColorRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class BOPBaseColorRegistry extends AbstractColorRegistry {
    @Override
    public void registerBlockColors() {
        ColorHandlerRegistry.registerBlockColors(getGrassColor(),
                BOPBaseRegistry.MOSSY_BLACK_SAND_SLAB
        );
    }

    @Override
    public void registerItemColors() {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();

        ColorHandlerRegistry.registerItemColors((itemstack, tintIndex) -> {
                    BlockState state = Blocks.GRASS.defaultBlockState();
                    return blockColors.getColor(state, null, null, tintIndex);
                },
                BOPBaseRegistry.MOSSY_BLACK_SAND_SLAB
        );
    }
}
