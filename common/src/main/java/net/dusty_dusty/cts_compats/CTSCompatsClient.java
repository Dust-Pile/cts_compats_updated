package net.dusty_dusty.cts_compats;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.dusty_dusty.cts_compats.registry.IColorRegistry;
import net.dusty_dusty.cts_compats.resources.SlabClientResources;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public final class CTSCompatsClient {
    private static final ResourceKey<CreativeModeTab> TERRAIN_SLABS_TAB = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            new ResourceLocation("terrain_slabs", "terrain_slabs")
    );

    private CTSCompatsClient() {
    }

    public static void init() {
        SlabClientResources.init();
        RegistryManager.forEachRegistry(registry -> {
            registerRenderTypes(registry.getCutoutBlocks(), registry.getCutoutMippedBlocks());
            registry.clientSetup();
            registry.getColorRegistry().ifPresent(colors -> registerColors(colors.get()));
        });

        Block[] slabs = RegistryManager.getAllBlocks().stream()
                .filter(SlabBlock.class::isInstance)
                .toArray(Block[]::new);
        CreativeTabRegistry.append(TERRAIN_SLABS_TAB, slabs);
    }

    private static void registerRenderTypes(Iterable<? extends Block> cutout, Iterable<? extends Block> cutoutMipped) {
        RenderTypeRegistry.register(RenderType.cutout(), toArray(cutout));
        RenderTypeRegistry.register(RenderType.cutoutMipped(), toArray(cutoutMipped));
    }

    private static Block[] toArray(Iterable<? extends Block> blocks) {
        ArrayList<Block> output = new ArrayList<>();
        blocks.forEach(output::add);
        return output.toArray(Block[]::new);
    }

    private static void registerColors(IColorRegistry colorRegistry) {
        colorRegistry.registerBlockColors();
        colorRegistry.registerItemColors();
    }
}
