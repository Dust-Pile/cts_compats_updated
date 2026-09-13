package net.dusty_dusty.cts_compats.mods.weathering.block;

import net.countered.terrainslabs.block.customslabs.apiSlabs.FallableSnowySpreadableSlab;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.minecraft.world.level.block.Block;

public class GrassySandyDirtSlab extends FallableSnowySpreadableSlab {
    public GrassySandyDirtSlab(Block block, ISlabCopy duel) {
        super(block, duel);
    }

    @Override
    protected boolean canSpread() {
        return false;
    }
}
