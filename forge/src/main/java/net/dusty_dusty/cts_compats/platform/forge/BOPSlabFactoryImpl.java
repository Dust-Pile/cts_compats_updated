package net.dusty_dusty.cts_compats.platform.forge;

import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.DriedSaltSlab;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.MossyBlackSandSlab;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.block.SandSlabBlockBOP;
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

public final class BOPSlabFactoryImpl {
    private BOPSlabFactoryImpl() {
    }

    public static Block sand(Block originalBlock) {
        return new ForgeSandSlab(originalBlock);
    }

    public static Block mossyBlackSand(Block originalBlock) {
        return new ForgeMossyBlackSandSlab(originalBlock);
    }

    public static Block driedSalt(Block originalBlock) {
        return new ForgeDriedSaltSlab(originalBlock);
    }

    private static final class ForgeSandSlab extends SandSlabBlockBOP {
        private ForgeSandSlab(Block originalBlock) {
            super(originalBlock);
        }

        @Override
        public boolean canSustainPlant(
                @NotNull BlockState state,
                @NotNull BlockGetter world,
                BlockPos pos,
                @NotNull Direction facing,
                IPlantable plantable
        ) {
            PlantType type = plantable.getPlantType(world, pos.relative(facing));
            if (type == PlantType.DESERT || type == PlantType.CAVE) {
                return true;
            }
            if (type == PlantType.BEACH) {
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockPos nearbyPos = pos.relative(direction);
                    BlockState nearbyState = world.getBlockState(nearbyPos);
                    FluidState nearbyFluid = world.getFluidState(nearbyPos);
                    if (nearbyFluid.is(FluidTags.WATER) || nearbyState.is(Blocks.FROSTED_ICE)) {
                        return true;
                    }
                }
            }
            return super.canSustainPlant(state, world, pos, facing, plantable);
        }
    }

    private static final class ForgeMossyBlackSandSlab extends MossyBlackSandSlab {
        private ForgeMossyBlackSandSlab(Block originalBlock) {
            super(originalBlock);
        }

        @Override
        public boolean canSustainPlant(
                @NotNull BlockState state,
                @NotNull BlockGetter world,
                BlockPos pos,
                @NotNull Direction facing,
                IPlantable plantable
        ) {
            return plantable.getPlantType(world, pos.relative(facing)) == PlantType.PLAINS;
        }
    }

    private static final class ForgeDriedSaltSlab extends DriedSaltSlab {
        private ForgeDriedSaltSlab(Block originalBlock) {
            super(originalBlock);
        }

        @Override
        public boolean canSustainPlant(
                @NotNull BlockState state,
                @NotNull BlockGetter world,
                BlockPos pos,
                @NotNull Direction facing,
                IPlantable plantable
        ) {
            PlantType type = plantable.getPlantType(world, pos.relative(facing));
            return type == PlantType.DESERT
                    || type == PlantType.NETHER
                    || type == PlantType.CAVE
                    || type == PlantType.PLAINS;
        }
    }
}
