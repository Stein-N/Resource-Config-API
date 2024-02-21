package net.xstopho.resource_config_api.builder;

import net.xstopho.resource_config_api.config.*;
import net.xstopho.resource_config_api.values.*;

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
     * @return returns a {@link Supplier} of Type {@link Byte}
     */
    @Override
    public Supplier<Byte> define(String key, byte defaultValue) {
        return addEntry(createKey(key), new ByteConfigValue(defaultValue, this.comment));
    }

    /**
     *
     * @param key Key where the defined Value is saved in the .toml File
     * @param defaultValue The defaultValue gets used when the config file get initialised the
     *                     first Time or when the Value in the .toml file is corrupt.
     * @param min Sets the minimal Value for the Config File
     * @param max Sets the maximal Value for the Config File
     * @return returns a {@link Supplier} of Type {@link Byte} <br>
     *         If the Config Value is out of Bounds, it gets reset to its Default Value when the
     *         config get initialized the next Time.
     */
    @Override
    public Supplier<Byte> defineInRange(String key, byte defaultValue, byte min, byte max) {
        return addEntry(createKey(key), new ByteConfigValue(defaultValue, min, max, this.comment));
    }

    /**
     * @param key Key where the defined Value is saved in the .toml File
     * @param defaultValue The defaultValue gets used when the config file get initialised the first Time or
     *                     when the Value in the .toml file is corrupt.
     * @return returns a {@link Supplier} of Type {@link Short}
     */
    @Override
    public Supplier<Short> define(String key, short defaultValue) {
        return addEntry(createKey(key), new ShortConfigValue(defaultValue, this.comment));
    }

    /**
     *
     * @param key Key where the defined Value is saved in the .toml File
     * @param defaultValue The defaultValue gets used when the config file get initialised the
     *                     first Time or when the Value in the .toml file is corrupt.
     * @param min Sets the minimal Value for the Config File
     * @param max Sets the maximal Value for the Config File
     * @return returns a {@link Supplier} of Type {@link Short} <br>
     *         If the Config Value is out of Bounds, it gets reset to its Default Value when the
     *         config get initialized the next Time.
     */
    @Override
    public Supplier<Short> defineInRange(String key, short defaultValue, short min, short max) {
        return addEntry(createKey(key), new ShortConfigValue(defaultValue, min, max, this.comment));
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
     * @return returns a {@link Supplier} of Type {@link Long}
     */
    @Override
    public Supplier<Long> define(String key, long defaultValue) {
        return addEntry(createKey(key), new LongConfigValue(defaultValue, this.comment));
    }

    /**
     *
     * @param key Key where the defined Value is saved in the .toml File
     * @param defaultValue The defaultValue gets used when the config file get initialised the
     *                     first Time or when the Value in the .toml file is corrupt.
     * @param min Sets the minimal Value for the Config File
     * @param max Sets the maximal Value for the Config File
     * @return returns a {@link Supplier} of Type {@link Long} <br>
     *         If the Config Value is out of Bounds, it gets reset to its Default Value when the
     *         config get initialized the next Time.
     */
    @Override
    public Supplier<Long> defineInRange(String key, long defaultValue, long min, long max) {
        return addEntry(createKey(key), new LongConfigValue(defaultValue, min, max, this.comment));
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
     * @return returns a {@link Supplier} of Type {@link Character}
     */
    @Override
    public Supplier<Character> define(String key, Character defaultValue) {
        return addEntry(createKey(key), new CharConfigValue(defaultValue, this.comment));
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

}
