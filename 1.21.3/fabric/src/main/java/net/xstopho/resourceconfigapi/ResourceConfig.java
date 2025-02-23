package net.xstopho.resourceconfigapi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;
import net.xstopho.resourceconfigapi.network.payloads.ConfigUpdatePayload;
import net.xstopho.resourceconfigapi.network.payloads.ConfigSyncPayload;

import java.util.Map;

public class ResourceConfig implements ModInitializer {

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playS2C().register(ConfigSyncPayload.TYPE, ConfigSyncPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ConfigUpdatePayload.TYPE, ConfigUpdatePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ConfigUpdatePayload.TYPE,
                (payload, context) -> ConfigUpdatePayload.handle(payload, context.server()));


        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ConfigConstants.LOG.info("Syncing Server Configs with Client");
            for (Map.Entry<ResourceLocation, ModConfig> entry : ConfigRegistry.getConfigEntries()) {
                ResourceLocation location = entry.getKey();
                ModConfig config = entry.getValue();

                if (location.toString().contains("client")) continue;
                ConfigConstants.LOG.info("Sending data for config '{}'.", location);
                ConfigNetwork.INSTANCE.sendToClient(handler.getPlayer(), new ConfigSyncPayload(location.toString(), config.toJson().toString()));
            }
        });
    }
}
