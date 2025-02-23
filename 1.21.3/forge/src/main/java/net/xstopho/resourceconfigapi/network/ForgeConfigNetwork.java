package net.xstopho.resourceconfigapi.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import net.xstopho.resourceconfigapi.ResourceConfig;

public class ForgeConfigNetwork implements ConfigNetwork {
    @Override
    public void sendToClient(ServerPlayer player, CustomPacketPayload payload) {
        ResourceConfig.NETWORK.send(payload, PacketDistributor.PLAYER.with(player));
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        ResourceConfig.NETWORK.send(payload, PacketDistributor.SERVER.noArg());
    }
}
