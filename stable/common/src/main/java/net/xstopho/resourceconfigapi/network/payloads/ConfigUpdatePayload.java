package net.xstopho.resourceconfigapi.network.payloads;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.xstopho.resourceconfigapi.ConfigConstants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;

public record ConfigUpdatePayload(String file, String json) implements CustomPacketPayload {
    public static final Type<ConfigUpdatePayload> TYPE = ConfigConstants.type("config_update_payload");
    public static final StreamCodec<FriendlyByteBuf, ConfigUpdatePayload> CODEC =
            StreamCodec.composite(ByteBufCodecs.STRING_UTF8, ConfigUpdatePayload::file,
                    ByteBufCodecs.STRING_UTF8, ConfigUpdatePayload::json, ConfigUpdatePayload::new);

    public static void handle(ConfigUpdatePayload payload, MinecraftServer server) {
        if (server == null || payload.file().contains("client")) return;
        server.execute(() -> {
            JsonObject jsonObject = JsonParser.parseString(payload.json()).getAsJsonObject();
            Identifier configLocation = Identifier.parse(payload.file());

            if (ConfigRegistry.contains(configLocation)) {
                ConfigConstants.LOG.info("Receive update for Config: {}", configLocation);
                ModConfig config = ConfigRegistry.getConfig(configLocation);
                config.fromJson(jsonObject);
                config.save();
            }

            ConfigConstants.LOG.info("Sync config changes with Player");
            server.getPlayerList().getPlayers().forEach(player ->
                    ConfigNetwork.INSTANCE.sendToClient(player, new ConfigSyncPayload(payload.file(), payload.json())));
        });
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
