package net.dusty_dusty.cts_compats.mods.weathering.block;

import com.ordana.immersive_weathering.blocks.soil_types.PermafrostBlock;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.countered.terrainslabs.block.customslabs.specialslabs.GravityAffectedSlab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

@SuppressWarnings("deprecation")
public class PermafrostSlab extends GravityAffectedSlab {
    public PermafrostSlab(Block block) {
        super(block);
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        animatePermafrost(state, level, pos, random);
    }

    @Override
    protected boolean canPlaceAsTop() {
        return true;
    }

    @Override
    protected boolean scheduleFallOnUpdate() {
        return false;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (((PermafrostBlock) ModBlocks.PERMAFROST.get() ).canMelt(level, pos)) {
            level.scheduleTick(pos, this, this.getDelayAfterPlace());
        }
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity fallingBlock) {
        if (level.random.nextBoolean()) {
            level.destroyBlock(pos, false);
        } else {
            super.onLand(level, pos, state, replaceableState, fallingBlock);
        }
    }

    public static void animatePermafrost(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(25) == 1) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                double d0 = pos.getX() + random.nextDouble();
                double d1 = pos.getY() - 0.05 + (state.getValue(SlabBlock.TYPE) == SlabType.TOP ? 0.5 : 0);
                double d2 = pos.getZ() + random.nextDouble();
                level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0F, 0.0F, 0.0F);
            }
        }
    }
}
