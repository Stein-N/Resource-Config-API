package net.xstopho.resourceconfigapi.config.builder;

import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.config.value.primitiv.*;
import net.xstopho.resourceconfigapi.config.value.reference.EnumConfigValue;
import net.xstopho.resourceconfigapi.config.value.reference.ListConfigValue;

import java.util.List;

public class ResourceConfigBuilder extends ResourceConfigBuilderBase {
    @Override
    public ConfigEntry<Integer> define(String key, Integer defaultValue) {
        return createEntry(createKey(key), new IntegerConfigValue(defaultValue));
    }

    @Override
    public ConfigEntry<Integer> defineInRange(String key, Integer defaultValue, Integer min, Integer max) {
        return createEntry(createKey(key), new IntegerConfigValue(defaultValue, min, max));
    }

    @Override
    public ConfigEntry<Long> define(String key, Long defaultValue) {
        return createEntry(createKey(key), new LongConfigValue(defaultValue));
    }

    @Override
    public ConfigEntry<Long> defineInRange(String key, Long defaultValue, Long min, Long max) {
        return createEntry(createKey(key), new LongConfigValue(defaultValue, min, max));
    }

    @Override
    public ConfigEntry<Short> define(String key, Short defaultValue) {
        return createEntry(createKey(key), new ShortConfigValue(defaultValue));
    }

    @Override
    public ConfigEntry<Short> defineInRange(String key, Short defaultValue, Short min, Short max) {
        return createEntry(createKey(key), new ShortConfigValue(defaultValue, min, max));
    }

    @Override
    public ConfigEntry<Double> define(String key, Double defaultValue) {
        return createEntry(createKey(key), new DoubleConfigValue(defaultValue));
    }

    @Override
    public ConfigEntry<Double> defineInRange(String key, Double defaultValue, Double min, Double max) {
        return createEntry(createKey(key), new DoubleConfigValue(defaultValue, min, max));
    }

    @Override
    public ConfigEntry<Float> define(String key, Float defaultValue) {
        return createEntry(createKey(key), new FloatConfigValue(defaultValue));
    }

    @Override
    public ConfigEntry<Float> defineInRange(String key, Float defaultValue, Float min, Float max) {
        return createEntry(createKey(key), new FloatConfigValue(defaultValue, min, max));
    }

    @Override
    public ConfigEntry<Byte> define(String key, Byte defaultValue) {
        return createEntry(createKey(key), new ByteConfigValue(defaultValue));
    }

    @Override
    public ConfigEntry<Byte> defineInRange(String key, Byte defaultValue, Byte min, Byte max) {
        return createEntry(createKey(key), new ByteConfigValue(defaultValue));
    }

    @Override
    public ConfigEntry<Boolean> define(String key, Boolean defaultValue) {
        return createEntry(createKey(key), new BooleanConfigValue(defaultValue));
    }

    @Override
    public ConfigEntry<String> define(String key, String defaultValue) {
        return createEntry(createKey(key), new StringConfigValue(defaultValue));
    }

    @Override
    public ConfigEntry<Character> define(String key, Character defaultValue) {
        return createEntry(createKey(key), new CharacterConfigValue(defaultValue));
    }

    @Override
    public <T extends Enum<T>> ConfigEntry<T> define(String key, T defaultValue) {
        return createEntry(createKey(key), new EnumConfigValue<>(defaultValue));
    }

    @Override
    public <T> ConfigEntry<List<T>> define(String key, List<T> defaultValue) {
        return createEntry(createKey(key), new ListConfigValue<>(defaultValue));
    }
}
