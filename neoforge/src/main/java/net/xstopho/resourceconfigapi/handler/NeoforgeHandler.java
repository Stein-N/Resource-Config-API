package net.xstopho.resourceconfigapi.handler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;

import java.util.Map;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NeoforgeHandler {

    @SubscribeEvent
    public static void registerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Constants.LOG.info("Syncing Configs with Client");

        //TODO: don't sync Client configs, they are loaded on server but shouldn't be send this would screw the client configs
        for (Map.Entry<ResourceLocation, ModConfig> entry : ConfigRegistry.CONFIGS.entrySet()) {
            ResourceLocation configLoc = entry.getKey();

            Constants.LOG.info("Syncing Config '{}'", configLoc);
            PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), new SyncConfigPayload(configLoc.toString(), entry.getValue().toJson().toString()));
        }
    }
}
