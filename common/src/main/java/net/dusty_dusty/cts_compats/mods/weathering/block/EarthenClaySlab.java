package net.dusty_dusty.cts_compats.mods.weathering.block;

import com.ordana.immersive_weathering.blocks.soil_types.EarthenClayBlock;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class EarthenClaySlab extends CustomSlab {
    public static final BooleanProperty HALF_WATERLOGGED;

    public EarthenClaySlab(Block block) {
        super(block);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF_WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(HALF_WATERLOGGED);
    }

    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState superState = super.getStateForPlacement(context);
        assert superState != null;
        return superState.getValue(WATERLOGGED) ? superState.setValue(HALF_WATERLOGGED, true) : superState;
    }

    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED) || state.getValue(HALF_WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            state.setValue(HALF_WATERLOGGED, true);
        }

        return state;
    }

    public @NotNull FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        } else if (state.getValue(HALF_WATERLOGGED)) {
            return Fluids.WATER.defaultFluidState().setValue(BlockStateProperties.LEVEL_FLOWING, 4);
        }

        return super.getFluidState(state);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
        if (!blockState.getValue(WATERLOGGED)) {
            if ((blockState.getValue(HALF_WATERLOGGED) || !serverLevel.isRainingAt(pos.above())) && !EarthenClayBlock.isNearWater(serverLevel, pos)) {
                if (blockState.getValue(HALF_WATERLOGGED) && serverLevel.dimensionType().ultraWarm()) {
                    serverLevel.setBlock(pos, blockState.setValue(HALF_WATERLOGGED, false), 2);
                }
            } else {
                serverLevel.setBlock(pos, blockState.setValue(HALF_WATERLOGGED, true), 2);
            }
        }

        super.randomTick(blockState, serverLevel, pos, randomSource);
    }

    static {
        HALF_WATERLOGGED = BooleanProperty.create("half_waterlogged");
    }
}
