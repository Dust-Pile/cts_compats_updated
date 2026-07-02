package net.dusty_dusty.cts_compats;

import com.mojang.logging.LogUtils;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.BOPVersionRouter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CTSCompats.MODID)
public class CTSCompats
{
    public static final String MODID = "cts_compats";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String PVJ_MODID = "projectvibrantjourneys";
    public static final String BOP_MODID = "biomesoplenty";
    public static final String VB_MODID = "vanillabackport";
    public static final String MEADOW_MODID = "meadow";
    public static final String IW_MODID = "immersive_weathering";
    public static final String QUARK_MODID = "quark";

    public final RegistryManager REGISTRY_MANAGER;

    @SuppressWarnings("Convert2MethodRef") // Causes class load. Not acceptable
    public CTSCompats(FMLJavaModLoadingContext context )
    {
        IEventBus modEventBus = context.getModEventBus();

        try {
            REGISTRY_MANAGER = new RegistryManager( modEventBus );
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // modEventBus.addListener(this::clientSetup);
        modEventBus.addListener( this::registerCreativeTabItems );

        MinecraftForge.EVENT_BUS.register(this);

//        REGISTRY_MANAGER.register( PVJ_MODID, () -> PVJRegistry.getInstance() );
        REGISTRY_MANAGER.register( BOP_MODID, () -> BOPVersionRouter.getInstance() );
//        REGISTRY_MANAGER.register( VB_MODID, () -> VanillaBackportRegistry.getInstance() );
//        REGISTRY_MANAGER.register( MEADOW_MODID, () -> MeadowRegistry.getInstance() );
//        REGISTRY_MANAGER.register( QUARK_MODID, () -> QuarkRegistry.getInstance() );
    }

    private void registerCreativeTabItems( BuildCreativeModeTabContentsEvent event ) {
        if ( event.getTabKey().location().toString().equals( "terrain_slabs:terrain_slabs" ) ) {
            for ( Block block : RegistryManager.getAllBlocks() ) {
                if ( block instanceof SlabBlock) {
                    event.accept( block );
                }
            }
        }
    }
}
