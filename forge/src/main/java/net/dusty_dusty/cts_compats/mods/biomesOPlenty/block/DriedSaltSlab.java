package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import net.countered.terrainslabs.block.customslabs.specialslabs.CustomSlab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class DriedSaltSlab extends CustomSlab {

    public DriedSaltSlab(Block originalBlock) {
        super(originalBlock);
    }

    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        PlantType type = plantable.getPlantType(world, pos.relative(facing));
        if (type == PlantType.DESERT) {
            return true;
        } else if (type == PlantType.NETHER) {
            return true;
        } else if (type == PlantType.CAVE) {
            return true;
        } else {
            return type == PlantType.PLAINS;
        }
    }
}