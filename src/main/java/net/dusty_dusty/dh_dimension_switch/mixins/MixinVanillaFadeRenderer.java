package net.dusty_dusty.dh_dimension_switch.mixins;

import com.seibel.distanthorizons.core.render.renderer.VanillaFadeRenderer;
import com.seibel.distanthorizons.core.render.renderer.shaders.FadeApplyShader;
import com.seibel.distanthorizons.core.render.renderer.shaders.VanillaFadeShader;
import net.dusty_dusty.dh_dimension_switch.LodRenderChecker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin( VanillaFadeRenderer.class )
public class MixinVanillaFadeRenderer {

    @Redirect( method = "render", at = @At(
            value = "INVOKE",
            target = "Lcom/seibel/distanthorizons/core/render/renderer/shaders/VanillaFadeShader;render(F)V"
        ), remap = false
    )
    void renderProxy(
            VanillaFadeShader instance,
            float v
    ){
        if ( LodRenderChecker.shouldRender() ) instance.render( v );
    }

    @Redirect( method = "render", at = @At(
            value = "INVOKE",
            target = "Lcom/seibel/distanthorizons/core/render/renderer/shaders/FadeApplyShader;render(F)V"
        ), remap = false
    )
    void applyRenderProxy(
            FadeApplyShader instance,
            float v
    ){
        if ( LodRenderChecker.shouldRender() ) instance.render( v );
    }

}
