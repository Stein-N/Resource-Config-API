package net.xstopho.resourceconfigapi.network.payloads;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.ConfigConstants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;

public record ConfigSyncPayload(String file, String json) implements CustomPacketPayload {
    public static final Type<ConfigSyncPayload> TYPE = ConfigConstants.type("config_sync_payload");
    public static final StreamCodec<RegistryFriendlyByteBuf, ConfigSyncPayload> CODEC =
            StreamCodec.composite(ByteBufCodecs.STRING_UTF8, ConfigSyncPayload::file,
                    ByteBufCodecs.STRING_UTF8, ConfigSyncPayload::json, ConfigSyncPayload::new);

    public static void handle(ConfigSyncPayload payload) {
        JsonObject jsonObject = JsonParser.parseString(payload.json()).getAsJsonObject();
        ResourceLocation configLocation = ResourceLocation.parse(payload.file());

        ConfigConstants.LOG.info("Receiving Config: {}", configLocation);
        if (ConfigRegistry.contains(configLocation)) {
            ModConfig config = ConfigRegistry.getConfig(configLocation);
            config.fromJson(jsonObject);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
