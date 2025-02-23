package net.xstopho.resourceconfigapi;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigConstants {

	public static final String MOD_ID = "resourceconfigapi";
	public static final String MOD_NAME = "Resource Config API";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation of(String id) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
	}

	public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> type(String id) {
		return new CustomPacketPayload.Type<>(ConfigConstants.of(id));
	}
}