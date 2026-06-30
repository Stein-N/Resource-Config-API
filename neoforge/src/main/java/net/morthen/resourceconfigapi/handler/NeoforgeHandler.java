package net.morthen.resourceconfigapi.handler;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.morthen.resourceconfigapi.ConfigConstants;

@EventBusSubscriber(modid = ConfigConstants.MOD_ID)
public class NeoforgeHandler {

    @SubscribeEvent
    public static void registerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        ConfigConstants.syncConfigs(player);
    }
}
