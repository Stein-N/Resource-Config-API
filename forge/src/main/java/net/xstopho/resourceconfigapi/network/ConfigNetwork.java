package net.xstopho.resourceconfigapi.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;
import net.xstopho.resourceconfigapi.ResourceConfig;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.network.packets.SyncBooleanConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packets.SyncDoubleConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packets.SyncIntegerConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packets.SyncStringConfigEntryPacket;

public class ConfigNetwork {

    public static SimpleChannel setupPackets() {
        SimpleChannel channel = ChannelBuilder.named(of("network")).acceptedVersions(Channel.VersionTest.exact(1)).networkProtocolVersion(1).simpleChannel();
        ResourceConfig.NETWORK = channel;

        channel.messageBuilder(SyncIntegerConfigEntryPacket.class, 0, NetworkDirection.PLAY_TO_CLIENT).decoder(SyncIntegerConfigEntryPacket::decode).encoder(SyncIntegerConfigEntryPacket::encode).consumerNetworkThread(SyncIntegerConfigEntryPacket::handle).add();
        channel.messageBuilder(SyncDoubleConfigEntryPacket.class, 1, NetworkDirection.PLAY_TO_CLIENT).decoder(SyncDoubleConfigEntryPacket::decode).encoder(SyncDoubleConfigEntryPacket::encode).consumerNetworkThread(SyncDoubleConfigEntryPacket::handle).add();
        channel.messageBuilder(SyncStringConfigEntryPacket.class, 2, NetworkDirection.PLAY_TO_CLIENT).decoder(SyncStringConfigEntryPacket::decode).encoder(SyncStringConfigEntryPacket::encode).consumerNetworkThread(SyncStringConfigEntryPacket::handle).add();
        channel.messageBuilder(SyncBooleanConfigEntryPacket.class, 3, NetworkDirection.PLAY_TO_CLIENT).decoder(SyncBooleanConfigEntryPacket::decode).encoder(SyncBooleanConfigEntryPacket::encode).consumerNetworkThread(SyncBooleanConfigEntryPacket::handle).add();

        return channel;
    }

    public static ResourceLocation of(String id) {
        return ResourceLocation.fromNamespaceAndPath(ResourceConfigConstants.MOD_ID, id);
    }
}
