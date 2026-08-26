package net.dusty_dusty.cts_compats.mixins.biomesOPlenty;

import biomesoplenty.block.*;
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
import org.spongepowered.asm.mixin.injection.At;

import static net.dusty_dusty.cts_compats.registry.AbstractRegistry.registerOffsetClasses;

@SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference"})
@Mixin( remap = false, value = {
        BlackstoneDecorationBlock.class,
        BrimstoneBudBlock.class,
        BrimstoneFumaroleBlock.class,
        HairBlock.class,
        PusBubbleBlock.class,
        BrambleLeavesBlock.class,
        SpiderEggBlock.class
})
public class MixinBlocksBeta {

    @WrapOperation( method = "m_7898_", require = 0, at = @At( value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelReader;m_8055_(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
    ) )
    private BlockState terrain_slabs$convertBlockState(
        LevelReader instance, BlockPos offPos, Operation<BlockState> original,
        BlockState state, LevelReader worldIn, BlockPos pos
    ) {
        return SlabHelper.terrain_slabs$convertBlockState( instance, offPos, original, state, worldIn, pos );
    }

    @SuppressWarnings("MixinAnnotationTarget")
    @WrapOperation( method = "m_214162_", require = 0, at = @At( value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;m_7106_(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"
    ) )
    private void terrain_slabs$offsetParticles(
            Level instance, ParticleOptions particleData,
            double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed,
            Operation<Void> original,
            BlockState state, Level level, BlockPos pos, RandomSource random
    ) {
        SlabHelper.terrain_slabs$offsetParticles(
                instance, particleData, x, y, z, xSpeed, ySpeed, zSpeed, original, state, level, pos, random
        );
    }
}

