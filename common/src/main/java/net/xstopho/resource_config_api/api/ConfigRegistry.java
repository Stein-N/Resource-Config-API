package net.xstopho.resource_config_api.api;

import net.xstopho.resource_config_api.builder.IConfigBuilder;
import net.xstopho.resource_config_api.config.ModConfig;
import net.xstopho.resource_config_api.platform.Services;

public class ConfigRegistry {

    public static void register(String modId, String fileName, IConfigBuilder builder, String filePath) {
        new ModConfig(modId, fileName, builder, filePath);
    }

    public static void register(String modId, String fileName, IConfigBuilder builder) {
        register(modId, fileName, builder, Services.PLATFORM.getConfigDir().toString());
    }

    public static void register(String modId, IConfigBuilder builder, String filePath) {
        register(modId, modId, builder, filePath);
    }

    public static void register(String modId, IConfigBuilder builder) {
        register(modId, modId, builder, Services.PLATFORM.getConfigDir().toString());
    }

}
