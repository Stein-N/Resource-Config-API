package net.xstopho.resourceconfigapi.config.builder;

import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IResourceConfigBuilder extends Serializable {

    Map<String, ConfigEntry<?>> getEntries();

    IResourceConfigBuilder push(String category);
    IResourceConfigBuilder pop();
    IResourceConfigBuilder sync();

    ConfigEntry<Integer> define(String key, Integer defaultValue);
    ConfigEntry<Integer> defineInRange(String key, Integer defaultValue, Integer min, Integer max);

    ConfigEntry<Long> define(String key, Long defaultValue);
    ConfigEntry<Long> defineInRange(String key, Long defaultValue, Long min, Long max);

    ConfigEntry<Short> define(String key, Short defaultValue);
    ConfigEntry<Short> defineInRange(String key, Short defaultValue, Short min, Short max);

    ConfigEntry<Double> define(String key, Double defaultValue);
    ConfigEntry<Double> defineInRange(String key, Double defaultValue, Double min, Double max);

    ConfigEntry<Float> define(String key, Float defaultValue);
    ConfigEntry<Float> defineInRange(String key, Float defaultValue, Float min, Float max);

    ConfigEntry<Byte> define(String key, Byte defaultValue);
    ConfigEntry<Byte> defineInRange(String key, Byte defaultValue, Byte min, Byte max);

    ConfigEntry<Boolean> define(String key, Boolean defaultValue);

    ConfigEntry<String> define(String key, String defaultValue);

    ConfigEntry<Character> define(String key, Character defaultValue);

    <T extends Enum<T>> ConfigEntry<Enum<T>> define(String key, Enum<T> defaultValue);
    <T> ConfigEntry<List<T>> define(String key, List<T> defaultValue);

    ConfigEntry<Integer[]> define(String key, Integer[] defaultValue);
    ConfigEntry<Long[]> define(String key, Long[] defaultValue);
    ConfigEntry<Short[]> define(String key, Short[] defaultValue);
    ConfigEntry<Double[]> define(String key, Double[] defaultValue);
    ConfigEntry<Float[]> define(String key, Float[] defaultValue);
    ConfigEntry<Byte[]> define(String key, Byte[] defaultValue);
    ConfigEntry<Boolean[]> define(String key, Boolean[] defaultValue);
    ConfigEntry<String[]> define(String key, String[] defaultValue);
    ConfigEntry<Character[]> define(String key, Character[] defaultValue);
    <T extends Enum<T>> ConfigEntry<Enum<T>[]> define(String key, Enum<T>[] defaultValue);

    default boolean isEmpty(String string) {
        return string == null || string.isEmpty() || string.isBlank();
    }
}
