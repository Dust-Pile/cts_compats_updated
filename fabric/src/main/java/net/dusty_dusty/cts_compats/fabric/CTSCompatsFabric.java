package net.dusty_dusty.cts_compats.fabric;

import dev.architectury.platform.Platform;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.fabricmc.api.ModInitializer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public final class CTSCompatsFabric implements ModInitializer {
    private static final Set<String> OPTIONAL_MODS = Set.of(
            CTSCompats.BOP_MODID,
            CTSCompats.MEADOW_MODID,
            CTSCompats.VB_MODID
    );
    private static final Set<String> initializedOptionalMods = new HashSet<>();
    private static boolean initialized;

    @Override
    public void onInitialize() {
        if (!Platform.isModLoaded(CTSCompats.BOP_MODID)
                && !Platform.isModLoaded(CTSCompats.MEADOW_MODID)
                && !Platform.isModLoaded(CTSCompats.VB_MODID)) {
            initialized = true;
            CTSCompats.init();
        }
    }

    public static void initializeAfter(String modId) {
        initializedOptionalMods.add(modId);
        if (allLoadedOptionalModsInitialized(initializedOptionalMods, Platform::isModLoaded)) {
            initialize();
        }
    }

    static boolean allLoadedOptionalModsInitialized(Set<String> initializedMods, Predicate<String> isModLoaded) {
        return OPTIONAL_MODS.stream()
                .filter(isModLoaded)
                .allMatch(initializedMods::contains);
    }

    private static void initialize() {
        if (!initialized) {
            initialized = true;
            CTSCompats.init();
        }
    }
}
