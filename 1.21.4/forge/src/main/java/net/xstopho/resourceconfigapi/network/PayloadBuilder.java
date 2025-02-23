package net.xstopho.resourceconfigapi.network;

import net.minecraftforge.network.*;
import net.xstopho.resourceconfigapi.ConfigConstants;
import net.xstopho.resourceconfigapi.ResourceConfig;
import net.xstopho.resourceconfigapi.network.payloads.ConfigSyncPayload;
import net.xstopho.resourceconfigapi.network.payloads.ConfigUpdatePayload;

public class PayloadBuilder {
    public static void build() {
        SimpleChannel channel = ChannelBuilder.named(ConfigConstants.of("config_network"))
                .acceptedVersions(Channel.VersionTest.exact(1))
                .networkProtocolVersion(1)
                .simpleChannel();
        ResourceConfig.NETWORK = channel;

        channel.messageBuilder(ConfigSyncPayload.class, 0, NetworkDirection.PLAY_TO_CLIENT)
               .encoder((payload, byteBuf) -> ConfigSyncPayload.CODEC.encode(byteBuf, payload))
               .decoder(ConfigSyncPayload.CODEC::decode)
               .consumerNetworkThread((payload, context) -> {
                   context.enqueueWork(() -> ConfigSyncPayload.handle(payload));
               }).add();

        channel.messageBuilder(ConfigUpdatePayload.class, 1, NetworkDirection.PLAY_TO_SERVER)
               .encoder((payload, byteBuf) -> ConfigUpdatePayload.CODEC.encode(byteBuf, payload))
               .decoder(ConfigUpdatePayload.CODEC::decode)
               .consumerNetworkThread((payload, context) -> {
                   context.enqueueWork(() -> ConfigUpdatePayload.handle(payload, context.getSender().getServer()));
               }).add();
    }
}
