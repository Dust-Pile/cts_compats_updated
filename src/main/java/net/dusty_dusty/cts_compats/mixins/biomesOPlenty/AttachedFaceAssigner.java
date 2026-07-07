package net.dusty_dusty.cts_compats.mixins.biomesOPlenty;

import net.countered.terrainslabs.api.IAttachedFaceOffset;
import org.spongepowered.asm.mixin.Mixin;

@Mixin( remap = false, targets = {
        "BrambleLeavesBlock"
})
public class AttachedFaceAssigner implements IAttachedFaceOffset {
}
