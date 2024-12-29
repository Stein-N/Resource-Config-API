package net.xstopho.resourceconfigapi.network;

public interface NetworkHook {

    void sendConfigUpdateToServer(String file, String json);
}
