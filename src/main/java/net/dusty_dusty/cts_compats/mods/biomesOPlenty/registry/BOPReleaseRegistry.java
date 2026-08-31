package net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry;

import net.countered.terrainslabs.api.OffsetClasses;

import java.util.Set;

import static net.dusty_dusty.cts_compats.registry.AbstractRegistry.registerOffsetClasses;

public final class BOPReleaseRegistry {
    public static BOPBaseRegistry INSTANCE = BOPBaseRegistry.getInstance();
    public static BOPBaseRegistry getInstance() {
        return INSTANCE;
    }

    static {
        try {
            registerOffsetClasses( OffsetClasses.Category.ONTOP_VEGETATION, Set.of(
                    Class.forName("biomesoplenty.common.block.BlackstoneDecorationBlock"),
                    Class.forName("biomesoplenty.common.block.BrimstoneBudBlock"),
                    Class.forName("biomesoplenty.common.block.BrimstoneFumaroleBlock"),
                    Class.forName("biomesoplenty.common.block.HairBlock"),
                    Class.forName("biomesoplenty.common.block.PusBubbleBlock"),
                    Class.forName("biomesoplenty.common.block.SpiderEggBlock"),
                    Class.forName("biomesoplenty.common.block.BrambleLeavesBlock")
            ) );

            registerOffsetClasses( OffsetClasses.Category.ONBOTTOM_VEGETATION, Set.of(
                    Class.forName("biomesoplenty.common.block.BrambleLeavesBlock")
            ) );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
