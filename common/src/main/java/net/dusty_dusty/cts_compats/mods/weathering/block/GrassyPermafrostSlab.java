package net.dusty_dusty.cts_compats.mods.weathering.block;

import com.ordana.immersive_weathering.blocks.soil_types.BaseSoilBlockFallable;
import com.ordana.immersive_weathering.blocks.soil_types.PermafrostBlock;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.countered.terrainslabs.block.customslabs.apiSlabs.FallableSnowyGrassySlab;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class GrassyPermafrostSlab extends FallableSnowyGrassySlab {
    public GrassyPermafrostSlab(Block block, ISlabCopy duel) {
        super(block, duel);
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        PermafrostSlab.animatePermafrost(state, level, pos, random);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        if (((PermafrostBlock) ModBlocks.PERMAFROST ).canMelt(level, pos)) {
            level.scheduleTick(pos, this, this.getDelayAfterPlace());
        }
        if (!BaseSoilBlockFallable.canBeGrass(state, level, pos)) {
            level.setBlockAndUpdate(pos, this.getDuel().getBlock().withPropertiesOf(state));
        }
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
    }

    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return state;
    }

    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity fallingBlock) {
        if (level.random.nextBoolean()) {
            level.destroyBlock(pos, false);
        } else {
            super.onLand(level, pos, state, replaceableState, fallingBlock);
        }
    }
}
