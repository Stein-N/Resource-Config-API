package net.xstopho.resourceconfigapi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.xstopho.resourceconfigapi.network.payloads.ConfigSyncPayload;
import net.xstopho.resourceconfigapi.network.payloads.ConfigUpdatePayload;

public class ResourceConfig implements ModInitializer {

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.clientboundPlay().register(ConfigSyncPayload.TYPE, ConfigSyncPayload.CODEC);
        PayloadTypeRegistry.clientboundConfiguration().register(ConfigSyncPayload.TYPE, ConfigSyncPayload.CODEC);

        PayloadTypeRegistry.serverboundPlay().register(ConfigUpdatePayload.TYPE, ConfigUpdatePayload.CODEC);
        PayloadTypeRegistry.serverboundConfiguration().register(ConfigUpdatePayload.TYPE, ConfigUpdatePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ConfigUpdatePayload.TYPE,
                (payload, context) -> ConfigUpdatePayload.handle(payload, context.server()));


        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer player = handler.getPlayer();
            ConfigConstants.syncConfigs(player);
        });
    }
}
