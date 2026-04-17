package net.dusty_dusty.cts_compats.registry;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.block.IAssignable;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.MODID;

public abstract class AbstractRegistry implements IRegistry {
    protected final DeferredRegister<Block> COMPAT_BLOCKS = DeferredRegister.create( ForgeRegistries.BLOCKS, MODID );
    protected final DeferredRegister<Item> COMPAT_ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MODID );
    private static final ArrayList<RegistryObject<Block>> cutoutRender = new ArrayList<>();
    protected final String REGISTRY_ID;

    protected AbstractRegistry ( String modId ) {
        REGISTRY_ID = modId;
    }

    public String getModID() {
        return this.REGISTRY_ID;
    }

    @SuppressWarnings("unchecked")
    public <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block ) {
        RegistryObject<T> output = this.COMPAT_BLOCKS.register( name, block );
        this.registerBlockItem( name, ( RegistryObject<Block> ) output);
        return output;
    }

    @SuppressWarnings("UnusedReturnValue")
    protected RegistryObject<Item> registerBlockItem(String name, RegistryObject<Block> block ) {
        return this.COMPAT_ITEMS.register( name, () -> new BlockItem( block.get(), new Item.Properties() ) );
    }

    public void register( IEventBus modEventBus ) {
        this.COMPAT_BLOCKS.register( modEventBus );
        this.COMPAT_ITEMS.register( modEventBus );
    }

    public void assign() {
        this.COMPAT_BLOCKS.getEntries().forEach( entry -> {
            Block block = entry.get();
            CTSCompats.LOGGER.info( block.getDescriptionId() );
            if ( block instanceof IAssignable) {
                CTSCompats.LOGGER.info( block.getDescriptionId() );
                ( (IAssignable) block ).assign();
            }
        });
        assignExtras();
    }

    @SuppressWarnings("removal")
    public static void setRenderTypes() {
        cutoutRender.forEach( blockRegistryObject ->
                ItemBlockRenderTypes.setRenderLayer( blockRegistryObject.get(), RenderType.cutout() ));
    }

    @SuppressWarnings("unchecked")
    public <T extends Block> RegistryObject<T> registerBlockCutout(String name, Supplier<T> block ) {
        RegistryObject<T> output = registerBlock( name, block );
        cutoutRender.add( (RegistryObject<Block>) output );
        return output;
    }

    protected void assignExtras() {}

    public Collection<RegistryObject<Block>> getRegistryBlocks() {
        return COMPAT_BLOCKS.getEntries();
    }

}
