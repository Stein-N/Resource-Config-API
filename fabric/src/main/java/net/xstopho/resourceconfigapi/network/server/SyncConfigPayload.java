package net.xstopho.resourceconfigapi.network.server;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

public record SyncConfigPayload(byte[] config) implements CustomPacketPayload {

    public static final Type<SyncConfigPayload> TYPE = new Type<>(Constants.of("sync_config_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncConfigPayload> CODEC =
            StreamCodec.composite(ByteBufCodecs.BYTE_ARRAY, SyncConfigPayload::config, SyncConfigPayload::new);

    public static void handle(SyncConfigPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            Class<?> deserializedConfig = ConfigUtils.deserializeConfig(payload.config());

            if (deserializedConfig.isAnnotationPresent(Config.class)) {
                Config annotation = deserializedConfig.getAnnotation(Config.class);

                ModConfig modConfig = ConfigRegistry.CONFIGS.get(annotation.fileName());
                modConfig.syncWithServerConfig(deserializedConfig);

            } else {
                Constants.LOG.error("Failed to deserialize Config from server!");
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
