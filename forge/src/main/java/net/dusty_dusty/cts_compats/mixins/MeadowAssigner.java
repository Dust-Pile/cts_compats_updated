package net.dusty_dusty.cts_compats.mixins;

import dev.polymixin.api.DynamicTargets;
import net.countered.terrainslabs.api.IFacedOffsetable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin( targets = {
        "net.satisfy.meadow.core.block.StorageBlock",
        "net.satisfy.meadow.core.block.CameraBlock",
        "net.satisfy.meadow.core.block.DoormatBlock",
        "net.satisfy.meadow.core.block.FireLog",
        "net.satisfy.meadow.core.block.CanBlock"
})
@DynamicTargets
public class MeadowAssigner implements IFacedOffsetable {
}
