package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.countered.terrainslabs.block.customslabs.specialslabs.GravityAffectedSlab;
import net.countered.terrainslabs.block.interfaces.IDuelSlab;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBaseRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class MossyBlackSandSlab extends GravityAffectedSlab implements IDuelSlab {

    public MossyBlackSandSlab( Block originalBlock ) {
        super( originalBlock );
    }

    @Override
    public ISlabCopy getDuel() {
        return (ISlabCopy) BOPBaseRegistry.BLACK_SAND_SLAB.get();
    }

    public boolean canSustainPlant(@NotNull BlockState state, @NotNull BlockGetter world, BlockPos pos, @NotNull Direction facing, IPlantable plantable) {
        PlantType type = plantable.getPlantType(world, pos.relative(facing));
        return type == PlantType.PLAINS;
    }

    private static boolean canBeGrass( BlockState state, LevelReader level, BlockPos pos ) {
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
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if ( !canBeGrass(state, level, pos ) ) {
            if ( !level.isAreaLoaded( pos, 1 ) ) {
                return;
            }

            level.setBlockAndUpdate( pos, ( BOPBaseRegistry.BLACK_SAND_SLAB.get() ).withPropertiesOf( state ) );
        }
    }

    @Override
    public void onLand(@NotNull Level world, @NotNull BlockPos pos, BlockState fallingBlockState, @NotNull BlockState currentStateInPos, @NotNull FallingBlockEntity fallingBlockEntity) {
        super.onLand( world, pos, fallingBlockState, currentStateInPos, fallingBlockEntity );
        if ( world.getBlockState( pos.below() ).is( this.getOriginBlock() ) ) {
            world.setBlockAndUpdate( pos.below(), this.getDuelBlock().defaultBlockState() );
        }
    }
}