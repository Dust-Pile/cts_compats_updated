package net.dusty_dusty.cts_compats.registry;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;

public abstract class AbstractColorRegistry implements IColorRegistry {

    public AbstractColorRegistry() {};

    protected static BlockColor getGrassColor() {
        return ( state, world, pos, tintIndex) -> (world != null && pos != null )
                ? BiomeColors.getAverageGrassColor( world, pos )
                : GrassColor.get( 0.5D, 1.0D );
    }

    protected static BlockColor getFoliageColor() {
        return (state, world, pos, tintIndex) -> (world != null && pos != null)
                ? BiomeColors.getAverageFoliageColor(world, pos)
                : FoliageColor.getDefaultColor();
    }
}
