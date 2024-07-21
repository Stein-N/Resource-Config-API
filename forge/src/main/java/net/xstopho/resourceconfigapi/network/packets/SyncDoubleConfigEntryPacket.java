package net.xstopho.resourceconfigapi.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.builder.IResourceConfigBuilder;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;

import java.util.HashMap;

public class SyncDoubleConfigEntryPacket {

    private final String fileName;
    private final String path;
    private final double value;

    public SyncDoubleConfigEntryPacket(String fileName, String path, double value) {
        this.fileName = fileName;
        this.path = path;
        this.value = value;
    }

    public static SyncDoubleConfigEntryPacket decode(FriendlyByteBuf buf) {
        return new SyncDoubleConfigEntryPacket(buf.readUtf(), buf.readUtf(), buf.readDouble());
    }

    public static void encode(SyncDoubleConfigEntryPacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.fileName);
        buf.writeUtf(packet.path);
        buf.writeDouble(packet.value);
    }

    public static void handle(SyncDoubleConfigEntryPacket packet, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            HashMap<String, ResourceModConfig> configs = ConfigRegistry.getConfigFiles();

            if (configs.containsKey(packet.fileName)) {
                ResourceModConfig config = configs.get(packet.fileName);

                IResourceConfigBuilder builder = config.getBuilder();

                for (ConfigEntry<?> entry : builder.getEntries().values()) {
                    if (entry.getPath().equals(packet.path) && entry.syncWithServer()) {
                        ((ConfigEntry<Double>) entry).setServerValue(packet.value);
                        entry.setSynced();
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
