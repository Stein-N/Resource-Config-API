package net.xstopho.resourceconfigapi.handler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.ResourceConfig;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.server.OperatorStatusPayload;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;
import net.xstopho.resourceconfigapi.util.PlayerUtils;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeHandler {

    @SubscribeEvent
    public static void registerLoginEvents(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();

        ResourceConfig.NETWORK.send(new OperatorStatusPayload(PlayerUtils.isPlayerOperator(player)), PacketDistributor.PLAYER.with(player));

        Constants.LOG.info("Syncing Server Configs with Client");
        for (Map.Entry<ResourceLocation, ModConfig> entry : ConfigRegistry.CONFIGS.entrySet()) {
            ResourceLocation location = entry.getKey();
            ModConfig config = entry.getValue();

            if (location.toString().contains("client")) continue;
            Constants.LOG.info("Sending data for Config '{}'", location);

            ResourceConfig.NETWORK.send(new SyncConfigPayload(location.toString(), config.toJson().toString()), PacketDistributor.PLAYER.with(player));
        }
    }
}
