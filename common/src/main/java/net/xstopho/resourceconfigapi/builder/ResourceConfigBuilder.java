package net.xstopho.resourceconfigapi.builder;

import net.xstopho.resourceconfigapi.config.values.primitive.BooleanConfigValue;
import net.xstopho.resourceconfigapi.config.values.primitive.DoubleConfigValue;
import net.xstopho.resourceconfigapi.config.values.primitive.IntegerConfigValue;
import net.xstopho.resourceconfigapi.config.values.primitive.StringConfigValue;
import net.xstopho.resourceconfigapi.config.values.reference.ArrayListConfigValue;
import net.xstopho.resourceconfigapi.config.values.reference.ListConfigValue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ResourceConfigBuilder extends ResourceConfigBuilderBase {
    @Override
    public Supplier<Boolean> define(String key, Boolean defaultValue) {
        return createEntry(createKey(key), new BooleanConfigValue(defaultValue, getComment()));
    }

    @Override
    public Supplier<Integer> define(String key, Integer defaultValue) {
        return createEntry(createKey(key), new IntegerConfigValue(defaultValue, getComment()));
    }

    @Override
    public Supplier<Integer> defineInRange(String key, Integer defaultValue, int min, int max) {
        return createEntry(createKey(key), new IntegerConfigValue(defaultValue, getComment(), min, max));
    }

    @Override
    public Supplier<Double> define(String key, Double defaultValue) {
        return createEntry(createKey(key), new DoubleConfigValue(defaultValue, getComment()));
    }

    @Override
    public Supplier<Double> defineInRange(String key, Double defaultValue, double min, double max) {
        return createEntry(createKey(key), new DoubleConfigValue(defaultValue, getComment(), min, max));
    }

    @Override
    public Supplier<String> define(String key, String defaultValue) {
        return createEntry(createKey(key), new StringConfigValue(defaultValue, getComment()));
    }

    @Override
    public Supplier<String> defineInRange(String key, String defaultValue, int minLength, int maxLength) {
        return createEntry(createKey(key), new StringConfigValue(defaultValue, getComment(), minLength, maxLength));
    }

    @Override
    public <T> Supplier<List<T>> define(String key, List<T> defaultList) {
        return createEntry(createKey(key), new ListConfigValue<>(defaultList, getComment()));
    }

    @Override
    public <T> Supplier<ArrayList<T>> define(String key, ArrayList<T> defaultList) {
        return createEntry(createKey(key), new ArrayListConfigValue<>(defaultList, getComment()));
    }
}
