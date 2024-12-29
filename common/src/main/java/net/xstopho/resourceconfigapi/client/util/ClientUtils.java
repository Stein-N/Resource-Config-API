package net.xstopho.resourceconfigapi.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.xstopho.resourceconfigapi.client.ClientConstants;
import net.xstopho.resourceconfigapi.network.NetworkHook;
import net.xstopho.resourceconfigapi.platform.CoreServices;

public class ClientUtils {

    public static void sendConfigUpdateToServer(String file, String json) {
        if (!ClientConstants.isOperator) return;
        CoreServices.load(NetworkHook.class).sendConfigUpdateToServer(file, json);
    }

    public static boolean isSingleplayer() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPacketListener connection = minecraft.getConnection();
        if (connection != null) {
            ServerData server = connection.getServerData();
            return server == null;
        }
        return false;
    }

    public static boolean isMultiplayer() {
        return !isSingleplayer() && Minecraft.getInstance().level != null;
    }
}
