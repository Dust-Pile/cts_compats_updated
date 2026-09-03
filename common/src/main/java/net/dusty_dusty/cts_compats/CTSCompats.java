package net.dusty_dusty.cts_compats;

import com.mojang.logging.LogUtils;
import dev.architectury.event.events.common.LifecycleEvent;
import net.dusty_dusty.cts_compats.mods.MeadowRegistry;
import net.dusty_dusty.cts_compats.mods.VanillaRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.BOPVersionRouter;
import net.dusty_dusty.cts_compats.mods.vanillaBackport.VanillaBackportVersionRouter;
import net.dusty_dusty.cts_compats.resources.SlabServerData;
import org.slf4j.Logger;

public final class CTSCompats {
    public static final String MODID = "cts_compats";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String PVJ_MODID = "projectvibrantjourneys";
    public static final String BOP_MODID = "biomesoplenty";
    public static final String VB_MODID = "vanillabackport";
    public static final String MEADOW_MODID = "meadow";
    public static final String IW_MODID = "immersive_weathering";
    public static final String QUARK_MODID = "quark";

    public static final RegistryManager REGISTRY_MANAGER = RegistryManager.getInstance();

    private CTSCompats() {
    }

    public static void init() {
        REGISTRY_MANAGER.register("minecraft", VanillaRegistry.getInstance());
        REGISTRY_MANAGER.register(BOP_MODID, BOPVersionRouter::getInstance);
        REGISTRY_MANAGER.register(VB_MODID, VanillaBackportVersionRouter::getInstance);
        REGISTRY_MANAGER.register(MEADOW_MODID, MeadowRegistry::getInstance);
        SlabServerData.init();
        LifecycleEvent.SETUP.register(REGISTRY_MANAGER::assign);
    }
}
