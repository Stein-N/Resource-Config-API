package net.xstopho.resourceconfigapi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;
import net.xstopho.resourceconfigapi.network.server.OperatorStatusPayload;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;
import net.xstopho.resourceconfigapi.util.PlayerUtils;

import java.util.Map;

public class ResourceConfig implements ModInitializer {

    @Override
    public void onInitialize() {
        ConfigNetwork.initServer();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Constants.LOG.info("Sync Operator status.");
            sender.sendPacket(new OperatorStatusPayload(PlayerUtils.isPlayerOperator(handler.player)));

            Constants.LOG.info("Syncing Server Configs with Client");
            for (Map.Entry<ResourceLocation, ModConfig> entry : ConfigRegistry.CONFIGS.entrySet()) {
                ResourceLocation configLoc = entry.getKey();

                if (configLoc.toString().contains("client")) continue;
                Constants.LOG.info("Sending data for config '{}'.", configLoc);

                sender.sendPacket(new SyncConfigPayload(entry.getKey().toString(), entry.getValue().toJson().toString()));
            }
        });
    }
}
