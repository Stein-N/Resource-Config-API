package net.xstopho.resourceconfigapi.api;

import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.config.ModConfig;

import java.util.HashMap;
import java.util.Map;

public class ConfigRegistry {

    public static final Map<ResourceLocation, ModConfig> CONFIGS = new HashMap<>();

    public static ModConfig register(Class<?> clazz, String modId) {
        if (clazz.isAnnotationPresent(Config.class)) {
            Config config = clazz.getAnnotation(Config.class);
            ResourceLocation configLoc = of(modId, config.type(), config.fileName());

            //Constants.LOG.info("Register config '{}' for mod '{}'", config.fileName(), modId);
            if (!CONFIGS.containsKey(configLoc)) {
                return CONFIGS.put(configLoc, new ModConfig(clazz, config.type(), modId));
            } else {
                throw new IllegalStateException("You try to register " + configLoc + " twice");
            }
        }
        throw new IllegalArgumentException("You try to register an class that isn't flagged as a Config!");
    }

    public static ResourceLocation of(String modId, ConfigType type, String fileName) {
        return ResourceLocation.fromNamespaceAndPath(modId, type.name().toLowerCase() + "/" + fileName);
    }
}
