package net.dusty_dusty.data_gen;

import net.dusty_dusty.cts_compats.RegistryManager;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class ModUSLangProvider extends LanguageProvider {
    public ModUSLangProvider(PackOutput output, String modid ) {
        super(output, modid, "en_us" );
    }

    @Override
    protected void addTranslations() {
        RegistryManager.forEachRegistry( registry -> {
            for ( RegistryObject<Block> blockRegister : registry.getRegistryBlocks() ) {
                Block block = blockRegister.get();
                String[] words = Util.makeDescriptionId("block", BuiltInRegistries.BLOCK.getKey(block))
                        .split("\\.")[2].split( "_" );
                String output = "";
                for ( String word : words ) {
                    output += word.substring( 0, 1 ).toUpperCase() + word.substring( 1 ) + " ";
                }

                this.add( block, output.substring( 0, output.length() - 1 ) );
            }
        } );
    }
}
