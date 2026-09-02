package net.dusty_dusty.cts_compats.mods.biomesOPlenty.block;

import biomesoplenty.api.damagesource.BOPDamageTypes;
import biomesoplenty.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.NotNull;

public class ThermalCalciteVentSlab extends ThermalCalciteSlab {
    public ThermalCalciteVentSlab( Block originalBlock ) {
        super(originalBlock);
    }

    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, Entity entity ) {
        if ( !entity.fireImmune() && entity instanceof LivingEntity livingEntity && !EnchantmentHelper.hasFrostWalker( livingEntity ) ) {
            DamageSource fumarole = new DamageSource(level.registryAccess()
                    .registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(BOPDamageTypes.FUMAROLE));
            entity.hurt(fumarole, 1.0F);
        }

        super.stepOn( level, pos, state, entity );
    }

    public void animateTick(@NotNull BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand ) {
        super.animateTick(stateIn, worldIn, pos, rand);
        double yOffset = stateIn.getValue( TYPE ) == SlabType.BOTTOM ? 0.5 : 1.0 ;
        worldIn.addAlwaysVisibleParticle(ModParticles.STEAM,
                pos.getX() + 0.5F + (rand.nextDouble() - rand.nextDouble()) / 6.0, pos.getY() + yOffset,
                pos.getZ() + 0.5 + (rand.nextDouble() - rand.nextDouble()) / 6.0, 0.0F, 0.02, 0.0F);
    }

    public boolean isPathfindable(@NotNull BlockState p_154341_, @NotNull BlockGetter p_154342_, @NotNull BlockPos p_154343_, @NotNull PathComputationType p_154344_ ) {
        return false;
    }
}
