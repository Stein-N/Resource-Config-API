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

public record SyncIntegerConfigEntryPacket(String configName, String path, Integer value) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncIntegerConfigEntryPacket> PACKET_TYPE = new CustomPacketPayload.Type<>(ConfigNetwork.of("sync_integer_config_entry_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncIntegerConfigEntryPacket> PACKET_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, SyncIntegerConfigEntryPacket::configName, ByteBufCodecs.STRING_UTF8, SyncIntegerConfigEntryPacket::path, ByteBufCodecs.INT, SyncIntegerConfigEntryPacket::value, SyncIntegerConfigEntryPacket::new);

    public static void apply(SyncIntegerConfigEntryPacket packet, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            HashMap<String, ResourceModConfig> configs = ConfigRegistry.getConfigFiles();

            if (configs.containsKey(packet.configName())) {
                ResourceModConfig config = configs.get(packet.configName());

                IResourceConfigBuilder builder = config.getBuilder();

                for (ConfigEntry<?> entry : builder.getEntries().values()) {
                    if (entry.getPath().equals(packet.path()) && entry.syncWithServer()) {
                        ((ConfigEntry<Integer>) entry).setServerValue(packet.value());
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
