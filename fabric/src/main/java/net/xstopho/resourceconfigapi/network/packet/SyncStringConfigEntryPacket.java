package net.xstopho.resourceconfigapi.network.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.builder.IResourceConfigBuilder;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;

import java.util.HashMap;

public record SyncStringConfigEntryPacket(String configName, String path, String value) implements CustomPacketPayload {
    public static final Type<SyncStringConfigEntryPacket> PACKET_TYPE = new Type<>(ConfigNetwork.of("sync_string_config_entry_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncStringConfigEntryPacket> PACKET_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, SyncStringConfigEntryPacket::configName, ByteBufCodecs.STRING_UTF8, SyncStringConfigEntryPacket::path, ByteBufCodecs.STRING_UTF8, SyncStringConfigEntryPacket::value, SyncStringConfigEntryPacket::new);

    public static void apply(SyncStringConfigEntryPacket packet, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            HashMap<String, ResourceModConfig> configs = ConfigRegistry.getConfigFiles();

            if (configs.containsKey(packet.configName())) {
                ResourceModConfig config = configs.get(packet.configName());

                IResourceConfigBuilder builder = config.getBuilder();

                for (ConfigEntry<?> entry : builder.getEntries().values()) {
                    if (entry.getPath().equals(packet.path()) && entry.syncWithServer()) {
                        ((ConfigEntry<String>) entry).setServerValue(packet.value());
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
