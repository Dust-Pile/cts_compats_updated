package net.dusty_dusty.dh_dimension_switch;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Dh_dimension_switch.MODID)
public class Dh_dimension_switch
{
    public static final String MODID = "dh_dimension_switch";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Dh_dimension_switch( FMLJavaModLoadingContext context )
    {
        IEventBus modEventBus = context.getModEventBus();

        // modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
        //context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
