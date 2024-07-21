package net.xstopho.resourceconfigapi.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.xstopho.resourceconfigapi.ResourceConfig;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.network.packets.SyncBooleanConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packets.SyncDoubleConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packets.SyncIntegerConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packets.SyncStringConfigEntryPacket;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ResourceConfigConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {

    @SubscribeEvent
    public static void registerLoginEvents(PlayerEvent.PlayerLoggedInEvent event) {
        ResourceConfigConstants.LOG.info("Syncing Config Values with Client");
        HashMap<String, ResourceModConfig> configs = ConfigRegistry.getConfigFiles();

        for (Map.Entry<String, ResourceModConfig> config : configs.entrySet()) {
            ResourceConfigConstants.LOG.info("Syncing Values for ModConfigFile: {}", config.getKey());
            ResourceModConfig modConfig = config.getValue();

            for (ConfigEntry<?> entry : modConfig.getBuilder().getEntries().values()) {
                if (entry.syncWithServer()) {
                    sendPacket((ServerPlayer) event.getEntity(), entry, config.getKey());
                }
            }
        }
    }

    public static void sendPacket(ServerPlayer player, ConfigEntry<?> entry, String fileName) {
        if (entry.getValue() instanceof Integer) {
            ResourceConfig.NETWORK.send(new SyncIntegerConfigEntryPacket(fileName, entry.getPath(), (Integer) entry.value()), PacketDistributor.PLAYER.with(player));
        }
        if (entry.getValue() instanceof Double) {
            ResourceConfig.NETWORK.send(new SyncDoubleConfigEntryPacket(fileName, entry.getPath(), (Double) entry.value()), PacketDistributor.PLAYER.with(player));
        }
        if (entry.getValue() instanceof String) {
            ResourceConfig.NETWORK.send(new SyncStringConfigEntryPacket(fileName, entry.getPath(), (String) entry.value()), PacketDistributor.PLAYER.with(player));
        }
        if (entry.getValue() instanceof Boolean) {
            ResourceConfig.NETWORK.send(new SyncBooleanConfigEntryPacket(fileName, entry.getPath(), (Boolean) entry.value()), PacketDistributor.PLAYER.with(player));
        }
    }
}
