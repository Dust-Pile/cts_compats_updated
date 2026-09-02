package net.dusty_dusty.cts_compats.forge;

import net.dusty_dusty.cts_compats.mods.VanillaRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;
import net.minecraftforge.eventbus.api.BusBuilder;
import net.minecraftforge.eventbus.api.EventListenerHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.IModBusEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CTSCompatsForgeTest {
    @Test
    void registersArchitecturyBusBeforeSharedInit() throws ReflectiveOperationException {
        prepareRegistries();
        prepareRegisterEvent();
        prepareModList();

        IEventBus modEventBus = BusBuilder.builder()
                .setTrackPhases(false)
                .markerType(IModBusEvent.class)
                .build();

        assertFalse(VanillaRegistry.DRIPSTONE_SLAB.isPresent());
        new CTSCompatsForge(loadingContext(modEventBus));
        modEventBus.post(registerEvent());
        assertTrue(VanillaRegistry.DRIPSTONE_SLAB.isPresent());
    }

    private static void prepareRegistries() throws ReflectiveOperationException {
        Field bootstrapped = Bootstrap.class.getDeclaredField("isBootstrapped");
        bootstrapped.setAccessible(true);
        bootstrapped.setBoolean(null, true);
        BuiltInRegistries.REGISTRY.key();
    }

    private static void prepareRegisterEvent() throws ReflectiveOperationException {
        Method listenerList = EventListenerHelper.class.getDeclaredMethod(
                "getListenerListInternal", Class.class, boolean.class
        );
        listenerList.setAccessible(true);
        listenerList.invoke(null, RegisterEvent.class, true);
    }

    private static void prepareModList() throws ReflectiveOperationException {
        ModList modList = ModList.of(List.of(), List.of());
        Method setLoadedMods = ModList.class.getDeclaredMethod("setLoadedMods", List.class);
        setLoadedMods.setAccessible(true);
        setLoadedMods.invoke(modList, List.of());
    }

    private static FMLJavaModLoadingContext loadingContext(IEventBus modEventBus) throws ReflectiveOperationException {
        Field unsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeInstance.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeInstance.get(null);
        FMLModContainer container = (FMLModContainer) unsafe.allocateInstance(FMLModContainer.class);

        Field eventBus = FMLModContainer.class.getDeclaredField("eventBus");
        eventBus.setAccessible(true);
        eventBus.set(container, modEventBus);

        Constructor<FMLJavaModLoadingContext> constructor = FMLJavaModLoadingContext.class
                .getDeclaredConstructor(FMLModContainer.class);
        constructor.setAccessible(true);
        return constructor.newInstance(container);
    }

    private static RegisterEvent registerEvent() throws ReflectiveOperationException {
        Constructor<RegisterEvent> constructor = RegisterEvent.class.getDeclaredConstructor(
                ResourceKey.class, ForgeRegistry.class, Registry.class
        );
        constructor.setAccessible(true);
        return constructor.newInstance(Registries.BLOCK, null, BuiltInRegistries.BLOCK);
    }
}
