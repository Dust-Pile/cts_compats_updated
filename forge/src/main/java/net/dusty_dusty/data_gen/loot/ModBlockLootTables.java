package net.dusty_dusty.data_gen.loot;

import dev.architectury.registry.registries.RegistrySupplier;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.dusty_dusty.cts_compats.CTSCompats;
import net.dusty_dusty.cts_compats.RegistryManager;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ModBlockLootTables extends BlockLootSubProvider {
    private static final LootPoolEntryContainer.Builder<?> GRASS_LOOT = LootTableReference.lootTableReference(
            ResourceLocation.fromNamespaceAndPath( "minecraft", "blocks/grass" ) );
    private static final boolean IS_ENABLED = false;
    public ModBlockLootTables() {
        super( Set.of(), FeatureFlags.REGISTRY.allFlags() );
    }

    @Override
    protected void generate() {
        if ( !IS_ENABLED ) {
            return;
        }

        RegistryManager.forEachRegistryAndID( ( modId, registry ) -> {
            for ( RegistrySupplier<? extends Block> blockRegister : registry.getRegistryBlocks() ) {
                Block block = blockRegister.get();
                Block originBlock = ( (ISlabCopy) block ).getOriginBlock();

                if ( modId.equals(CTSCompats.VB_MODID ) ) {
                    modId = "minecraft";
                }

                ResourceLocation parentLocation = ResourceLocation.fromNamespaceAndPath(
                        modId, "blocks/" + originBlock.getDescriptionId().split( "\\." )[2] );

                // Workaround so it thinks these exist
                // TODO: IMPORTANT NOTE!!! MAKE SURE TO DELETE THE EXTRA TABLES!!!
                this.add( originBlock, LootTable.lootTable() );

                this.add( block, simpleReference( parentLocation ) );
            }
        } );

        this.add( Blocks.GRASS, LootTable.lootTable() );
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        if ( !IS_ENABLED ) {
            return blocks;
        }
        for ( Block block : RegistryManager.getAllBlocks() ) {
            blocks.add( ( (ISlabCopy) block ).getOriginBlock() );
            blocks.add( block );
        }

        blocks.add( Blocks.GRASS );
        return blocks;
    }

    static LootTable.Builder simpleReference( ResourceLocation parent ) {
        return LootTable.lootTable().withPool( simplePool().add( LootTableReference.lootTableReference( parent ) ) ).setRandomSequence( parent );
    }

    static LootPool.Builder simplePool() {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F));
    }
}
