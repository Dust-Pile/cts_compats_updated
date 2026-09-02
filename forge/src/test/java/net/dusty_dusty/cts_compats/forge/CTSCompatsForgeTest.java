package net.dusty_dusty.cts_compats.forge;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.Bootstrap;
import net.minecraftforge.eventbus.api.IEventBus;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CTSCompatsForgeTest {
    @Test
    void registersArchitecturyBusBeforeSharedInit() throws ReflectiveOperationException {
        Field bootstrapped = Bootstrap.class.getDeclaredField("isBootstrapped");
        bootstrapped.setAccessible(true);
        bootstrapped.setBoolean(null, true);
        BuiltInRegistries.REGISTRY.key();
        IEventBus modEventBus = (IEventBus) Proxy.newProxyInstance(
                IEventBus.class.getClassLoader(),
                new Class<?>[]{IEventBus.class},
                (proxy, method, args) -> null
        );
        assertDoesNotThrow(() -> CTSCompatsForge.initCommon(modEventBus));
    }
}
