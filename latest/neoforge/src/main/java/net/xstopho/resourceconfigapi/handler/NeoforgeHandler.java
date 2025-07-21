package net.xstopho.resourceconfigapi.handler;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.xstopho.resourceconfigapi.ConfigConstants;

@EventBusSubscriber(modid = ConfigConstants.MOD_ID)
public class NeoforgeHandler {

    @SubscribeEvent
    public static void registerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        ConfigConstants.syncConfigs(player);
    }
}
