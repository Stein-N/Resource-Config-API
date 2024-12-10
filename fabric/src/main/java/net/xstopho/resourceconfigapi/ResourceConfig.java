package net.xstopho.resourceconfigapi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

public class ResourceConfig implements ModInitializer {

    @Override
    public void onInitialize() {
        ConfigNetwork.initServer();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Constants.LOG.info("Syncing Configs with Client");

            for (ModConfig config : ConfigRegistry.CONFIGS.values()) {
                Config annotation = config.getClazz().getAnnotation(Config.class);

                Constants.LOG.info("Syncing Config '{}' from Mod '{}'", annotation.fileName(), config.getModId());

                sender.sendPacket(new SyncConfigPayload(ConfigUtils.serializeConfig(config.getClazz())));
            }
        });
    }
}
