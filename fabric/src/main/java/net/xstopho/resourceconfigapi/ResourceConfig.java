package net.xstopho.resourceconfigapi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;
import net.xstopho.resourceconfigapi.network.server.OperatorStatusPayload;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;
import net.xstopho.resourceconfigapi.util.PlayerUtils;

public class ResourceConfig implements ModInitializer {

    @Override
    public void onInitialize() {
        ConfigNetwork.initServer();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Constants.LOG.info("Sync Operator status.");
            sender.sendPacket(new OperatorStatusPayload(PlayerUtils.isPlayerOperator(handler.player)));

            Constants.LOG.info("Syncing Configs with Client");

            for (ModConfig config : ConfigRegistry.CONFIGS.values()) {
                Config annotation = config.getClazz().getAnnotation(Config.class);
                ResourceLocation configLoc = ConfigRegistry.of(config.getModId(), annotation.type(), annotation.fileName());

                Constants.LOG.info("Syncing Config '{}'", configLoc);

                sender.sendPacket(new SyncConfigPayload(configLoc.toString(), config.toJson().toString()));
            }
        });
    }
}
