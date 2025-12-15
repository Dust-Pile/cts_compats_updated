package net.dusty_dusty.dh_dimension_switch;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class LodRenderChecker {
    private static final Minecraft CLIENT = Minecraft.getInstance();
    private static boolean shouldRun = true;

    public static boolean shouldRender(){
        return shouldRun;
    }

    public static void updateRender() {
        assert CLIENT.level != null;
        assert CLIENT.player != null;
        if( !CLIENT.level.dimensionType().hasCeiling() ) {
            shouldRun = !CLIENT.level.dimension().equals( Level.OVERWORLD ) || CLIENT.player.position().y > 40;
        }
        shouldRun = false;
    }
}