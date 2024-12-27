package net.xstopho.resourceconfigapi.api;

import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.config.ModConfig;

import java.util.HashMap;
import java.util.Map;

public class ConfigRegistry {

    public static final Map<ResourceLocation, ModConfig> CONFIGS = new HashMap<>();

    public static void register(Class<?> clazz, String modId) {
        if (!clazz.isAnnotationPresent(Config.class)) {
            Constants.LOG.error("You try to register '{}', this class isn't flagged as a Config and was skipped!", clazz.getName());
            return;
        }

        Config annotation = clazz.getAnnotation(Config.class);
        ResourceLocation config = of(modId, annotation.type(), annotation.fileName());

        CONFIGS.putIfAbsent(config, new ModConfig(clazz, annotation.type(), modId));
    }

    public static ResourceLocation of(String modId, ConfigType type, String fileName) {
        return ResourceLocation.fromNamespaceAndPath(modId, type.name().toLowerCase() + "/" + fileName);
    }
}
