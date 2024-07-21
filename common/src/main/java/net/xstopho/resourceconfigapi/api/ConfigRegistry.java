package net.xstopho.resourceconfigapi.api;


import net.xstopho.resourceconfigapi.builder.IResourceConfigBuilder;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.platform.Services;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ConfigRegistry {

    private static final HashMap<String, ResourceModConfig> MOD_CONFIG_FILES = new LinkedHashMap<>();

    public static ResourceModConfig register(String modId, String fileName, IResourceConfigBuilder builder, String folderName, boolean disableRangedComments) {
        validateBuilder(builder);
        ResourceModConfig config = new ResourceModConfig(modId, fileName, builder, Path.of(Services.getConfigPath() + "/" + folderName), disableRangedComments);
        MOD_CONFIG_FILES.put(fileName, config);
        return config;
    }

    public static ResourceModConfig register(String modId, IResourceConfigBuilder builder, String folderName, boolean disableRangedComments) {
        validateBuilder(builder);
        ResourceModConfig config = new ResourceModConfig(modId, modId, builder, Path.of(Services.getConfigPath() + "/" + folderName), disableRangedComments);
        MOD_CONFIG_FILES.put(modId, config);
        return config;
    }

    public static ResourceModConfig register(String modId, String fileName, IResourceConfigBuilder builder, boolean disableRangedComments) {
        validateBuilder(builder);
        ResourceModConfig config = new ResourceModConfig(modId, fileName, builder, Services.getConfigPath(), disableRangedComments);
        MOD_CONFIG_FILES.put(fileName, config);
        return config;
    }

    public static ResourceModConfig register(String modId, IResourceConfigBuilder builder, boolean disableRangedComments) {
        validateBuilder(builder);
        ResourceModConfig config = new ResourceModConfig(modId, modId, builder, Services.getConfigPath(), disableRangedComments);
        MOD_CONFIG_FILES.put(modId, config);
        return config;
    }

    public static HashMap<String, ResourceModConfig> getConfigFiles() {
        return MOD_CONFIG_FILES;
    }

    private static void validateBuilder(IResourceConfigBuilder builder) {
        for (ResourceModConfig configFile : MOD_CONFIG_FILES.values()) {
            if (configFile.getBuilder().equals(builder)) {
                throw new IllegalStateException("You try to register the same ResourceConfigBuilder twice!");
            }
        }
    }
}
