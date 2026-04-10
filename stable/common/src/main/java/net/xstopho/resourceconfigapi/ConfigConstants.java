package net.xstopho.resourceconfigapi;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;
import net.xstopho.resourceconfigapi.network.payloads.ConfigSyncPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ConfigConstants {

	public static final String MOD_ID = "resourceconfigapi";
	public static final String MOD_NAME = "Resource Config API";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static Identifier of(String id) {
		return Identifier.fromNamespaceAndPath(MOD_ID, id);
	}

	public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> type(String id) {
		return new CustomPacketPayload.Type<>(ConfigConstants.of(id));
	}

	public static void syncConfigs(ServerPlayer player) {
		ConfigConstants.LOG.info("Syncing Server Configs with Client");
		for (Map.Entry<Identifier, ModConfig> entry : ConfigRegistry.getConfigEntries()) {
			Identifier location = entry.getKey();
			ModConfig config = entry.getValue();

			if (location.toString().contains("client")) continue;
			ConfigConstants.LOG.info("Sending data for Config '{}'", location);
			ConfigNetwork.INSTANCE.sendToClient(player, new ConfigSyncPayload(location.toString(), config.toJson().toString()));
		}
	}
}