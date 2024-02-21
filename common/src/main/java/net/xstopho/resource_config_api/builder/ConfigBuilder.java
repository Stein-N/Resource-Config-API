package net.xstopho.resource_config_api.builder;

import net.xstopho.resource_config_api.config.*;
import net.xstopho.resource_config_api.values.primitive.BooleanConfigValue;
import net.xstopho.resource_config_api.values.primitive.DoubleConfigValue;
import net.xstopho.resource_config_api.values.primitive.IntegerConfigValue;
import net.xstopho.resource_config_api.values.primitive.StringConfigValue;
import net.xstopho.resource_config_api.values.reference.ListConfigValue;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ConfigBuilder extends BaseBuilder {

    @Override
    public Map<String, ConfigEntry<?>> getEntries() {
        return this.entries;
    }

    /**
     * @return is used by the {@link ModConfig} to set the CategoryComments
     */
    @Override
    public Map<String, String> getCategoryComments() {
        return this.categoryComments;
    }

    /**
     * @param key Key where the defined Value is saved in the .toml File
     * @param defaultValue The defaultValue gets used when the config file get initialised the first Time or
     *                     when the Value in the .toml file is corrupt.
     * @return returns a {@link Supplier} of Type {@link Boolean}
     */
    @Override
    public Supplier<Boolean> define(String key, boolean defaultValue) {
        return addEntry(createKey(key), new BooleanConfigValue(defaultValue, this.comment));
    }

    /**
     * @param key Key where the defined Value is saved in the .toml File
     * @param defaultValue The defaultValue gets used when the config file get initialised the first Time or
     *                     when the Value in the .toml file is corrupt.
     * @return returns a {@link Supplier} of Type {@link Integer}
     */
    @Override
    public Supplier<Integer> define(String key, int defaultValue) {
        return addEntry(createKey(key), new IntegerConfigValue(defaultValue, this.comment));
    }

    /**
     *
     * @param key Key where the defined Value is saved in the .toml File
     * @param defaultValue The defaultValue gets used when the config file get initialised the
     *                     first Time or when the Value in the .toml file is corrupt.
     * @param min Sets the minimal Value for the Config File
     * @param max Sets the maximal Value for the Config File
     * @return returns a {@link Supplier} of Type {@link Integer} <br>
     *         If the Config Value is out of Bounds, it gets reset to its Default Value when the
     *         config get initialized the next Time
     */
    @Override
    public Supplier<Integer> defineInRange(String key, int defaultValue, int min, int max) {
        return addEntry(createKey(key), new IntegerConfigValue(defaultValue, min, max, this.comment));
    }

    /**
     * @param key Key where the defined Value is saved in the .toml File
     * @param defaultValue The defaultValue gets used when the config file get initialised the first Time or
     *                     when the Value in the .toml file is corrupt.
     * @return returns a {@link Supplier} of Type {@link Double}
     */
    @Override
    public Supplier<Double> define(String key, double defaultValue) {
        return addEntry(createKey(key), new DoubleConfigValue(defaultValue, this.comment));
    }

    /**
     *
     * @param key Key where the defined Value is saved in the .toml File
     * @param defaultValue The defaultValue gets used when the config file get initialised the
     *                     first Time or when the Value in the .toml file is corrupt.
     * @param min Sets the minimal Value for the Config File
     * @param max Sets the maximal Value for the Config File
     * @return returns a {@link Supplier} of Type {@link Double} <br>
     *         If the Config Value is out of Bounds, it gets reset to its Default Value when the
     *         config get initialized the next Time.
     */
    @Override
    public Supplier<Double> defineInRange(String key, double defaultValue, double min, double max) {
        return addEntry(createKey(key), new DoubleConfigValue(defaultValue, min, max, this.comment));
    }

    /**
     * @param key Key where the defined Value is saved in the .toml File
     * @param defaultValue The defaultValue gets used when the config file get initialised the first Time or
     *                     when the Value in the .toml file is corrupt.
     * @return returns a {@link Supplier} of Type {@link String}
     */
    @Override
    public Supplier<String> define(String key, String defaultValue) {
        return addEntry(createKey(key), new StringConfigValue(defaultValue, this.comment));
    }

    /**
     *
     * @param key Key where the defined Value is saved in the .toml File
     * @param defaultValue The defaultValue gets used when the config file get initialised the
     *                     first Time or when the Value in the .toml file is corrupt.
     * @param minLength Sets the minimal Length of the String
     * @param maxLength Sets the maximal Length of the String
     * @return returns a {@link Supplier} of Type {@link Integer} <br>
     *         If the Config Value is out of Bounds, it gets reset to its Default Value when the
     *         config get initialized the next Time
     */
    @Override
    public Supplier<String> defineInRange(String key, String defaultValue, int minLength, int maxLength) {
        return addEntry(createKey(key), new StringConfigValue(defaultValue, minLength, maxLength, this.comment));
    }

    /**
     * @param key Key where the defined List is saved in the .toml File
     * @param defaultList The defaultValue gets used when the config file get initialised the first Time or
     *                     when the Value in the .toml file is corrupt.
     * @return returns a {@link Supplier} of Type {@link List<T>}
     */
    @Override
    public <T> Supplier<List<T>> define(String key, List<T> defaultList) {
        return addEntry(createKey(key), new ListConfigValue<>(defaultList, this.comment));
    }

}
