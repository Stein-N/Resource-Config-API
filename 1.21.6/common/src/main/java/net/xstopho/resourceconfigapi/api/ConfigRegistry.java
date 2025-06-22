package net.xstopho.resourceconfigapi.api;

import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.config.ModConfig;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ConfigRegistry {

    private static final Map<ResourceLocation, ModConfig> CONFIGS = new HashMap<>();

    /**
     * Registers the Config class for the given Mod ID. <br>
     * If the Class doesn't have the {@link Config} annotation, the config class gets
     * rejected but doesn't throw an Exception
     * @param clazz Config class with {@link Config} annotation
     * @param modId Mod id
     */
    public static ModConfig register(Class<?> clazz, String modId) {
        if (!clazz.isAnnotationPresent(Config.class)) {
            String error = "You try to register '%s', this class isn't flagged as a Config and was skipped!";
            throw new IllegalArgumentException(String.format(error, clazz.getName()));
        }

        Config annotation = clazz.getAnnotation(Config.class);
        ResourceLocation config = of(modId, annotation.type(), annotation.fileName());

        return CONFIGS.putIfAbsent(config, new ModConfig(clazz, annotation.type(), modId));
    }

    public static ModConfig getConfig(ResourceLocation configLocation) {
        return CONFIGS.get(configLocation);
    }

    public static boolean contains(ResourceLocation configLocation) {
        return CONFIGS.containsKey(configLocation);
    }

    public static Map<ResourceLocation, ModConfig> getConfigs() {
        return CONFIGS;
    }

    public static Set<Map.Entry<ResourceLocation, ModConfig>> getConfigEntries() {
        return getConfigs().entrySet();
    }

    private static ResourceLocation of(String modId, ConfigType type, String fileName) {
        return ResourceLocation.fromNamespaceAndPath(modId, type.name().toLowerCase(Locale.ENGLISH) + "/" + fileName);
    }
}
