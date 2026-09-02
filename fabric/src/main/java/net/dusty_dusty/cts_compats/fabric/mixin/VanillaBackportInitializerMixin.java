package net.dusty_dusty.cts_compats.fabric.mixin;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.fabric.CTSCompatsFabric;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.blackgear.vanillabackport.core.fabric.VanillaBackportFabric", remap = false)
public abstract class VanillaBackportInitializerMixin {
    @Inject(method = "onInitialize", at = @At("RETURN"), remap = false)
    private void ctsCompats$initialize(CallbackInfo callbackInfo) {
        CTSCompatsFabric.initializeAfter(CTSCompats.VB_MODID);
    }
}
