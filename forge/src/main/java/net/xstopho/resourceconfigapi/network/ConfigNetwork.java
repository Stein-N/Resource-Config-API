package net.xstopho.resourceconfigapi.network;

public class ConfigNetwork {

//    public static SimpleChannel setupPackets() {
//        SimpleChannel channel = ChannelBuilder.named(of("network")).acceptedVersions(Channel.VersionTest.exact(1)).networkProtocolVersion(1).simpleChannel();
//        ResourceConfig.NETWORK = channel;
//
//        channel.messageBuilder(SyncBooleanConfigEntryPacket.class, 3, NetworkDirection.PLAY_TO_CLIENT).decoder(SyncBooleanConfigEntryPacket::decode).encoder(SyncBooleanConfigEntryPacket::encode).consumerNetworkThread(SyncBooleanConfigEntryPacket::handle).add();
//
//        return channel;
//    }
//
//    public static ResourceLocation of(String id) {
//        return ResourceLocation.fromNamespaceAndPath(ResourceConfigConstants.MOD_ID, id);
//    }
}
