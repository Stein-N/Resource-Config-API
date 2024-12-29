package net.xstopho.resourceconfigapi;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	//TODO: - try to only send config updates when a config is really changed
	//      - porting every Payload to forge and neoforge

	public static final String MOD_ID = "resourceconfigapi";
	public static final String MOD_NAME = "Resource Config API";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation of(String id) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
	}
}