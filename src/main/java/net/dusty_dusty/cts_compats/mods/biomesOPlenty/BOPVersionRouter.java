package net.dusty_dusty.cts_compats.mods.biomesOPlenty;

import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBaseRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBetaRegistry;
import net.dusty_dusty.cts_compats.registry.AbstractVersionRouter;
import net.dusty_dusty.cts_compats.registry.IRegistry;
import net.dusty_dusty.cts_compats.registry.Version;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.BOP_MODID;

@SuppressWarnings("Convert2MethodRef")
public final class BOPVersionRouter extends AbstractVersionRouter {
    private static final Map<Version.Range, Supplier<IRegistry>> VERSION_MAP = new HashMap<>();
    static {
        VERSION_MAP.put( Version.Range.acceptCustom( "18.0.0.592", "19.0.0.96", true, false ), () -> BOPBaseRegistry.getInstance() );
        VERSION_MAP.put( Version.Range.acceptLaterThanInclusive( "19.0.0.96" ), () -> BOPBetaRegistry.getInstance() );
    }

    private static final BOPVersionRouter INSTANCE = new BOPVersionRouter( BOP_MODID, VERSION_MAP );
    public static BOPVersionRouter getInstance() {
        return INSTANCE;
    }

    private BOPVersionRouter( String modid, Map<Version.Range, Supplier<IRegistry>> versionFilter ) {
        super( modid, versionFilter );
    }
}
