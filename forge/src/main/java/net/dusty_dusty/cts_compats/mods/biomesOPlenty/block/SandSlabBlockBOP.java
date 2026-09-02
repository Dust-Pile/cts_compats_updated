package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.countered.terrainslabs.block.customslabs.specialslabs.GravityAffectedSlab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

public class SandSlabBlockBOP extends GravityAffectedSlab {

    public SandSlabBlockBOP(Block originalBlock) {
        super(originalBlock);
    }

    @Override
    public boolean canSustainPlant(@NotNull BlockState state, @NotNull BlockGetter world, BlockPos pos, @NotNull Direction facing, IPlantable plantable) {
        PlantType type = plantable.getPlantType(world, pos.relative(facing));
        if (type == PlantType.DESERT) {
            return true;
        } else if (type == PlantType.CAVE) {
            return true;
        } else {
            if (type == PlantType.BEACH) {
                for(Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockState blockstate1 = world.getBlockState(pos.relative(direction));
                    FluidState fluidstate = world.getFluidState(pos.relative(direction));
                    if (fluidstate.is(FluidTags.WATER) || blockstate1.is(Blocks.FROSTED_ICE)) {
                        return true;
                    }
                }
            }

            return super.canSustainPlant(state, world, pos, facing, plantable);
        }
    }
}
