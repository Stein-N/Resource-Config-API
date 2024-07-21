package net.xstopho.resourceconfigapi.network.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.builder.IResourceConfigBuilder;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;

import java.util.HashMap;

public record SyncBooleanConfigEntryPacket(String configName, String path, Boolean value) implements CustomPacketPayload {
    public static final Type<SyncBooleanConfigEntryPacket> PACKET_TYPE = new Type<>(ConfigNetwork.of("sync_boolean_config_entry_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncBooleanConfigEntryPacket> PACKET_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, SyncBooleanConfigEntryPacket::configName, ByteBufCodecs.STRING_UTF8, SyncBooleanConfigEntryPacket::path, ByteBufCodecs.BOOL, SyncBooleanConfigEntryPacket::value, SyncBooleanConfigEntryPacket::new);

    public static void apply(SyncBooleanConfigEntryPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            HashMap<String, ResourceModConfig> configs = ConfigRegistry.getConfigFiles();

            if (configs.containsKey(packet.configName())) {
                ResourceModConfig config = configs.get(packet.configName());

                IResourceConfigBuilder builder = config.getBuilder();

                for (ConfigEntry<?> entry : builder.getEntries().values()) {
                    if (entry.getPath().equals(packet.path()) && entry.syncWithServer()) {
                        ((ConfigEntry<Boolean>) entry).setServerValue(packet.value());
                        entry.setSynced();
                    }
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}
