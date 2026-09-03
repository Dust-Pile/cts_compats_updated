package net.dusty_dusty.cts_compats.mixins.projectVibrantJourneys;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.countered.terrainslabs.api.SlabHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings({"MixinAnnotationTarget", "InvalidInjectorMethodSignature"})
@Mixin( targets = {
        "dev.orderedchaos.projectvibrantjourneys.common.blocks.CindercaneBlock",
        "dev.orderedchaos.projectvibrantjourneys.common.blocks.FallenLeavesBlock",
        "dev.orderedchaos.projectvibrantjourneys.common.blocks.GroundcoverBlock",
        "dev.orderedchaos.projectvibrantjourneys.common.blocks.BeachedKelpBlock"
})
public class MixinBlocks {

    @WrapOperation( method = "canSurvive", require = 0, at = @At( value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelReader;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;")
    )
    private BlockState terrain_slabs$convertBlockState(
            LevelReader instance, BlockPos offPos, Operation<BlockState> original,
            BlockState state, LevelReader world, BlockPos pos
    ) {
        return SlabHelper.terrain_slabs$convertBlockState( instance, offPos, original, state, world, pos );
    }

    @WrapOperation( method = "canSurvive", require = 0, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/Block;isFaceFull(Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/core/Direction;)Z")
    )
    private boolean terrain_slabs$slabsSupportCenter(
            VoxelShape pShape, Direction pFace, Operation<Boolean> original,
            BlockState state, LevelReader world, BlockPos pos
    ) {
        boolean origOutput = original.call(pShape, pFace);
        return SlabHelper.terrain_slabs$slabsSupportGeneric(
                world, pos.relative(pFace), pFace, origOutput, state, world, pos
        );
    }

    @WrapOperation( method = "canSurvive", require = 0, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/Block;canSupportRigidBlock(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z")
    )
    private boolean terrain_slabs$slabsStillSupportCenter(
            BlockGetter pLevel, BlockPos pPos, Operation<Boolean> original,
            BlockState state, LevelReader world, BlockPos pos
    ) {
        boolean origOutput = original.call(world, pPos);
        return SlabHelper.terrain_slabs$slabsSupportGeneric(
                world, pPos, Direction.UP, origOutput, state, world, pos
        );
    }

//    @WrapOperation( method = "animateTick", require = 0, at = @At( value = "INVOKE",
//        target = "Lnet/minecraft/world/level/Level;m_7106_(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V")
//    )
//    private void terrain_slabs$offsetParticles(
//            Level instance, ParticleOptions particleData,
//            double x, double y, double z,
//            double xSpeed, double ySpeed, double zSpeed,
//            Operation<Void> original,
//            BlockState state, Level level, BlockPos pos, RandomSource random
//    ) {
//        SlabHelper.terrain_slabs$offsetParticles(
//                instance, particleData, x, y, z, xSpeed, ySpeed, zSpeed, original, state, level, pos, random
//        );
//    }
}

