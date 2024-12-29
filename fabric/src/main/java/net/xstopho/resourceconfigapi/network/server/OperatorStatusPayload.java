package net.xstopho.resourceconfigapi.network.server;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.client.ClientConstants;
import net.xstopho.resourceconfigapi.client.util.ClientUtils;

public record OperatorStatusPayload(boolean status) implements CustomPacketPayload {
    public static final Type<OperatorStatusPayload> TYPE = new Type<>(Constants.of("operator_status_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OperatorStatusPayload> CODEC =
            StreamCodec.composite(ByteBufCodecs.BOOL, OperatorStatusPayload::status, OperatorStatusPayload::new);

    public static void handle(OperatorStatusPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            ClientUtils.setOperatorStatus(payload.status());
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
