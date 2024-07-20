package net.xstopho.resourceconfigapi.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.network.packet.SyncBooleanConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packet.SyncDoubleConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packet.SyncIntegerConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packet.SyncStringConfigEntryPacket;

public class ConfigNetwork {

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(SyncIntegerConfigEntryPacket.PACKET_TYPE, SyncIntegerConfigEntryPacket::apply);
        ClientPlayNetworking.registerGlobalReceiver(SyncDoubleConfigEntryPacket.PACKET_TYPE, SyncDoubleConfigEntryPacket::apply);
        ClientPlayNetworking.registerGlobalReceiver(SyncStringConfigEntryPacket.PACKET_TYPE, SyncStringConfigEntryPacket::apply);
        ClientPlayNetworking.registerGlobalReceiver(SyncBooleanConfigEntryPacket.PACKET_TYPE, SyncBooleanConfigEntryPacket::apply);
    }

    public static void initServer() {
        PayloadTypeRegistry.playS2C().register(SyncIntegerConfigEntryPacket.PACKET_TYPE, SyncIntegerConfigEntryPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(SyncDoubleConfigEntryPacket.PACKET_TYPE, SyncDoubleConfigEntryPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(SyncStringConfigEntryPacket.PACKET_TYPE, SyncStringConfigEntryPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(SyncBooleanConfigEntryPacket.PACKET_TYPE, SyncBooleanConfigEntryPacket.PACKET_CODEC);
    }

    public static ResourceLocation of(String id) {
        return ResourceLocation.fromNamespaceAndPath(ResourceConfigConstants.MOD_ID, id);
    }
}
