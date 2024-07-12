package net.xstopho.resourceconfigapi.api;


import net.xstopho.resourceconfigapi.builder.IConfigBuilder;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.platform.Services;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class ConfigRegistry {

    public static final List<ModConfig> MOD_CONFIGS = new LinkedList<>();

    public static void register(String modId, String fileName, IConfigBuilder builder, Path path) {
        MOD_CONFIGS.add(new ModConfig(modId, fileName, builder, path));
    }

    public static void register(String modId, String fileName, IConfigBuilder builder) {
        MOD_CONFIGS.add(new ModConfig(modId, fileName, builder, Services.CONFIG_DIR));
    }

    public static void register(String modId, IConfigBuilder builder, Path path) {
        MOD_CONFIGS.add(new ModConfig(modId, modId, builder, path));
    }

    public static void register(String modId, IConfigBuilder builder) {
        MOD_CONFIGS.add(new ModConfig(modId, modId, builder, Services.CONFIG_DIR));
    }
}
