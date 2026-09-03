package net.dusty_dusty.cts_compats.mixins;

import net.countered.terrainslabs.api.IFacedOffsetable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(remap = false, targets = {
        "net.satisfy.meadow.core.block.StorageBlock",
        "net.satisfy.meadow.core.block.CameraBlock",
        "net.satisfy.meadow.core.block.DoormatBlock",
        "net.satisfy.meadow.core.block.FireLog",
        "net.satisfy.meadow.core.block.CanBlock"
})
public class MeadowAssigner implements IFacedOffsetable {
}
