package net.xstopho.resourceconfigapi.network.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.PacketDistributor;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.ResourceConfig;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;

public record ConfigUpdatePayload(String file, String json) {

    public static final StreamCodec<FriendlyByteBuf, ConfigUpdatePayload> CODEC =
            StreamCodec.composite(ByteBufCodecs.STRING_UTF8, ConfigUpdatePayload::file,
                    ByteBufCodecs.STRING_UTF8, ConfigUpdatePayload::json,
                    ConfigUpdatePayload::new);

    public static ConfigUpdatePayload decode(FriendlyByteBuf byteBuf) {
        return CODEC.decode(byteBuf);
    }

    public static void encode(ConfigUpdatePayload payload, FriendlyByteBuf byteBuf) {
        CODEC.encode(byteBuf, payload);
    }

    public static void handle(ConfigUpdatePayload payload, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            JsonObject jsonObject = JsonParser.parseString(payload.json()).getAsJsonObject();
            ResourceLocation configLoc = ResourceLocation.parse(payload.file());

            if (configLoc.toString().contains("client")) return;

            if (ConfigRegistry.CONFIGS.containsKey(configLoc)) {
                Constants.LOG.info("Receiving update for Config: {}", configLoc);
                ModConfig config = ConfigRegistry.CONFIGS.get(configLoc);
                config.writeConfig(jsonObject);
                config.fromJson(jsonObject);
            }

            PlayerList playerList = context.getSender().getServer().getPlayerList();

            for (ServerPlayer player : playerList.getPlayers()) {
                if (player == context.getSender()) continue;
                ResourceConfig.NETWORK.send(new SyncConfigPayload(configLoc.toString(), jsonObject.toString()), PacketDistributor.PLAYER.with(player));
            }
        });
    }
}
