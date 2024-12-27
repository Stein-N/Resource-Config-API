package net.xstopho.resourceconfigapi.util;

import net.minecraft.network.chat.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class ConfigUtils {

    public static Component createTitle(String key) {
        return Component.translatable("config." + convertKey(key) + ".title");
    }

    public static Component createConfigLabel(String modId, String fileName) {
        return Component.translatable("config." + modId + "." + fileName);
    }

    public static Component createModLabel(String modId, String key) {
        return createLabel(modId + "." + key);
    }

    public static Component createLabel(String key) {
        return Component.translatable("config." + convertKey(key) + ".label");
    }

    public static Component createModTooltip(String modId, String key) {
        return createTooltip(modId + "." + key);
    }

    public static Component createTooltip(String key) {
        return Component.translatable("config." + convertKey(key) + ".tooltip");
    }

    private static String convertKey(String key) {
        return key.toLowerCase().replace(" ", "_");
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
}
