package net.xstopho.resourceconfigapi.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class PlayerUtils {

    public static boolean isPlayerOperator(ServerPlayer player) {
        MinecraftServer server = player.getServer();
        if (server != null) {
            return server.getPlayerList().isOp(player.getGameProfile());
        }
        return false;
    }
}
