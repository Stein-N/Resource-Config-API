package net.xstopho.resourceconfigapi.network;

import net.minecraftforge.network.PacketDistributor;
import net.xstopho.resourceconfigapi.ResourceConfig;
import net.xstopho.resourceconfigapi.network.client.ConfigUpdatePayload;

public class ForgeNetworkHook implements NetworkHook {
    @Override
    public void sendConfigUpdateToServer(String file, String json) {
        ResourceConfig.NETWORK.send(new ConfigUpdatePayload(file, json), PacketDistributor.SERVER.noArg());
    }
}
