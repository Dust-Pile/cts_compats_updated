package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.countered.terrainslabs.block.interfaces.IDuelSlab;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import org.jetbrains.annotations.NotNull;

public class AlgalEndStoneSlab extends CustomSlab implements IDuelSlab {
    private final Block DUEL;

    public AlgalEndStoneSlab( Block originalBlock, Block duel ) {
        super(originalBlock);
        this.DUEL = duel;
    }

    @Override
    public ISlabCopy getDuel() {
        return (ISlabCopy) DUEL;
    }

    private static boolean canBeGrass(BlockState state, LevelReader level, BlockPos pos ) {
        BlockPos blockpos = pos.above();
        BlockState blockAboveState = level.getBlockState(blockpos);
        if (blockAboveState.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(level, state, pos, blockAboveState, blockpos, Direction.UP, blockAboveState.getLightBlock( level, blockpos ) );
            return i < level.getMaxLightLevel();
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if ( !canBeGrass(state, level, pos ) ) {
            if ( !level.isAreaLoaded( pos, 1 ) ) {
                return;
            }

            level.setBlockAndUpdate( pos, ( getDuel().getBlock() ).withPropertiesOf( state ) );
        }
    }
}
