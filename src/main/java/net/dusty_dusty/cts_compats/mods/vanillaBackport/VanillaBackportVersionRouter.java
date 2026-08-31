package net.dusty_dusty.cts_compats.mods.vanillaBackport;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.registry.AbstractVersionRouter;
import net.dusty_dusty.cts_compats.registry.IRegistry;
import net.dusty_dusty.cts_compats.registry.Version;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class VanillaBackportVersionRouter extends AbstractVersionRouter {
    private static final Map<Version.Range, Supplier<IRegistry>> VERSION_MAP = new HashMap<>();
    static {
        VERSION_MAP.put( Version.Range.acceptCustom( "*", "1.1.7", true, false ), () -> VanillaBackportBaseRegistry.getInstance() );
        VERSION_MAP.put( Version.Range.acceptLaterThanInclusive( "1.1.7" ), () -> VanillaBackportSulfurRegistry.getInstance() );
    }

    private static final VanillaBackportVersionRouter INSTANCE = new VanillaBackportVersionRouter(CTSCompats.VB_MODID, VERSION_MAP );
    public static VanillaBackportVersionRouter getInstance() {
        return INSTANCE;
    }

    private VanillaBackportVersionRouter(String modId, Map<? extends Comparable<Version>, Supplier<IRegistry>> versionFilter) {
        super(modId, versionFilter);
    }
}
