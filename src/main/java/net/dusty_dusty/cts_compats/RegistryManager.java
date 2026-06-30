package net.dusty_dusty.cts_compats;

import net.dusty_dusty.cts_compats.mods.VanillaRegistry;
import net.dusty_dusty.cts_compats.registry.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.dusty_dusty.cts_compats.CTSCompats.LOGGER;

public final class RegistryManager {
    final IEventBus MOD_EVENT_BUS;
    final ArrayList<IRegistry> REGISTRIES = new ArrayList<>();

    static final Map<String, Version> LOADED_VERSIONS = new HashMap<>();
    static final Map<String, IRegistry> LOADED_MODS = new HashMap<>();
    private static RegistryManager INSTANCE;
    public static RegistryManager getInstance() {
        return INSTANCE;
    }

    public static IRegistry getRegistry( String modId ) {
        return LOADED_MODS.get( modId );
    }
    public static Version getVersion( String modId ) {
        return LOADED_VERSIONS.get( modId );
    }

    RegistryManager(IEventBus modEventBus ) throws IllegalAccessException {
        if ( INSTANCE != null ) {
            throw new IllegalAccessException( "RegistryManager can only be instantiated once" );
        }

        this.MOD_EVENT_BUS = modEventBus;
        modEventBus.addListener( this::commonSetup );
        modEventBus.addListener( this::clientSetup );

        register( "minecraft", VanillaRegistry.getInstance() );
        INSTANCE = this;
    }

    public static void forEachRegistry( Consumer<IRegistry> consumer ) {
        INSTANCE.REGISTRIES.forEach( consumer );
    }
    public static void forEachRegistryAndID( final BiConsumer<String, IRegistry> biConsumer ) {
        LOADED_MODS.forEach( biConsumer );
    }
    public static List<Block> getAllBlocks() {
        List<Block> blocks = new ArrayList<>();
        forEachRegistry( registry -> {
            registry.getRegistryBlocks().forEach( blockRegister -> {
                blocks.add( blockRegister.get() );
            });
        });
        return blocks;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean register(String modId, Supplier<IRegistry> registrySupplier ) {
        return runModCompat( modId, () -> register( modId, registrySupplier.get() ) );
    }
    private void register( String modId, IRegistry registry ) {
        REGISTRIES.add( registry );
        LOADED_MODS.put( modId, registry );
        registry.register( MOD_EVENT_BUS );
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        REGISTRIES.forEach( IRegistry::assign );
    }

    @SuppressWarnings("Convert2MethodRef") // Method Reference loads class. Unacceptable.
    private void clientSetup(final FMLClientSetupEvent event) {
        runModCompat( CTSCompats.BOP_MODID, () -> AbstractRegistry.setRenderTypes() );
        forEachRegistry( registry -> registry.clientSetup() );
    }

    @Mod.EventBusSubscriber( modid = CTSCompats.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        public static void onColorHandlerEventBlock(RegisterColorHandlersEvent.Block event) {
            INSTANCE.REGISTRIES.forEach( registry -> {
                Optional<IColorRegistry> colorRegistry = registry.getColorRegistry();
                colorRegistry.ifPresent(iColorRegistry -> iColorRegistry.onColorHandlerEventBlock(event));
            } );
        }

        @SubscribeEvent
        public static void onColorHandlerEventItem(RegisterColorHandlersEvent.Item event) {
            INSTANCE.REGISTRIES.forEach( registry -> {
                Optional<IColorRegistry> colorRegistry = registry.getColorRegistry();
                colorRegistry.ifPresent(iColorRegistry -> iColorRegistry.onColorHandlerEventItem( event ));
            } );
        }

        @SubscribeEvent
        public static void onModelBake( ModelEvent.ModifyBakingResult event ) {
            INSTANCE.REGISTRIES.forEach( registry -> {
                Optional<IColorRegistry> colorRegistry = registry.getColorRegistry();
                colorRegistry.ifPresent(iEmissiveRegistry -> iEmissiveRegistry.onModelBake( event ));
            } );
        }

        @SubscribeEvent
        public static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event) {
            INSTANCE.REGISTRIES.forEach( registry -> {
                Optional<IColorRegistry> colorRegistry = registry.getColorRegistry();
                colorRegistry.ifPresent(iEmissiveRegistry -> iEmissiveRegistry.onRegisterAdditionalModels( event ));
            } );
        }
    }

    private static boolean runModCompat( String modid, Runnable register ) {
        if ( ModList.get().isLoaded( modid ) ) {
            LOADED_VERSIONS.put( modid, new Version( ModList.get().getModFileById( modid ).versionString() ) );
            LOGGER.info( "Loading Runnable for {} version {}.", modid, LOADED_VERSIONS.get( modid ) );
            register.run();
            return true;
        }
        return false;
    }

}
