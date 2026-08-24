package net.dusty_dusty.cts_compats.mods;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.registry.AbstractRegistry;
import net.dusty_dusty.cts_compats.registry.IColorRegistry;

import java.util.Optional;

// TODO: Figure out chorus plants on top :sob:
public class QuarkRegistry extends AbstractRegistry {
    private static final QuarkRegistry INSTANCE = new QuarkRegistry( CTSCompats.QUARK_MODID );
    public static QuarkRegistry getInstance() {
        return INSTANCE;
    }

    protected QuarkRegistry(String modId) {
        super(modId);
    }

    @Override
    public Optional<IColorRegistry> getColorRegistry() {
        return Optional.empty();
    }
}
