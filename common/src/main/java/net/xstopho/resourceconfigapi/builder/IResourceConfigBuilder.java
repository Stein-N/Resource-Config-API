package net.xstopho.resourceconfigapi.builder;

import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface IResourceConfigBuilder {

    Map<String, ConfigEntry<?>> getEntries();
    Map<String, String> getCategoryComment();

    IResourceConfigBuilder push(String category);
    IResourceConfigBuilder pop();
    IResourceConfigBuilder comment(String comment);
    IResourceConfigBuilder translation(String key);
    IResourceConfigBuilder sync();

    Supplier<Boolean> define(String key, Boolean defaultValue);

    Supplier<Integer> define(String key, Integer defaultValue);
    Supplier<Integer> defineInRange(String key, Integer defaultValue, int min, int max);

    Supplier<Double> define(String key, Double defaultValue);
    Supplier<Double> defineInRange(String key, Double defaultValue, double min, double max);

    Supplier<String> define(String key, String defaultValue);
    Supplier<String> defineInRange(String key, String defaultValue, int minLength, int maxLength);

    <T> Supplier<List<T>> define(String key, List<T> defaultList);
    <T> Supplier<ArrayList<T>> define(String key, ArrayList<T> defaultList);

    default boolean isEmpty(String string) {
        return string == null || string.isEmpty() || string.isBlank();
    }
}
