package net.xstopho.resourceconfigapi.handler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xstopho.resourceconfigapi.ConfigConstants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;
import net.xstopho.resourceconfigapi.network.payloads.ConfigSyncPayload;

import java.util.Map;

@Mod.EventBusSubscriber(modid = ConfigConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeHandler {

    @SubscribeEvent
    public static void registerLoginEvents(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();

        ConfigConstants.LOG.info("Syncing Server Configs with Client");
        for (Map.Entry<ResourceLocation, ModConfig> entry : ConfigRegistry.getConfigEntries()) {
            ResourceLocation location = entry.getKey();
            ModConfig config = entry.getValue();

            if (location.toString().contains("client")) continue;
            ConfigConstants.LOG.info("Sending data for Config '{}'", location);
            ConfigNetwork.INSTANCE.sendToClient(player, new ConfigSyncPayload(location.toString(), config.toJson().toString()));
        }
    }
}
