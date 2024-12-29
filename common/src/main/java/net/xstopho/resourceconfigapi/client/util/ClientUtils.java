package net.xstopho.resourceconfigapi.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.xstopho.resourceconfigapi.network.NetworkHook;
import net.xstopho.resourceconfigapi.platform.CoreServices;

public class ClientUtils {

    private static boolean operatorStatus = true;

    public static void setOperatorStatus(boolean status) {
        operatorStatus = status;
    }

    public static boolean isOperator() {
        return isSingleplayer() || operatorStatus;
    }

    public static void sendConfigUpdateToServer(String file, String json) {
        if (!isOperator()) return;
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
