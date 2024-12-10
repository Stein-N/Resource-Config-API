package net.xstopho.resourceconfigapi.api;

import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.config.ModConfig;

import java.util.HashMap;
import java.util.Map;

public class ConfigRegistry {

    public static final Map<String, ModConfig> CONFIGS = new HashMap<>();

    public static ModConfig register(Class<?> clazz, String modId) {
        if (clazz.isAnnotationPresent(Config.class)) {
            Config config = clazz.getAnnotation(Config.class);

            Constants.LOG.info("Register config '{}' for mod '{}'", config.fileName(), modId);
            return CONFIGS.put(config.fileName(), new ModConfig(clazz, config.type(), modId));
        }
        throw new IllegalArgumentException("You try to register an class that isn't flagged as a Config!");
    }
}
