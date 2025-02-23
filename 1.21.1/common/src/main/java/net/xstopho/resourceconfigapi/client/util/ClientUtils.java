package net.xstopho.resourceconfigapi.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.player.LocalPlayer;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;
import net.xstopho.resourceconfigapi.network.NetworkHook;
import net.xstopho.resourceconfigapi.network.payloads.ConfigUpdatePayload;

public class ClientUtils {
    private static final LocalPlayer player = Minecraft.getInstance().player;

    public static boolean isOperator() {
        return player.hasPermissions(4);
    }

    public static void sendConfigUpdateToServer(String file, String json) {
        if (!isOperator()) return;
        ConfigNetwork.INSTANCE.sendToServer(new ConfigUpdatePayload(file, json));
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
        return !isSingleplayer() && worldLoaded();
    }

    public static boolean worldLoaded() {
        return Minecraft.getInstance().level != null;
    }
}
