package net.xstopho.resourceconfigapi.toml;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TomlHelper {

    public static boolean isPrimitive(Object object) {
        Class<?> clazz = object.getClass();
        return clazz.isPrimitive() || clazz == Integer.class ||
                clazz == Long.class || clazz == Short.class ||
                clazz == Double.class || clazz == Float.class ||
                clazz == Boolean.class || clazz == Byte.class ||
                clazz == Character.class || clazz == String.class;
    }

    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == Integer.class ||
                clazz == Long.class || clazz == Short.class ||
                clazz == Double.class || clazz == Float.class ||
                clazz == Boolean.class || clazz == Byte.class ||
                clazz == Character.class || clazz == String.class;
    }

    public static String convertToString(Object object) {
        if (object.toString().matches("^\\d+(.\\d+)?$")) return object.toString();
        return switch (object) {
            case Character ignored -> "'" + object + "'";
            case String ignored -> "\"" + object + "\"";
            case Enum<?> ignored -> "\"" + object + "\"";
            default -> object.toString();
        };
    }

    public static Object convertToPrimitive(String value, Class<?> type) {
        if (type == int.class || type == Integer.class) return Integer.parseInt(value);
        if (type == long.class || type == Long.class) return Long.parseLong(value);
        if (type == short.class || type == Short.class) return Short.parseShort(value);
        if (type == double.class || type == Double.class) return Double.parseDouble(value);
        if (type == float.class || type == Float.class) return Float.parseFloat(value);
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
        if (type == byte.class || type == Byte.class) return Byte.parseByte(value);
        if (type == char.class || type == Character.class) return value.charAt(0);
        return value;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T convertToEnum(String value, Class<?> clazz) {
        return Enum.valueOf((Class<T>) clazz, value);
    }

    public static <T> T convertToObject(String value, Function<String, T> converter) {
        return converter.apply(value);
    }

    public static boolean isEnum(Object object) {
        return object instanceof Enum<?>;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> arrayToList(Object object) {
        if (object instanceof Object[]) {
            return Arrays.asList((T[]) object);
        } else {
            int length = Array.getLength(object);
            List<T> list = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                list.add((T) Array.get(object, i));
            }
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] listToArray(Object object, Class<T> clazz) {
        if (object instanceof List<?> list) {
            if (isPrimitive(clazz)) {
                Object[] array = new Object[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    array[i] = convertToPrimitive(list.get(i).toString(), clazz);
                }
                return (T[]) array;
            } else {
                return (T[]) list.toArray();
            }
        } else throw new IllegalStateException("The given Object isn't any type of Collection!");
    }
}