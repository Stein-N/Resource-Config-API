package net.xstopho.resourceconfigapi.network;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.network.packets.SyncBooleanConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packets.SyncDoubleConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packets.SyncIntegerConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packets.SyncStringConfigEntryPacket;

public class ConfigNetwork {

    public static void initClient(PayloadRegistrar payload) {
        payload.playToClient(SyncIntegerConfigEntryPacket.PACKET_TYPE, SyncIntegerConfigEntryPacket.PACKET_CODEC, SyncIntegerConfigEntryPacket::apply);
        payload.playToClient(SyncDoubleConfigEntryPacket.PACKET_TYPE, SyncDoubleConfigEntryPacket.PACKET_CODEC, SyncDoubleConfigEntryPacket::apply);
        payload.playToClient(SyncStringConfigEntryPacket.PACKET_TYPE, SyncStringConfigEntryPacket.PACKET_CODEC, SyncStringConfigEntryPacket::apply);
        payload.playToClient(SyncBooleanConfigEntryPacket.PACKET_TYPE, SyncBooleanConfigEntryPacket.PACKET_CODEC, SyncBooleanConfigEntryPacket::apply);
    }

    public static ResourceLocation of(String id) {
        return ResourceLocation.fromNamespaceAndPath(ResourceConfigConstants.MOD_ID, id);
    }
}
