package net.xstopho.resource_config_api.builder;

import net.xstopho.resource_config_api.config.ConfigEntry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface IConfigBuilder {

    Map<String, ConfigEntry<?>> getEntries();
    Map<String, String> getCategoryComments();

    IConfigBuilder push(String category);
    IConfigBuilder pop();
    IConfigBuilder comment(String comment);

    Supplier<Boolean> define(String key, boolean defaultValue);

    Supplier<Integer> define(String key, int defaultValue);
    Supplier<Integer> defineInRange(String key, int defaultValue, int min, int max);

    Supplier<Double> define(String key, double defaultValue);
    Supplier<Double> defineInRange(String key, double defaultValue, double min, double max);

    Supplier<String> define(String key, String defaultValue);
    Supplier<String> defineInRange(String key, String defaultValue, int minLength, int maxLength);

    <T> Supplier<List<T>> define(String key, List<T> defaultList);
    <T> Supplier<ArrayList<T>> define(String key, ArrayList<T> defaultList);
}
