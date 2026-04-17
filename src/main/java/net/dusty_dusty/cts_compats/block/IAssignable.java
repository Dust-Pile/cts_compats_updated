package net.dusty_dusty.cts_compats.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface IAssignable {

    default void assign() {
//        switch ( this.getCopyType() ) {
//            case ON_TOP:
//                AssignUtil.putOnTopVegetation( this.getOriginBlock(), (Block) this );
//                AssignUtil.putVegetationOnTopItem( this.getOriginalItem(), (Block) this );
//                break;
//            case SLAB:
//                AssignUtil.putTerrainSlab( this.getOriginBlock(), (Block) this );
//                if ( this instanceof IDuelSlab duelSlab ) {
//                    BlockCopyWrapper duel = new BlockCopyWrapper( duelSlab.getDuelSlab() );
//
//                    AssignUtil.putTopSlabReplacement( (Block) this, duelSlab.getDuel() );
//                    AssignUtil.putBlockBelowReplacement( (Block) this, duel.getOriginBlock() );
//
//                    if ( this instanceof Fallable) {
//                        AssignUtil.putInverseSlabReplacement( duelSlab.getDuel(), (Block) this );
//                    }
//                }
//                break;
//        }
    }

    final class AssignUtil {
        public static final VoxelShape FULL_BLOCK_ON_SLAB = Block.box( 0.0D, -8.0D, 0.0D, 16.0D, 8.0D, 16.0D );

        public static void putOnTopVegetation( Block key, Block value ) {
        }
        public static void putTerrainSlab( Block key, Block value ) {
        }
        public static void putBlockBelowReplacement( Block key, Block value ) {
        }
        public static void putTopSlabReplacement( Block key, Block value ) {
        }
        public static void putInverseSlabReplacement( Block key, Block value ) {
        }
        public static void putVegetationOnTopItem(Item item, Block block ) {
        }
    }
}
