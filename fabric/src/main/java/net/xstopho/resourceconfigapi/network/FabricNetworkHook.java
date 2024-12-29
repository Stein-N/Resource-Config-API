package net.xstopho.resourceconfigapi.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.xstopho.resourceconfigapi.network.client.ConfigUpdatePayload;

public class FabricNetworkHook implements NetworkHook {
    @Override
    public void sendConfigUpdateToServer(String file, String json) {
        ClientPlayNetworking.send(new ConfigUpdatePayload(file, json));
    }
}
