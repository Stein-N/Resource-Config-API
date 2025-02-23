package net.xstopho.resourceconfigapi;

import net.fabricmc.api.ClientModInitializer;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;

public class ResourceConfigClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigNetwork.initClient();
    }
}
