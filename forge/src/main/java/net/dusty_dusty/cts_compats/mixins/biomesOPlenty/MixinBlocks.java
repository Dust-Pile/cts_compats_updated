package net.dusty_dusty.cts_compats.mixins.biomesOPlenty;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.countered.terrainslabs.api.SlabHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Targets include all vanilla classes that need to be modified
 */
@SuppressWarnings("UnresolvedMixinReference")
@Pseudo
@Mixin( remap = false, targets = {
        "biomesoplenty.common.block.BlackstoneDecorationBlock",
        "biomesoplenty.common.block.BrimstoneBudBlock",
        "biomesoplenty.common.block.BrimstoneClusterBlock",
        "biomesoplenty.common.block.BrimstoneFumaroleBlock",
        "biomesoplenty.common.block.HairBlock",
        "biomesoplenty.common.block.PusBubbleBlock",
        "biomesoplenty.common.block.BrambleLeavesBlock",
        "biomesoplenty.common.block.SpiderEggBlock"
})
public class MixinBlocks {
    /**
     * When calling for the state below a block, pretends it's the matching full block when relevant.
     */
    @WrapOperation( method = "m_7898_", require = 0, at = @At( value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelReader;m_8055_(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
    ) )
    private BlockState terrain_slabs$convertBlockState(
            LevelReader instance, BlockPos offPos, Operation<BlockState> original,
            BlockState state, LevelReader level, BlockPos pos
    ) {
        return SlabHelper.terrain_slabs$convertBlockState( instance, offPos, original, state, level, pos );
    }

    /**
     * Fix particle position. Lazy implementation may need to be fixed later.
     */
    @WrapOperation( method = "m_214162_", require = 0, at =
            @At( value = "INVOKE", target = "Lnet/minecraft/world/level/Level;m_7106_(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V" )
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

