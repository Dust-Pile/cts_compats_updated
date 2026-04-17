package net.dusty_dusty.cts_compats.registry;

import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Supplier;

public abstract class AbstractVersionRouter implements IRegistry {
    private final Map<? extends Comparable<Version>, Supplier<IRegistry>> VERSION_FILTER;
    final IRegistry REGISTRY;
    protected final String REGISTRY_ID;

    @Override
    public String getModID() {
        return REGISTRY_ID;
    }

    protected AbstractVersionRouter( String modId, Map<? extends Comparable<Version>, Supplier<IRegistry>> versionFilter ) {
        VERSION_FILTER = versionFilter;
        REGISTRY = getRegistryFromVersion( modId, RegistryManager.getVersion( modId ) );
        REGISTRY_ID = modId;
    }

    private IRegistry getRegistryFromVersion( String modid, Version version ) {
        Set<? extends Comparable<Version>> filters = VERSION_FILTER.keySet();
        for ( Comparable<Version> filter : filters ) {
            if ( filter.compareTo( version ) == 0 ) {
                return VERSION_FILTER.get( filter ).get();
            }
        }
        String err = "No filter matches version " + version + " of mod " + modid + " in registered version router (no compatibility available for mod version).";
        CTSCompats.LOGGER.error( err );
        throw new IllegalArgumentException( err );
    }

    public void clientSetup() {
        REGISTRY.clientSetup();
    }

    public void register( IEventBus modEventBus ) {
        REGISTRY.register( modEventBus );
    }

    public void assign() {
        REGISTRY.assign();
    }

    public Optional<IColorRegistry> getColorRegistry() {
        return REGISTRY.getColorRegistry();
    }

    public Collection<RegistryObject<Block>> getRegistryBlocks() {
        return REGISTRY.getRegistryBlocks();
    }
}
