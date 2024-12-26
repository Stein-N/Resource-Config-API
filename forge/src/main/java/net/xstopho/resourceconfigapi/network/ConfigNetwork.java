package net.xstopho.resourceconfigapi.network;

import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.ResourceConfig;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;

public class ConfigNetwork {

    public static void initPayloads() {
        SimpleChannel channel = ChannelBuilder.named(Constants.of("config_network"))
                .acceptedVersions(Channel.VersionTest.exact(1))
                .networkProtocolVersion(1)
                .simpleChannel();
        ResourceConfig.NETWORK = channel;

        channel.messageBuilder(SyncConfigPayload.class)
                .encoder(SyncConfigPayload::encode)
                .decoder(SyncConfigPayload::decode)
                .consumerNetworkThread(SyncConfigPayload::handle)
                .add();
    }
}
