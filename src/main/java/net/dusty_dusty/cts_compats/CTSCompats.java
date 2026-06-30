package net.dusty_dusty.cts_compats;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
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

    public CTSCompats(FMLJavaModLoadingContext context )
    {
        IEventBus modEventBus = context.getModEventBus();

        // modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
        //context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
