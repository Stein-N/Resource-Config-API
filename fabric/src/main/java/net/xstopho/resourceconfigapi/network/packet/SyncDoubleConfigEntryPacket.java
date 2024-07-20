package net.xstopho.resourceconfigapi.network.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.builder.IResourceConfigBuilder;
import net.xstopho.resourceconfigapi.config.ModConfigFile;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;

import java.util.HashMap;

public record SyncDoubleConfigEntryPacket(String configName, String path, Double value) implements CustomPacketPayload {
    public static final Type<SyncDoubleConfigEntryPacket> PACKET_TYPE = new Type<>(ConfigNetwork.of("sync_double_config_entry_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncDoubleConfigEntryPacket> PACKET_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, SyncDoubleConfigEntryPacket::configName, ByteBufCodecs.STRING_UTF8, SyncDoubleConfigEntryPacket::path, ByteBufCodecs.DOUBLE, SyncDoubleConfigEntryPacket::value, SyncDoubleConfigEntryPacket::new);

    public static void apply(SyncDoubleConfigEntryPacket packet, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            HashMap<String, ModConfigFile> configs = ConfigRegistry.getConfigFiles();

            if (configs.containsKey(packet.configName())) {
                ModConfigFile config = configs.get(packet.configName());

                IResourceConfigBuilder builder = config.getBuilder();

                for (ConfigEntry<?> entry : builder.getEntries().values()) {
                    if (entry.getPath().equals(packet.path()) && entry.syncWithServer()) {
                        ((ConfigEntry<Double>) entry).setServerValue(packet.value());
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
