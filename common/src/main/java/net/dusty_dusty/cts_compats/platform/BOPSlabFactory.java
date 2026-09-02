package net.dusty_dusty.cts_compats.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.Block;

public final class BOPSlabFactory {
    private BOPSlabFactory() {
    }

    @ExpectPlatform
    public static Block sand(Block originalBlock) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Block mossyBlackSand(Block originalBlock) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Block driedSalt(Block originalBlock) {
        throw new AssertionError();
    }
}
