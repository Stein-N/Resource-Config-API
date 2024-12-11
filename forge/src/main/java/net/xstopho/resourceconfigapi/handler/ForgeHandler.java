package net.xstopho.resourceconfigapi.handler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.ResourceConfig;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeHandler {

    @SubscribeEvent
    public static void registerLoginEvents(PlayerEvent.PlayerLoggedInEvent event) {
        Constants.LOG.info("Syncing Configs with Client");

        for (ModConfig config : ConfigRegistry.CONFIGS.values()) {
            Config annotation = config.getClazz().getAnnotation(Config.class);
            ResourceLocation configLoc = ConfigRegistry.of(config.getModId(), annotation.type(), annotation.fileName());

            Constants.LOG.info("Syncing Config '{}'", configLoc);

            ResourceConfig.NETWORK.send(new SyncConfigPayload(configLoc.toString(), config.readConfig().toString()), PacketDistributor.PLAYER.with((ServerPlayer) event.getEntity()));
        }
    }
}
