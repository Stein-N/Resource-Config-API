package net.xstopho.resourceconfigapi.network.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;

public record SyncConfigPayload(String file, String json) {

    public static final StreamCodec<FriendlyByteBuf, SyncConfigPayload> CODEC =
            StreamCodec.composite(ByteBufCodecs.STRING_UTF8, SyncConfigPayload::file,
                    ByteBufCodecs.STRING_UTF8, SyncConfigPayload::json,
                    SyncConfigPayload::new);

    public static SyncConfigPayload decode(FriendlyByteBuf byteBuf) {
        return CODEC.decode(byteBuf);
    }

    public static void encode(SyncConfigPayload payload, FriendlyByteBuf byteBuf) {
        CODEC.encode(byteBuf, payload);
    }

    public static void handle(SyncConfigPayload payload, CustomPayloadEvent.Context context) {
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
}
