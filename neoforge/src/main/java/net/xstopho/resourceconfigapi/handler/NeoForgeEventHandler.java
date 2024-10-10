package net.xstopho.resourceconfigapi.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;

@EventBusSubscriber(modid = ResourceConfigConstants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NeoForgeEventHandler {

    @SubscribeEvent
    public static void registerLoginEvents(PlayerEvent.PlayerLoggedInEvent event) {
//        ResourceConfigConstants.LOG.info("Syncing Config Values with Client");
//        HashMap<String, ResourceModConfig> configs = ConfigRegistry.getConfigFiles();
//
//        for (Map.Entry<String, ResourceModConfig> config : configs.entrySet()) {
//            ResourceConfigConstants.LOG.info("Syncing Values for ModConfigFile: {}", config.getKey());
//            ResourceModConfig modConfig = config.getValue();
//
//            for (ConfigEntry<?> entry : modConfig.getBuilder().getEntries().values()) {
//                if (entry.syncWithServer()) {
//                    sendPacket((ServerPlayer) event.getEntity(), entry, config.getKey());
//                }
//            }
//        }
    }

//    public static void sendPacket(ServerPlayer player, ConfigEntry<?> entry, String fileName) {
//        if (entry.getValue() instanceof Integer) {
//            PacketDistributor.sendToPlayer(player, new SyncIntegerConfigEntryPacket(fileName, entry.getPath(), (Integer) entry.value()));
//        }
//        if (entry.getValue() instanceof Double) {
//            PacketDistributor.sendToPlayer(player, new SyncDoubleConfigEntryPacket(fileName, entry.getPath(), (Double) entry.value()));
//        }
//        if (entry.getValue() instanceof String) {
//            PacketDistributor.sendToPlayer(player, new SyncStringConfigEntryPacket(fileName, entry.getPath(), (String) entry.value()));
//        }
//        if (entry.getValue() instanceof Boolean) {
//            PacketDistributor.sendToPlayer(player, new SyncBooleanConfigEntryPacket(fileName, entry.getPath(), (Boolean) entry.value()));
//        }
//    }
}
