package net.xstopho.resourceconfigapi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfigFile;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;
import net.xstopho.resourceconfigapi.network.packet.SyncBooleanConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packet.SyncDoubleConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packet.SyncIntegerConfigEntryPacket;
import net.xstopho.resourceconfigapi.network.packet.SyncStringConfigEntryPacket;

import java.util.HashMap;
import java.util.Map;

public class ResourceConfig implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigNetwork.initServer();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ResourceConfigConstants.LOG.info("Syncing Config Values with Client");

            HashMap<String, ModConfigFile> configs = ConfigRegistry.getConfigFiles();

            for (Map.Entry<String, ModConfigFile> config : configs.entrySet()) {
                ResourceConfigConstants.LOG.info("Syncing Values for ModConfigFile: " + config.getKey());
                ModConfigFile modConfig = config.getValue();

                for (ConfigEntry<?> entry : modConfig.getBuilder().getEntries().values()) {
                    if (entry.syncWithServer()) {
                        sendPacket(sender, entry, config.getKey());
                    }
                }
            }
        });
    }

    public void sendPacket(PacketSender sender, ConfigEntry<?> entry, String fileName) {
        if (entry.getValue() instanceof Integer) {
            sender.sendPacket(new SyncIntegerConfigEntryPacket(fileName, entry.getPath(), (Integer) entry.value()));
        }
        if (entry.getValue() instanceof Double) {
            sender.sendPacket(new SyncDoubleConfigEntryPacket(fileName, entry.getPath(), (Double) entry.value()));
        }
        if (entry.getValue() instanceof String) {
            sender.sendPacket(new SyncStringConfigEntryPacket(fileName, entry.getPath(), (String) entry.value()));
        }
        if (entry.getValue() instanceof Boolean) {
            sender.sendPacket(new SyncBooleanConfigEntryPacket(fileName, entry.getPath(), (Boolean) entry.value()));
        }
    }
}
