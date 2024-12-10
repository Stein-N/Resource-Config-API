package net.xstopho.resourceconfigapi.api;

import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.util.ConfigType;

public class ConfigRegistry {

    public static ModConfig register(Class<?> clazz, ConfigType type, String modId) {
        if (clazz.isAnnotationPresent(Config.class)) {
            Config config = clazz.getAnnotation(Config.class);

            ResourceConfigConstants.LOG.info("Register config '{}' for mod '{}'", config.fileName(), modId);
            return new ModConfig(clazz, type, modId);
        }
        throw new IllegalArgumentException("You try to register an class that isn't flagged as a Config!");
    }
}
