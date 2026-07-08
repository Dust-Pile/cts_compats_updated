package net.dusty_dusty.cts_compats.mixins.biomesOPlenty;

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

/**
 * Targets include all vanilla classes that need to be modified
 */
@SuppressWarnings({"MixinAnnotationTarget", "InvalidInjectorMethodSignature"})
@Mixin( targets = {
        "biomesoplenty.block.WildflowerBlock",
        "biomesoplenty.block.YellowMapleLeavesBlock",
        "biomesoplenty.block.BlackstoneDecorationBlock",
        "biomesoplenty.block.BrimstoneBudBlock",
        "biomesoplenty.block.BrimstoneClusterBlock",
        "biomesoplenty.block.FoliageBlockBOP",
        "biomesoplenty.block.FlowerBlockBOP",
        "biomesoplenty.block.BrimstoneFumaroleBlock",
        "biomesoplenty.block.FleshTendonsBlock",
        "biomesoplenty.block.HangingStrandBlock",
        "biomesoplenty.block.FleshTendonsBottomBlock",
        "biomesoplenty.block.HangingStrandBottomBlock",
        "biomesoplenty.block.EyebulbBlock",
        "biomesoplenty.block.HairBlock",
        "biomesoplenty.block.PusBubbleBlock",
        "biomesoplenty.block.SpanishMossBottomBlock",
        "biomesoplenty.block.SpanishMossBlock",
        "biomesoplenty.block.HighGrassBlock",
        "biomesoplenty.block.HighGrassPlantBlock",
        "biomesoplenty.block.BrambleLeavesBlock",
        "biomesoplenty.block.MushroomBlockBOP",
        "biomesoplenty.block.GlowwormSilkBottomBlock",
        "biomesoplenty.block.GlowwormSilkBlock",
        "biomesoplenty.block.SpiderEggBlock",
        "biomesoplenty.block.HangingCobwebBottomBlock",
        "biomesoplenty.block.HangingCobwebBlock",
        "biomesoplenty.block.DoubleWaterPlantBlock",
        "biomesoplenty.block.DoubleWatersidePlantBlock",
        "biomesoplenty.block.SeaOatsBlock"
})
public class MixinBlocksBeta {
    /**
     * When calling for the state below a block, pretends it's the matching full block when relevant.
     */
    @SuppressWarnings({"MixinAnnotationTarget", "InvalidInjectorMethodSignature"})
    @WrapOperation( method = "canSurvive", require = 0, at = @At( value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelReader;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
    ) )
    private BlockState terrain_slabs$convertBlockState(
            LevelReader instance, BlockPos offPos, Operation<BlockState> original,
            BlockState state, LevelReader level, BlockPos pos
    ) {
        return SlabHelper.terrain_slabs$convertBlockState( instance, offPos, original, state, level, pos );
    }

    /**
     * When checking if below block "can support center", gives true for the top face of a bottom slab.
     */
    @SuppressWarnings({"MixinAnnotationTarget", "InvalidInjectorMethodSignature"})
    @WrapOperation( method = "canSurvive", require = 0, at = {
            @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;canSupportCenter(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z"),
            @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/TorchBlock;canSupportCenter(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z")
    } )
    private boolean terrain_slabs$slabsSupportCenter(
            LevelReader instance, BlockPos offsetPos, Direction direction, Operation<Boolean> original,
            BlockState state, LevelReader level, BlockPos pos
    ) {
        return SlabHelper.terrain_slabs$slabsSupportCenter(
                instance, offsetPos, direction, original, state, level, pos
        );
    }

    /**
     * Fix particle position. Lazy implementation may need to be fixed later.
     */
    @WrapOperation( method = "animateTick", require = 0, at =
            @At( value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V" )
    )
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

