package net.xstopho.resourceconfigapi.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.network.packet.SyncIntegerConfigEntryPacket;

public class ConfigNetwork {

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(SyncIntegerConfigEntryPacket.PACKET_TYPE, SyncIntegerConfigEntryPacket::apply);
    }

    public static void initServer() {
        PayloadTypeRegistry.playS2C().register(SyncIntegerConfigEntryPacket.PACKET_TYPE, SyncIntegerConfigEntryPacket.PACKET_CODEC);
    }

    public static ResourceLocation of(String id) {
        return ResourceLocation.fromNamespaceAndPath(ResourceConfigConstants.MOD_ID, id);
    }
}
