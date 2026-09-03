package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class FleshSlab extends CustomSlab {

    public FleshSlab(Block originalBlock) {
        super(originalBlock);
    }

    public void stepOn(@NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState blockState, Entity entityIn) {
        entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(0.95, 1.0F, 0.95));
    }
}
