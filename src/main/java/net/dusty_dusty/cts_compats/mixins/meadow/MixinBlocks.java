package net.dusty_dusty.cts_compats.mixins.meadow;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.countered.terrainslabs.api.SlabHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@SuppressWarnings({"MixinAnnotationTarget"})
@Mixin( targets = {
        "net.satisfy.meadow.core.block.ClimbingRopeTopmountBlock",
        "net.satisfy.meadow.core.block.OilLantern"
})
public class MixinBlocks {

//    @WrapOperation( method = "canSurvive", require = 0, at = @At( value = "INVOKE",
//            target = "Lnet/minecraft/world/level/LevelReader;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;")
//    )
//    private BlockState terrain_slabs$convertBlockState(
//            LevelReader instance, BlockPos offPos, Operation<BlockState> original,
//            BlockState state, LevelReader world, BlockPos pos
//    ) {
//        return SlabHelper.terrain_slabs$convertBlockState( instance, offPos, original, state, world, pos );
//    }

    @WrapOperation( method = "canSurvive", require = 0, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/Block;canSupportCenter(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z")
    )
    private boolean terrain_slabs$slabsSupportCenter(
            LevelReader pLevel, BlockPos pPos, Direction pDirection, Operation<Boolean> original,
            BlockState state, LevelReader world, BlockPos pos
    ) {
        return SlabHelper.terrain_slabs$slabsSupportCenter(
                pLevel, pPos, pDirection, original, state, world, pos
        );
    }

    @WrapOperation( method = "animateTick", require = 0, at = @At( value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V")
    )
    private void terrain_slabs$offsetParticles(
            Level instance, ParticleOptions particleData, double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed, Operation<Void> original,
            BlockState state, Level world, BlockPos pos, RandomSource random
    ) {
        SlabHelper.terrain_slabs$offsetParticles(
                instance, particleData, x, y, z, xSpeed, ySpeed, zSpeed, original, state, world, pos, random
        );
    }
}
