package net.xstopho.resourceconfigapi.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.xstopho.resourceconfigapi.client.ClientConstants;

public record OperatorStatusPayload(boolean status) {

    public static final StreamCodec<FriendlyByteBuf, OperatorStatusPayload> CODEC =
            StreamCodec.composite(ByteBufCodecs.BOOL, OperatorStatusPayload::status, OperatorStatusPayload::new);

    public static OperatorStatusPayload decode(FriendlyByteBuf byteBuf) {
        return CODEC.decode(byteBuf);
    }

    public static void encode(OperatorStatusPayload payload, FriendlyByteBuf byteBuf) {
        CODEC.encode(byteBuf, payload);
    }

    public static void handle(OperatorStatusPayload payload, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ClientConstants.isOperator = payload.status();
        });
    }
}
