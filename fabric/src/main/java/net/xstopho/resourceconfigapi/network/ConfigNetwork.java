package net.xstopho.resourceconfigapi.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;

public class ConfigNetwork {

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(SyncConfigPayload.TYPE, SyncConfigPayload::handle);
    }

    public static void initServer() {
        PayloadTypeRegistry.playS2C().register(SyncConfigPayload.TYPE, SyncConfigPayload.CODEC);
    }
}
