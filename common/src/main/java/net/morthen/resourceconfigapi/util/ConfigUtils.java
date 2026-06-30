package net.morthen.resourceconfigapi.util;

import net.morthen.resourceconfigapi.ConfigConstants;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;

public class ConfigUtils {

    public static boolean isNotEmpty(String string) {
        return string != null && !string.isBlank();
    }

    public static boolean unsupportedDatatype(Field field) {
        try {
            Object value = field.get(null);
            if (value instanceof Collection<?> || value instanceof Map<?,?>) {
                return true;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        ConfigConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
