package net.dusty_dusty.cts_compats.mods.weathering.block;

import net.countered.terrainslabs.block.customslabs.soilslabs.SnowySpreadableSlab;
import net.countered.terrainslabs.block.interfaces.ISlabCopy;
import net.countered.terrainslabs.registries.ModBlocksRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class RootedGrassSlab extends SnowySpreadableSlab {
    public RootedGrassSlab(Block block, ISlabCopy duel) {
        super(block, duel);
    }

    @Override
    protected boolean canSpread() {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (item instanceof ShovelItem && !(Boolean)state.getValue(SnowyDirtBlock.SNOWY)) {
            level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, ModBlocksRegistry.PATH_SLAB.get().withPropertiesOf(state));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (item instanceof HoeItem) {
            level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, ModBlocksRegistry.GRASS_SLAB.get().withPropertiesOf(state));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                Block.popResourceFromFace(level, pos, hitResult.getDirection(), Items.HANGING_ROOTS.getDefaultInstance());
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(state, level, pos, player, hand, hitResult);
        }
    }
}