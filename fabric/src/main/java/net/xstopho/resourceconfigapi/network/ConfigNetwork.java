package net.xstopho.resourceconfigapi.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.xstopho.resourceconfigapi.network.client.ConfigUpdatePayload;
import net.xstopho.resourceconfigapi.network.server.OperatorStatusPayload;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;

public class ConfigNetwork {

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(SyncConfigPayload.TYPE, SyncConfigPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(OperatorStatusPayload.TYPE, OperatorStatusPayload::handle);
    }

    public static void initServer() {
        PayloadTypeRegistry.playS2C().register(SyncConfigPayload.TYPE, SyncConfigPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(OperatorStatusPayload.TYPE, OperatorStatusPayload.CODEC);

        PayloadTypeRegistry.playC2S().register(ConfigUpdatePayload.TYPE, ConfigUpdatePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ConfigUpdatePayload.TYPE, ConfigUpdatePayload::handle);
    }
}
