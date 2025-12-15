package net.dusty_dusty.dh_dimension_switch.mixins;

import com.seibel.distanthorizons.api.methods.events.sharedParameterObjects.DhApiRenderParam;
import com.seibel.distanthorizons.core.render.RenderBufferHandler;
import com.seibel.distanthorizons.core.render.renderer.FogRenderer;
import com.seibel.distanthorizons.core.render.renderer.LodRenderer;
import com.seibel.distanthorizons.core.render.renderer.SSAORenderer;
import com.seibel.distanthorizons.core.render.renderer.generic.GenericObjectRenderer;
import com.seibel.distanthorizons.core.util.math.Mat4f;
import com.seibel.distanthorizons.core.wrapperInterfaces.minecraft.IProfilerWrapper;
import net.dusty_dusty.dh_dimension_switch.LodRenderChecker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LodRenderer.class )
public class MixinLodRenderer {

    @Redirect( method = "renderLodPass", at = @At(
            value = "INVOKE",
            target = "Lcom/seibel/distanthorizons/core/render/RenderBufferHandler;renderOpaque(Lcom/seibel/distanthorizons/core/render/renderer/LodRenderer;Lcom/seibel/distanthorizons/api/methods/events/sharedParameterObjects/DhApiRenderParam;)V"
        ), remap = false
    )
    public void renderOpaqueProxy(
            RenderBufferHandler instance,
            LodRenderer renderContext,
            DhApiRenderParam renderEventParam
    ){
        if ( LodRenderChecker.updateRender() ) instance.renderOpaque( renderContext, renderEventParam );
    }

    @Redirect( method = "renderLodPass", at = @At(
            value = "INVOKE",
            target = "Lcom/seibel/distanthorizons/core/render/renderer/generic/GenericObjectRenderer;render(Lcom/seibel/distanthorizons/api/methods/events/sharedParameterObjects/DhApiRenderParam;Lcom/seibel/distanthorizons/core/wrapperInterfaces/minecraft/IProfilerWrapper;Z)V"
        ), remap = false
    )
    public void genericRenderProxy(
            GenericObjectRenderer instance,
            DhApiRenderParam boxGroup,
            IProfilerWrapper iProfilerWrapper,
            boolean renderEventParam
    ){
        if ( LodRenderChecker.shouldRender() ) instance.render( boxGroup, iProfilerWrapper, renderEventParam );
    }

    @Redirect( method = "renderLodPass", at = @At(
            value = "INVOKE",
            target = "Lcom/seibel/distanthorizons/core/render/renderer/SSAORenderer;render(Lcom/seibel/distanthorizons/core/util/math/Mat4f;F)V"
        ), remap = false
    )
    public void SSAORenderProxy(
            SSAORenderer instance,
            Mat4f projectionMatrix,
            float partialTicks
    ){
        if ( LodRenderChecker.shouldRender() ) instance.render( projectionMatrix, partialTicks );
    }

    @Redirect( method = "renderLodPass", at = @At(
            value = "INVOKE",
            target = "Lcom/seibel/distanthorizons/core/render/renderer/FogRenderer;render(Lcom/seibel/distanthorizons/core/util/math/Mat4f;F)V"
        ), remap = false
    )
    public void fogRenderProxy(
            FogRenderer instance,
            Mat4f modelViewProjectionMatrix,
            float partialTicks
    ){
        if ( LodRenderChecker.shouldRender() ) instance.render( modelViewProjectionMatrix, partialTicks );
    }

    @Redirect( method = "renderTransparentBuffersAndFireApiEvent", at = @At(
            value = "INVOKE",
            target = "Lcom/seibel/distanthorizons/core/render/RenderBufferHandler;renderTransparent(Lcom/seibel/distanthorizons/core/render/renderer/LodRenderer;Lcom/seibel/distanthorizons/api/methods/events/sharedParameterObjects/DhApiRenderParam;)V"
        ), remap = false
    )
    public void renderTransparentProxy(
            RenderBufferHandler instance,
            LodRenderer renderContext,
            DhApiRenderParam renderEventParam
    ){
        if ( LodRenderChecker.shouldRender() ) instance.renderTransparent( renderContext, renderEventParam );
    }
}
