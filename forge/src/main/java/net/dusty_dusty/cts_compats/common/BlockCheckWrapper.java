package net.dusty_dusty.cts_compats.common;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings({"UnusedReturnValue", "unused", "OptionalUsedAsFieldOrParameterType"})
public class BlockCheckWrapper {
    public static final Group SAND_AND_DIRT = new Group();
    public static final Group DIRT_AND_FARMLAND = new Group();
    public static final Group NETHER_FLOOR = new Group();
    public static Group WATER_PLANT_PLACEABLE = new Group();
    static {
        SAND_AND_DIRT.add( BlockTags.SAND );
        SAND_AND_DIRT.add( BlockTags.DIRT );
        SAND_AND_DIRT.add( Tags.Blocks.SAND );

        DIRT_AND_FARMLAND.add( BlockTags.DIRT );
        DIRT_AND_FARMLAND.add( Blocks.FARMLAND );

        NETHER_FLOOR.add( Blocks.NETHERRACK );
        NETHER_FLOOR.add( Blocks.SOUL_SAND );
        NETHER_FLOOR.add( Blocks.SOUL_SOIL );
        NETHER_FLOOR.add( Blocks.CRIMSON_NYLIUM );
        NETHER_FLOOR.add( Blocks.WARPED_NYLIUM );

        WATER_PLANT_PLACEABLE.addAll( SAND_AND_DIRT );
        WATER_PLANT_PLACEABLE.add( Tags.Blocks.GRAVEL );
        WATER_PLANT_PLACEABLE.add( Blocks.CLAY );
        WATER_PLANT_PLACEABLE.add( BlockTags.BIG_DRIPLEAF_PLACEABLE );
    }

    public static BlockCheckWrapper ALWAYS = new BlockCheckWrapper( state -> true );
    public static BlockCheckWrapper NEVER = new BlockCheckWrapper( state -> false );

    private final Optional<Block> blockOption;
    private final Optional<TagKey<Block>> blockTagOption;
    private final Optional<Function<BlockState, Boolean>> functionOption;

    public BlockCheckWrapper( Block block ) {
        this.blockOption = Optional.of( block );
        this.blockTagOption = Optional.empty();
        this.functionOption = Optional.empty();
    }
    public BlockCheckWrapper( RegistrySupplier<Block> block ) {
        this.blockOption = Optional.of( block.get() );
        this.blockTagOption = Optional.empty();
        this.functionOption = Optional.empty();
    }
    public BlockCheckWrapper( TagKey<Block> blockTag ) {
        this.blockTagOption = Optional.of( blockTag );
        this.blockOption = Optional.empty();
        this.functionOption = Optional.empty();
    }
    public BlockCheckWrapper( Function<BlockState, Boolean> func ) {
        this.blockTagOption = Optional.empty();
        this.blockOption = Optional.empty();
        this.functionOption = Optional.of( func );
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public boolean check( BlockState state ) {
        if ( functionOption.isPresent() ) {
            return functionOption.get().apply( state );
        }
        return blockOption.map(state::is).orElseGet(() -> state.is(blockTagOption.get()));
    }

    public Group asGroup() {
        return new Group( this );
    }

    public static boolean checkAll( BlockState state, List<BlockCheckWrapper> blockChecks ) {
        for ( BlockCheckWrapper blockCheck : blockChecks ) {
            if ( blockCheck.check( state ) ) {
                return true;
            }
        }
        return false;
    }

    public static class Group extends ArrayList<BlockCheckWrapper> {
        public Group() {
            super();
        }

        public Group( BlockCheckWrapper... wrappers ) {
            super( List.of(wrappers) );
        }

        public Group( Block block ) {
            this();
            this.add( block );
        }
        public Group( TagKey<Block> key ) {
            this();
            this.add( key );
        }

        Group( Object... objects ) {
            super();
            for ( Object object : objects ) {
                if ( object instanceof Block block ) {
                    this.add( new BlockCheckWrapper( block ) );
                    continue;
                } else if ( object instanceof TagKey<?> key && key.isFor( ForgeRegistries.BLOCKS.getRegistryKey() ) ) {
                    //noinspection unchecked
                    this.add( new BlockCheckWrapper( ( TagKey<Block> ) key ) );
                    continue;
                } else if ( object instanceof RegistrySupplier<?> regObj && regObj.get() instanceof Block block ) {
                    this.add( new BlockCheckWrapper( block ) );
                    continue;
                }
                throw new IllegalArgumentException( "Only blocks, block registry objects, and block tags can be used for this." );
            }
        }

        public Group add( Block block ) {
            this.add( new BlockCheckWrapper( block ) );
            return this;
        }
        public Group add( RegistrySupplier<Block> blockRegister ) {
            this.add( new BlockCheckWrapper( blockRegister ) );
            return this;
        }
        public Group add( TagKey<Block> key ) {
            this.add( new BlockCheckWrapper( key ) );
            return this;
        }
        public Group add( Function<BlockState, Boolean> func ) {
            this.add( new BlockCheckWrapper( func ) );
            return this;
        }

        public boolean check( BlockState blockState ) {
            return checkAll( blockState, this );
        }

        @Override
        public Group clone() {
            return (Group) super.clone();
        }

        public Group cloneAndAppend( Collection<? extends BlockCheckWrapper> appends ) {
            Group clone = this.clone();
            clone.addAll( appends );
            return clone;
        }
        public Group cloneAndAppend( BlockCheckWrapper wrapper ) {
            Group clone = this.clone();
            clone.add( wrapper );
            return clone;
        }
    }
}
