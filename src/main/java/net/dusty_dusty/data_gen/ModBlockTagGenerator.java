package net.dusty_dusty.data_gen;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.mods.VanillaRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    final ExistingFileHelper existingFileHelper;

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CTSCompats.MODID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void addTags( HolderLookup.Provider pProvider ) {
        // IntrinsicTagAppender<Block> mineable_hoe = this.tag(BlockTags.MINEABLE_WITH_HOE );

        this.tag( BlockTags.MINEABLE_WITH_PICKAXE ).add(
//                BOPBaseRegistry.BRIMSTONE_SLAB.get(),
//                BOPBaseRegistry.BRIMSTONE_BUD_ON_TOP.get(),
//                BOPBaseRegistry.DRIED_SALT_SLAB.get(),
//                BOPBaseRegistry.BRIMSTONE_FUMAROLE_ON_TOP.get(),

                VanillaRegistry.DRIPSTONE_SLAB.get()
        );
//        this.tag(BlockTags.MINEABLE_WITH_AXE ).add(
//                BOPBaseRegistry.FLESH_SLAB.get(),
//                BOPBaseRegistry.POROUS_FLESH_SLAB.get(),
//                BOPBaseRegistry.EYEBULB_ON_TOP.get()
//        );
//        this.tag(BlockTags.MINEABLE_WITH_SHOVEL ).add(
//                BOPBaseRegistry.MOSSY_BLACK_SAND_SLAB.get(),
//                BOPBaseRegistry.BLACK_SAND_SLAB.get(),
//                BOPBaseRegistry.ORANGE_SAND_SLAB.get(),
//                BOPBaseRegistry.WHITE_SAND_SLAB.get()
//        );

        this.tag( Tags.Blocks.NEEDS_WOOD_TOOL ).add(
//                BOPBaseRegistry.BRIMSTONE_FUMAROLE_ON_TOP.get(),
//                BOPBaseRegistry.BRIMSTONE_SLAB.get(),

                VanillaRegistry.DRIPSTONE_SLAB.get()
        );

        // TODO: Tool requirement tags!!!
    }
}