package net.xstopho.resourceconfigapi.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class ConfigUtils {

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
