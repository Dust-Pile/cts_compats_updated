package net.dusty_dusty.cts_compats.mods.weathering.block;

import com.ordana.immersive_weathering.blocks.soil_types.BaseSoilBlockFallable;
import com.ordana.immersive_weathering.blocks.soil_types.PermafrostBlock;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.countered.terrainslabs.block.customslabs.apiSlabs.FallableSnowySpreadableSlab;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class GrassyPermafrostSlab extends FallableSnowySpreadableSlab {
    public GrassyPermafrostSlab(Block block, ISlabCopy duel) {
        super(block, duel);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        PermafrostSlab.animatePermafrost(state, level, pos, random);
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
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (((PermafrostBlock) ModBlocks.PERMAFROST.get() ).canMelt(level, pos)) {
            level.scheduleTick(pos, this, this.getDelayAfterPlace());
        }
        if (!BaseSoilBlockFallable.canBeGrass(state, level, pos)) {
            level.setBlockAndUpdate(pos, this.getDuel().getBlock().withPropertiesOf(state));
        }

        super.randomTick(state, level, pos, random);
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity fallingBlock) {
        if (level.random.nextBoolean()) {
            level.destroyBlock(pos, false);
        } else {
            super.onLand(level, pos, state, replaceableState, fallingBlock);
        }
    }
}
