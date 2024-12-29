package net.xstopho.resourceconfigapi.handler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.server.OperatorStatusPayload;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;
import net.xstopho.resourceconfigapi.util.PlayerUtils;

import java.util.Map;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NeoforgeHandler {

    @SubscribeEvent
    public static void registerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();

        PacketDistributor.sendToPlayer(player, new OperatorStatusPayload(PlayerUtils.isPlayerOperator(player)));

        Constants.LOG.info("Syncing Server Configs with Client");
        for (Map.Entry<ResourceLocation, ModConfig> entry : ConfigRegistry.CONFIGS.entrySet()) {
            ResourceLocation location = entry.getKey();
            ModConfig config = entry.getValue();

            if (location.toString().contains("client")) continue;
            Constants.LOG.info("Sending data for Config '{}'", location);

            PacketDistributor.sendToPlayer(player, new SyncConfigPayload(location.toString(), config.toJson().toString()));
        }
    }
}
