package net.dusty_dusty.cts_compats.forge;

import dev.architectury.platform.forge.EventBuses;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.CTSCompatsClient;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.dusty_dusty.cts_compats.mods.MeadowRegistry;
import net.dusty_dusty.cts_compats.mods.PVJRegistry;
import net.dusty_dusty.cts_compats.mods.QuarkRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.BOPVersionRouter;
import net.dusty_dusty.cts_compats.mods.vanillaBackport.VanillaBackportVersionRouter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CTSCompats.MODID)
public final class CTSCompatsForge {
    public CTSCompatsForge(FMLJavaModLoadingContext context) {
        initCommon(context.getModEventBus());

        RegistryManager registries = CTSCompats.REGISTRY_MANAGER;
        registries.register(CTSCompats.PVJ_MODID, () -> PVJRegistry.getInstance());
        registries.register(CTSCompats.BOP_MODID, () -> BOPVersionRouter.getInstance());
        registries.register(CTSCompats.VB_MODID, () -> VanillaBackportVersionRouter.getInstance());
        registries.register(CTSCompats.MEADOW_MODID, () -> MeadowRegistry.getInstance());
        registries.register(CTSCompats.QUARK_MODID, () -> QuarkRegistry.getInstance());
    }

    static void initCommon(IEventBus modEventBus) {
        EventBuses.registerModEventBus(CTSCompats.MODID, modEventBus);
        CTSCompats.init();
    }

    @Mod.EventBusSubscriber(modid = CTSCompats.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static final class ClientEvents {
        private ClientEvents() {
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            CTSCompatsClient.init();
        }
    }
}
