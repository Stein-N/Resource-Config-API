package net.xstopho.resourceconfigapi.api;

import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.config.builder.ResourceConfigBuilder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigRegistry {
    private static final Map<String, Map<ConfigType, ResourceModConfig>> MOD_CONFIG_FILES = new LinkedHashMap<>();

    public static ResourceModConfig register(String modId, ConfigType type, ResourceConfigBuilder builder) {
        checkConfigType(modId, type);
        ResourceModConfig config = new ResourceModConfig(modId, builder, type);
        addConfig(modId, type, config);

        return config;
    }

    public static Map<String, Map<ConfigType, ResourceModConfig>> getModConfigFiles() {
        return MOD_CONFIG_FILES;
    }

    private static void checkConfigType(String modId, ConfigType type) {
        Map<ConfigType, ResourceModConfig> modConfigMap = MOD_CONFIG_FILES.get(modId);
        if (modConfigMap != null && modConfigMap.containsKey(type)) {
            throw new IllegalStateException("You already registered an Config File from type '" + type + "'");
        }
    }

    private static void addConfig(String modId, ConfigType type, ResourceModConfig config) {
        Map<ConfigType, ResourceModConfig> configMap = MOD_CONFIG_FILES.containsKey(modId) ? MOD_CONFIG_FILES.get(modId) : new HashMap<>();
        configMap.put(type, config);

        MOD_CONFIG_FILES.put(modId, configMap);
    }
}
