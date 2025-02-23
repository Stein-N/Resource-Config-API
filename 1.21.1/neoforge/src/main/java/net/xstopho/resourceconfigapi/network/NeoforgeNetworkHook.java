package net.xstopho.resourceconfigapi.network;

import net.neoforged.neoforge.network.PacketDistributor;
import net.xstopho.resourceconfigapi.network.client.ConfigUpdatePayload;

public class NeoforgeNetworkHook implements NetworkHook {
    @Override
    public void sendConfigUpdateToServer(String file, String json) {
        PacketDistributor.sendToServer(new ConfigUpdatePayload(file, json));
    }
}
