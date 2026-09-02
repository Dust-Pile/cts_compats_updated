package net.dusty_dusty.cts_compats.forge;

import dev.architectury.platform.forge.EventBuses;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.CTSCompatsClient;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.dusty_dusty.cts_compats.mods.PVJRegistry;
import net.dusty_dusty.cts_compats.mods.QuarkRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CTSCompats.MODID)
public final class CTSCompatsForge {
    public CTSCompatsForge(FMLJavaModLoadingContext context) {
        EventBuses.registerModEventBus(CTSCompats.MODID, context.getModEventBus());
        CTSCompats.init();

        RegistryManager registries = CTSCompats.REGISTRY_MANAGER;
        registries.register(CTSCompats.PVJ_MODID, () -> PVJRegistry.getInstance());
        registries.register(CTSCompats.QUARK_MODID, () -> QuarkRegistry.getInstance());
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
