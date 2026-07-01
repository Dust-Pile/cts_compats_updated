package net.dusty_dusty.data_gen;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CTSCompats.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
