package net.xstopho.resourceconfigapi.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xstopho.resourceconfigapi.ConfigConstants;

@Mod.EventBusSubscriber(modid = ConfigConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeHandler {

    @SubscribeEvent
    public static void registerLoginEvents(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        ConfigConstants.syncConfigs(player);
    }
}
