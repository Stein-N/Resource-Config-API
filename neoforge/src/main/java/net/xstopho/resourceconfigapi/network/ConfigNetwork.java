package net.xstopho.resourceconfigapi.network;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;

public class ConfigNetwork {

    public static void initClient(PayloadRegistrar payload) {
        //payload.playToClient(SyncBooleanConfigEntryPacket.PACKET_TYPE, SyncBooleanConfigEntryPacket.PACKET_CODEC, SyncBooleanConfigEntryPacket::apply);
    }

    public static ResourceLocation of(String id) {
        return ResourceLocation.fromNamespaceAndPath(ResourceConfigConstants.MOD_ID, id);
    }
}
