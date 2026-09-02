package net.dusty_dusty.data_gen;

import com.google.gson.JsonObject;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.io.IOException;

import static net.dusty_dusty.cts_compats.CTSCompats.LOGGER;

public class ModBlockStateProvider extends BlockStateProvider {
    final ExistingFileHelper existingFileHelper;

    protected ModBlockStateProvider( PackOutput output, ExistingFileHelper existingFileHelper ) {
        super( output, CTSCompats.MODID, existingFileHelper );
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        RegistryManager.forEachRegistry( registry -> {
            for ( RegistryObject<Block> entry : registry.getRegistryBlocks() ) {
                Block block = entry.get();
                if ( block instanceof ISlabCopy slabCopy ) {
                    slabCopyFromCube( slabCopy );
                }
            }
        } );
    }


    @SuppressWarnings("deprecation")
    private void slabCopyFromCube(ISlabCopy blockCopy) {
        ResourceLocation origLoc = BuiltInRegistries.BLOCK.getKey( blockCopy.getOriginBlock() );
        ResourceLocation loc = BuiltInRegistries.BLOCK.getKey( (Block) blockCopy );
        JsonObject jsonObject = getBlockJson( blockCopy, "models/block" );
        if ( jsonObject == null ) {
            return;
        }
        if ( jsonObject.get( "parent" ).getAsString().contains("cube_all") ) {
            slabCopyFromCubeAll( blockCopy, jsonObject, loc, origLoc );
            return;
        }

        JsonObject textures = jsonObject.getAsJsonObject( "textures" );

        try {
            this.slabBlock( (SlabBlock) blockCopy, origLoc,
                    ResourceLocation.parse( textures.get( "side" ).getAsString() ),
                    ResourceLocation.parse( textures.get( "bottom" ).getAsString() ),
                    ResourceLocation.parse( textures.get( "top" ).getAsString() ) );
        } catch (Exception e) {
            LOGGER.error( e.toString() );
            return;
        }

        simpleBlockItem( (Block) blockCopy, new BlockModelBuilder( loc.withPrefix("block/"), existingFileHelper ) );
    }

    private void slabCopyFromCubeAll( ISlabCopy blockCopy, JsonObject jsonObject, ResourceLocation loc, ResourceLocation origLoc ) {
        this.slabBlock( (SlabBlock) blockCopy, origLoc,
                ResourceLocation.parse( jsonObject.getAsJsonObject( "textures" ).get( "all" ).getAsString() ) );

        simpleBlockItem( (Block) blockCopy, new BlockModelBuilder( loc.withPrefix("block/"), existingFileHelper ) );
    }

    @SuppressWarnings("deprecation")
    private JsonObject getBlockJson(ISlabCopy blockCopy, String prefix ) {
        ResourceLocation loc = BuiltInRegistries.BLOCK.getKey( blockCopy.getOriginBlock() );

        try {
            Resource modelJson = existingFileHelper.getResource( loc, PackType.CLIENT_RESOURCES, ".json", prefix);
            return GsonHelper.parse( modelJson.openAsReader() );
        } catch ( IOException e ) {
            LOGGER.warn( e.toString() );
            return null;
        }
    }
}
