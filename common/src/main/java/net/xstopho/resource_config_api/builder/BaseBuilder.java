package net.xstopho.resource_config_api.builder;

import net.xstopho.resource_config_api.config.ConfigEntry;
import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class BaseBuilder implements IConfigBuilder {

    String category = null, comment;
    Map<String, ConfigEntry<?>> entries = new LinkedHashMap<>();
    Map<String, String> categoryComments = new LinkedHashMap<>();

    /**
     * @param category Define a Category for all following Values that are defined by any define method.
     * @return returns the ConfigBuilder itself, to add more actions afterward.<br>
     *         You can also use the {@link #comment(String)} method before {@link #push(String)} to add a comment
     *         for the Category in the Config file.
     */
    @Override
    public IConfigBuilder push(String category) {
        if (this.category == null) {
            this.categoryComments.put(category, this.comment);
            this.category = category;
            this.comment = null;
        } else throw new IllegalArgumentException("Category '" + this.category + "' is already set. Use pop() to set a new Category!");
        return this;
    }

    /**
     * @return returns the ConfigBuilder itself, to add more actions afterward.<br>
     *         Basically deletes the current set Category.
     */
    @Override
    public IConfigBuilder pop() {
        if (this.category != null) this.category = null;
        else throw new IllegalArgumentException("There is no Category to remove!");
        return this;
    }

    /**
     * @param msg can be as long as you want
     * @return returns the ConfigBuilder itself, to add more actions afterward.<br>
     *         Use this Method before declaring a Category with {@link #push(String)}
     *         or defining a Value with f.e. {@link #define(String, int)}.<br>
     *         When a Ranged Value is defined by f.e. {@link #defineInRange(String, int, int, int)} the
     *         comment gets ignored, because it gets automatically a ranged comment!
     */
    @Override
    public IConfigBuilder comment(String msg) {
        if (comment == null) this.comment = " " + msg;
        else this.comment = this.comment + "\n " + msg;
        return this;
    }

    /**
     * @param path Path where the Value gets saved in the .toml file
     * @param configValue contains the defaultValue and comment defined by the {@link IConfigBuilder}<br>
     *                    via the {@link #comment(String)} and f.e. {@link #define(String, int)} Methods
     * @return returns a Supplier that returns the value from the config file, when it is initialised.
     * @param <T> gets defined by the {@link #define(String, int)} methods.
     */
    public <T> Supplier<T> addEntry(String path, ConfigValue<T> configValue) {
        if (this.entries.containsKey(path)) throw new IllegalStateException("Key '" + path + "' is already defined!");

        ConfigEntry<T> entry = new ConfigEntry<>(path, configValue);
        this.entries.put(path, entry);
        this.comment = null;
        return entry::getValue;
    }

    /**
     * @param key defined key where the Value gets saved in the .toml file
     * @return returns a key separated with a dot, when a Category is set, when not it returns the given key
     */
    public String createKey(String key) {
        if (key == null || key.isBlank() || key.isEmpty()) throw new IllegalArgumentException("Key can't be null or empty!");
        if (category != null) return category + "." + key;
        return key;
    }

}
