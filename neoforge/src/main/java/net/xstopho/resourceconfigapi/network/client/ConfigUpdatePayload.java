package net.xstopho.resourceconfigapi.network.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;

public record ConfigUpdatePayload(String file, String json) implements CustomPacketPayload {

    public static final Type<ConfigUpdatePayload> TYPE = new Type<>(Constants.of("config_update_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ConfigUpdatePayload> CODEC =
            StreamCodec.composite(ByteBufCodecs.STRING_UTF8, ConfigUpdatePayload::file,
                    ByteBufCodecs.STRING_UTF8, ConfigUpdatePayload::json,
                    ConfigUpdatePayload::new);

    public static void handle(ConfigUpdatePayload payload, IPayloadContext context) {
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

            PlayerList playerList = context.player().getServer().getPlayerList();

            for (ServerPlayer player : playerList.getPlayers()) {
                if (player == context.player()) continue;
                PacketDistributor.sendToPlayer(player, new SyncConfigPayload(configLoc.toString(), jsonObject.toString()));
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
