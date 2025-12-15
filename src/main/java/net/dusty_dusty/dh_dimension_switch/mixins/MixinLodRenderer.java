package net.dusty_dusty.dh_dimension_switch.mixins;

import com.seibel.distanthorizons.api.interfaces.override.rendering.IDhApiShaderProgram;
import com.seibel.distanthorizons.core.render.RenderBufferHandler;
import com.seibel.distanthorizons.core.render.renderer.*;
import com.seibel.distanthorizons.core.util.math.Mat4f;
import com.seibel.distanthorizons.core.wrapperInterfaces.minecraft.IProfilerWrapper;
import net.dusty_dusty.dh_dimension_switch.ILodRenderer;
import net.dusty_dusty.dh_dimension_switch.LodRenderChecker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( LodRenderer.class )
public abstract class MixinLodRenderer implements ILodRenderer {

    @Shadow( remap = false )
    protected abstract void renderLodPass(IDhApiShaderProgram shaderProgram, RenderBufferHandler lodBufferHandler, RenderParams renderEventParam, boolean opaquePass);

    @Unique
    public void dhds$renderLodPass(IDhApiShaderProgram shaderProgram, RenderBufferHandler lodBufferHandler, RenderParams renderEventParam, boolean opaquePass) {
        this.renderLodPass( shaderProgram, lodBufferHandler, renderEventParam, opaquePass );
    }

    /**
     * Update the render switch at method head once per frame
     *
     * @param renderParams unused
     * @param profiler unused
     * @param runningDeferredPass do not update on deferred pass
     * @param ci unused
     */
    @Inject( method = "renderLodPass(Lcom/seibel/distanthorizons/core/render/renderer/RenderParams;Lcom/seibel/distanthorizons/core/wrapperInterfaces/minecraft/IProfilerWrapper;Z)V",
            at = @At( "HEAD" ), remap = false )
    private void onRenderLodPass(
            RenderParams renderParams,
            IProfilerWrapper profiler,
            boolean runningDeferredPass,
            CallbackInfo ci
    ){
        if ( !runningDeferredPass ) {
            LodRenderChecker.updateRender();
        }
    }

    ///=======================///
    /// Rendering Suppression ///
    ///=======================///

    @Redirect( method = "renderLodPass(Lcom/seibel/distanthorizons/core/render/renderer/RenderParams;Lcom/seibel/distanthorizons/core/wrapperInterfaces/minecraft/IProfilerWrapper;Z)V",
            at = @At(
                value = "INVOKE",
                target = "Lcom/seibel/distanthorizons/core/render/renderer/LodRenderer;renderLodPass(Lcom/seibel/distanthorizons/api/interfaces/override/rendering/IDhApiShaderProgram;Lcom/seibel/distanthorizons/core/render/RenderBufferHandler;Lcom/seibel/distanthorizons/core/render/renderer/RenderParams;Z)V"
        ), remap = false
    )
    private void renderLodPassProxy(
            LodRenderer instance,
            IDhApiShaderProgram vboIndex,
            RenderBufferHandler bufferContainer,
            RenderParams vbos, boolean lodIndex
    ){
        if ( LodRenderChecker.shouldRender() ) {
            // Force access to a private method via shenanigans
            ((ILodRenderer) instance).dhds$renderLodPass( vboIndex, bufferContainer, vbos, lodIndex );
        }
    }

    // Generic Objects should be rendered (Clouds, beacons)
//    @Redirect( method = "renderLodPass(Lcom/seibel/distanthorizons/core/render/renderer/RenderParams;Lcom/seibel/distanthorizons/core/wrapperInterfaces/minecraft/IProfilerWrapper;Z)V",
//            at = @At(
//                value = "INVOKE",
//                target = "Lcom/seibel/distanthorizons/core/render/renderer/generic/GenericObjectRenderer;render(Lcom/seibel/distanthorizons/api/methods/events/sharedParameterObjects/DhApiRenderParam;Lcom/seibel/distanthorizons/core/wrapperInterfaces/minecraft/IProfilerWrapper;Z)V"
//        ), remap = false
//    )
//    public void genericRenderProxy(
//            GenericObjectRenderer instance,
//            DhApiRenderParam boxGroup,
//            IProfilerWrapper iProfilerWrapper,
//            boolean renderEventParam
//    ){
//        if ( LodRenderChecker.shouldRender() ) instance.render( boxGroup, iProfilerWrapper, renderEventParam );
//    }

    @Redirect( method = "renderLodPass(Lcom/seibel/distanthorizons/core/render/renderer/RenderParams;Lcom/seibel/distanthorizons/core/wrapperInterfaces/minecraft/IProfilerWrapper;Z)V",
            at = @At(
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

    @Redirect( method = "renderLodPass(Lcom/seibel/distanthorizons/core/render/renderer/RenderParams;Lcom/seibel/distanthorizons/core/wrapperInterfaces/minecraft/IProfilerWrapper;Z)V",
            at = @At(
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

    @Redirect( method = "renderLodPass(Lcom/seibel/distanthorizons/core/render/renderer/RenderParams;Lcom/seibel/distanthorizons/core/wrapperInterfaces/minecraft/IProfilerWrapper;Z)V",
            at = @At(
            value = "INVOKE",
            target = "Lcom/seibel/distanthorizons/core/render/renderer/DhFadeRenderer;render(Lcom/seibel/distanthorizons/core/util/math/Mat4f;Lcom/seibel/distanthorizons/core/util/math/Mat4f;FLcom/seibel/distanthorizons/core/wrapperInterfaces/minecraft/IProfilerWrapper;)V"
        ), remap = false
    )
    public void fadeRenderProxy(
            DhFadeRenderer instance,
            Mat4f height,
            Mat4f e,
            float v,
            IProfilerWrapper mcModelViewMatrix
    ){
        if ( LodRenderChecker.shouldRender() ) instance.render( height, e, v, mcModelViewMatrix );
    }
}
