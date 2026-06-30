package net.morthen.resourceconfigapi.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.morthen.resourceconfigapi.util.ConfigUtils;

public interface ConfigNetwork {
    ConfigNetwork INSTANCE = ConfigUtils.load(ConfigNetwork.class);

    void sendToClient(ServerPlayer player, CustomPacketPayload payload);
    void sendToServer(CustomPacketPayload payload);
}
