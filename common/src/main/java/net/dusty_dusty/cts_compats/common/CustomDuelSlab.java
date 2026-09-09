package net.dusty_dusty.cts_compats.common;

import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.block.interfaces.IDuelSlab;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.minecraft.world.level.block.Block;

public class CustomDuelSlab extends CustomSlab implements IDuelSlab {
    private final ISlabCopy duel;

    public CustomDuelSlab(Block block, ISlabCopy duel) {
        super(block);
        this.duel = duel;
    }

    @Override
    public ISlabCopy getDuel() {
        return duel;
    }
}
