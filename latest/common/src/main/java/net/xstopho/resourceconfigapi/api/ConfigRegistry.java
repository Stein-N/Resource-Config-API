package net.xstopho.resourceconfigapi.api;

import net.minecraft.resources.Identifier;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.config.ModConfig;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ConfigRegistry {

    private static final Map<Identifier, ModConfig> CONFIGS = new HashMap<>();

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
        Identifier config = of(modId, annotation.type(), annotation.fileName());

        return CONFIGS.putIfAbsent(config, new ModConfig(clazz, annotation.type(), modId));
    }

    public static ModConfig getConfig(Identifier configLocation) {
        return CONFIGS.get(configLocation);
    }

    public static boolean contains(Identifier configLocation) {
        return CONFIGS.containsKey(configLocation);
    }

    public static Map<Identifier, ModConfig> getConfigs() {
        return CONFIGS;
    }

    public static Set<Map.Entry<Identifier, ModConfig>> getConfigEntries() {
        return getConfigs().entrySet();
    }

    private static Identifier of(String modId, ConfigType type, String fileName) {
        return Identifier.fromNamespaceAndPath(modId, type.name().toLowerCase(Locale.ENGLISH) + "/" + fileName);
    }
}
