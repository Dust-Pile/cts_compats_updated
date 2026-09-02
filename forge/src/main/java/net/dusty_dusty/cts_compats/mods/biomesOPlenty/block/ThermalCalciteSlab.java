package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import biomesoplenty.api.block.BOPBlocks;
import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.dusty_dusty.cts_compats.common.BlockCheckWrapper;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBetaRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class ThermalCalciteSlab extends CustomSlab {
    public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 1, 5);
    protected static BlockCheckWrapper.Group SOLID_THERMAL_CALCITE = new BlockCheckWrapper.Group();
    static {
        SOLID_THERMAL_CALCITE.add( BOPBlocks.THERMAL_CALCITE );
        SOLID_THERMAL_CALCITE.add( BOPBlocks.THERMAL_CALCITE_VENT );
        SOLID_THERMAL_CALCITE.add( state -> state.getBlock() instanceof ThermalCalciteSlab
                && state.getValue( TYPE ) == SlabType.DOUBLE
        );
    }

    public ThermalCalciteSlab( Block originalBlock ) {
        super( originalBlock );
        this.registerDefaultState( this.defaultBlockState().setValue( DISTANCE, 5 ) );
    }

    public void animateTick(@NotNull BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand ) {
        super.animateTick( stateIn, worldIn, pos, rand );
        if ( worldIn.getBlockState( pos.above() ).getFluidState().getType() == Fluids.WATER
                && worldIn.getBlockState( pos.above() ).getFluidState().getAmount() == 8
        ) {
            double yOffset = stateIn.getValue( TYPE ) == SlabType.BOTTOM ? 0.5 : 1.0 ;
            worldIn.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_COLUMN_UP,
                    pos.getX() + 0.5 + (rand.nextDouble() - rand.nextDouble()) / 2.0, pos.getY() + yOffset,
                    pos.getZ() + 0.5 + (rand.nextDouble() - rand.nextDouble()) / 2.0, 0.0F, 0.0, 0.0);
        }

    }

    public void tick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random ) {
        level.setBlock(pos, updateDistance(state, level, pos), 3);
    }

    public @NotNull InteractionResult use(
            @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player,
            @NotNull InteractionHand hand, @NotNull BlockHitResult hit
    ) {
        ItemStack itemstack = player.getItemInHand(hand);
        if ( itemstack.is( ItemTags.PICKAXES ) && this.getOriginBlock() == BOPBlocks.THERMAL_CALCITE ) {
            if (!level.isClientSide) {
                level.playSound( null, pos, SoundEvents.CALCITE_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F );
                level.setBlockAndUpdate( pos, BOPBetaRegistry.THERMAL_CALCITE_VENT_SLAB.get().withPropertiesOf( state ) );
                itemstack.hurtAndBreak( 1, player, ( plr ) -> plr.broadcastBreakEvent( hand ) );
                level.gameEvent( player, GameEvent.BLOCK_CHANGE, pos );
                player.awardStat( Stats.ITEM_USED.get( itemstack.getItem() ) );
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use( state, level, pos, player, hand, hit );
        }
    }

    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState state2, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos pos2 ) {
        state = super.updateShape( state, direction, state2, level, pos, pos2 );
        int i = getDistanceAt( state2 ) + 1;
        if ( i != 1 || state.getValue( DISTANCE ) != i ) {
            level.scheduleTick(pos, this, 1);
        }

        return state;
    }

    private static BlockState updateDistance( BlockState state, LevelAccessor level, BlockPos pos ) {
        int i = 5;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        if ( state.getValue( WATERLOGGED ) ) {
            return state.setValue( DISTANCE, 1 );
        }

        BlockState belowState = level.getBlockState( pos.below() );
        if ( state.getValue( TYPE ) == SlabType.BOTTOM && SOLID_THERMAL_CALCITE.check( belowState ) ) {
            return state.setValue( DISTANCE, belowState.getValue( DISTANCE ) );
        }
        BlockState aboveState = level.getBlockState( pos.above() );
        if ( state.getValue( TYPE ) == SlabType.TOP && SOLID_THERMAL_CALCITE.check( aboveState ) ) {
            return state.setValue( DISTANCE, aboveState.getValue( DISTANCE ) );
        }

        for( Direction direction : Direction.values() ) {
            blockpos$mutableblockpos.setWithOffset( pos, direction );
            i = Math.min( i, getDistanceAt(level.getBlockState(blockpos$mutableblockpos)) + 1 );
            if (i == 1) {
                break;
            }
        }

        return state.setValue(DISTANCE, i);
    }

    public static int getDistanceAt(BlockState state) {
        if (state.getFluidState().getType() != Fluids.WATER && state.getFluidState().getType()
                != Fluids.LAVA && !(state.getBlock() instanceof IceBlock) && !(state.getBlock() instanceof MagmaBlock)
        ) {
            return state.hasProperty( DISTANCE ) ? state.getValue( DISTANCE ) : 5;
        } else {
            return 0;
        }
    }

    protected void createBlockStateDefinition( StateDefinition.Builder<Block, BlockState> builder ) {
        builder.add( DISTANCE, WATERLOGGED, TYPE, GENERATED );
    }

    public BlockState getStateForPlacement( @NotNull BlockPlaceContext ctx ) {
        BlockState blockstate = super.getStateForPlacement( ctx );
        if ( blockstate == null ) {
            blockstate = defaultBlockState();
        }
        return updateDistance( blockstate, ctx.getLevel(), ctx.getClickedPos() );
    }
}