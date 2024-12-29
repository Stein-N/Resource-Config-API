package net.xstopho.resourceconfigapi.network.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;

public record SyncConfigPayload(String file, String json) implements CustomPacketPayload {

    public static final Type<SyncConfigPayload> TYPE = new Type<>(Constants.of("sync_config_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncConfigPayload> CODEC =
            StreamCodec.composite(ByteBufCodecs.STRING_UTF8, SyncConfigPayload::file,
                    ByteBufCodecs.STRING_UTF8, SyncConfigPayload::json,
                    SyncConfigPayload::new);

    public static void handle(SyncConfigPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            JsonObject jsonObject = JsonParser.parseString(payload.json()).getAsJsonObject();
            ResourceLocation configLoc = ResourceLocation.parse(payload.file());

            Constants.LOG.info("Receiving data for config '{}'", configLoc);
            if (ConfigRegistry.CONFIGS.containsKey(configLoc)) {
                ModConfig config = ConfigRegistry.CONFIGS.get(configLoc);
                config.fromJson(jsonObject);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
