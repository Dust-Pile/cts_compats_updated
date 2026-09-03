package net.dusty_dusty.cts_compats.mixins.biomesOPlenty;

import net.countered.terrainslabs.api.helperInterface.IAttachedFaceOffset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin( remap = false, targets = {
        "biomesoplenty.common.block.BrambleLeavesBlock",
        "biomesoplenty.block.BrambleLeavesBlock"
})
public class BOPAttachedFaceAssigner implements IAttachedFaceOffset {}
