package net.dusty_dusty.cts_compats.fabric;

import net.dusty_dusty.cts_compats.CTSCompatsClient;
import net.fabricmc.api.ClientModInitializer;

public final class CTSCompatsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CTSCompatsClient.init();
    }
}
