package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import net.dusty_dusty.cts_compats.registry.AbstractOptionRegistry;
import net.dusty_dusty.cts_compats.resources.ResourceOptions;

public final class BOPOptionRegistry extends AbstractOptionRegistry {
    @Override
    public void register(OptionProvider provider) {
        provider.addOptions(
                BOPBaseRegistry.MOSSY_BLACK_SAND_SLAB.getId(),
                ResourceOptions.ColorFlags.GREENER_GRASS
        );
    }
}
