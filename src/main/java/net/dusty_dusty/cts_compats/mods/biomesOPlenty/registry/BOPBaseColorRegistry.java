package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import net.dusty_dusty.cts_compats.registry.AbstractColorRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public final class BOPBaseColorRegistry extends AbstractColorRegistry {
    @Override
    public void onColorHandlerEventBlock(RegisterColorHandlersEvent.Block event) {
        event.register(getGrassColor(),
                BOPBaseRegistry.MOSSY_BLACK_SAND_SLAB.get()
        );
    }

    @Override
    public void onColorHandlerEventItem(RegisterColorHandlersEvent.Item event) {
        BlockColors blockColors = event.getBlockColors();

        event.register((itemstack, tintIndex) -> {
                    BlockState state = Blocks.GRASS.defaultBlockState();
                    return blockColors.getColor(state, null, null, tintIndex);
                },
                BOPBaseRegistry.MOSSY_BLACK_SAND_SLAB.get()
        );
    }
}