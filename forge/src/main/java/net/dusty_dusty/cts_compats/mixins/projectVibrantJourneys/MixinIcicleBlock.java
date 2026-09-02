package net.dusty_dusty.cts_compats.mixins.projectVibrantJourneys;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.orderedchaos.projectvibrantjourneys.common.blocks.IcicleBlock;
import dev.orderedchaos.projectvibrantjourneys.core.registry.PVJBlocks;
import net.countered.terrainslabs.api.IConditionalOffset;
import net.countered.terrainslabs.api.ICustomOffsetConversion;
import net.countered.terrainslabs.api.SlabHelper;
import net.countered.terrainslabs.api.helperInterface.ISpikeConversion;
import net.countered.terrainslabs.block.OffsetProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin( IcicleBlock.class )
public class MixinIcicleBlock implements ISpikeConversion<DripstoneThickness>, IConditionalOffset {

    @Override
    public <L extends BlockGetter> boolean couldBeOntop(L level, BlockPos pos, BlockState state) {
        return state.getValue( IcicleBlock.TIP_DIRECTION ) == Direction.UP;
    }

    @Override
    public <L extends BlockGetter> boolean couldBeOnbottom(L level, BlockPos pos, BlockState state) {
        return state.getValue( IcicleBlock.TIP_DIRECTION ) == Direction.DOWN;
    }

    @WrapOperation( method = "isValidPointedDripstonePlacement", at = @At( value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelReader;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
    ) )
    private static BlockState terrain_slabs$convertBlockState(
            LevelReader instance, BlockPos offPos, Operation<BlockState> original,
            LevelReader level, BlockPos pos, Direction dir
    ) {
        BlockState state = PVJBlocks.ICICLE.get().defaultBlockState(); //Should not matter...
        return SlabHelper.terrain_slabs$convertBlockState( instance, offPos, original, state, level, pos );
    }

    @WrapOperation( method = "spawnDripParticle",
            at = @At( value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V" )
    )
    private static void terrain_slabs$offsetParticles(
            Level instance, ParticleOptions pParticleData,
            double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed,
            Operation<Void> original, Level level, BlockPos pos, BlockState state
    ) {
        SlabHelper.terrain_slabs$offsetParticles(
                instance, pParticleData, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, original, state, level, pos, null
        );
    }
}
