package net.morthen.resourceconfigapi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.morthen.resourceconfigapi.network.payloads.ConfigSyncPayload;

public class ResourceConfigClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(ConfigSyncPayload.TYPE,
                (payload, context) -> ConfigSyncPayload.handle(payload));
    }
}
