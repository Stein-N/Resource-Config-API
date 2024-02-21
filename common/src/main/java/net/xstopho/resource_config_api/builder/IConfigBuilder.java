package net.xstopho.resource_config_api.builder;

import net.xstopho.resource_config_api.config.ConfigEntry;

import java.util.Map;
import java.util.function.Supplier;

public interface IConfigBuilder {

    Map<String, ConfigEntry<?>> getEntries();
    Map<String, String> getCategoryComments();

    IConfigBuilder push(String category);
    IConfigBuilder pop();
    IConfigBuilder comment(String comment);

    Supplier<Boolean> define(String key, boolean defaultValue);

    Supplier<Byte> define(String key, byte defaultValue);
    Supplier<Byte> defineInRange(String key, byte defaultValue, byte min, byte max);

    Supplier<Short> define(String key, short defaultValue);
    Supplier<Short> defineInRange(String key, short defaultValue, short min, short max);

    Supplier<Integer> define(String key, int defaultValue);
    Supplier<Integer> defineInRange(String key, int defaultValue, int min, int max);

    // Might be removed because Toml save them wrong
    Supplier<Long> define(String key, long defaultValue);
    Supplier<Long> defineInRange(String key, long defaultValue, long min, long max);

    Supplier<Double> define(String key, double defaultValue);
    Supplier<Double> defineInRange(String key, double defaultValue, double min, double max);

    Supplier<Character> define(String key, Character defaultValue);

    Supplier<String> define(String key, String defaultValue);
    Supplier<String> defineInRange(String key, String defaultValue, int minLength, int maxLength);
}
