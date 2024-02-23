package net.xstopho.resource_config_api.api;

import net.xstopho.resource_config_api.builder.IConfigBuilder;
import net.xstopho.resource_config_api.config.ModConfig;
import net.xstopho.resource_config_api.platform.Services;

import java.nio.file.Path;

public class ConfigRegistry {

    public static void register(String modId, String fileName, IConfigBuilder builder, Path path) {
        new ModConfig(modId, fileName, builder, path);
    }

    public static void register(String modId, String fileName, IConfigBuilder builder) {
        new ModConfig(modId, fileName, builder, Services.PLATFORM.getConfigDir());
    }

    public static void register(String modId, IConfigBuilder builder, Path path) {
        new ModConfig(modId, modId, builder, path);
    }

    public static void register(String modId, IConfigBuilder builder) {
        new ModConfig(modId, modId, builder, Services.PLATFORM.getConfigDir());
    }

}
