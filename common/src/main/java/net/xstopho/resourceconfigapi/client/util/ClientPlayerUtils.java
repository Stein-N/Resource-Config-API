package net.xstopho.resourceconfigapi.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;

public class ClientPlayerUtils {

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
        return !isSingleplayer();
    }
}
