package net.dusty_dusty.cts_compats.mixins.vanillaBackport;

import com.blackgear.vanillabackport.common.level.blocks.SpeleothemBlock;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.orderedchaos.projectvibrantjourneys.common.blocks.IcicleBlock;
import net.countered.terrainslabs.api.IConditionalOffset;
import net.countered.terrainslabs.api.SlabHelper;
import net.countered.terrainslabs.api.helperInterface.ISpikeConversion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin( SpeleothemBlock.class )
public class MixinSpeleothemBlock implements ISpikeConversion<DripstoneThickness>, IConditionalOffset {

    @Override
    public <L extends BlockGetter> boolean couldBeOntop(L level, BlockPos pos, BlockState state) {
        return state.getValue( IcicleBlock.TIP_DIRECTION ) == Direction.UP;
    }

    @Override
    public <L extends BlockGetter> boolean couldBeOnbottom(L level, BlockPos pos, BlockState state) {
        return state.getValue( IcicleBlock.TIP_DIRECTION ) == Direction.DOWN;
    }

    @WrapOperation( method = "isValidSpeleothemPlacement", at = @At( value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelReader;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
    ) )
    private static BlockState terrain_slabs$convertBlockState(
            LevelReader instance, BlockPos offPos, Operation<BlockState> original,
            LevelReader level, BlockPos pos, Direction dir
    ) {
        BlockState state = ModBlocks.SULFUR_SPIKE.get().defaultBlockState(); //Should not matter...
        return SlabHelper.terrain_slabs$convertBlockState( instance, offPos, original, state, level, pos );
    }
}
