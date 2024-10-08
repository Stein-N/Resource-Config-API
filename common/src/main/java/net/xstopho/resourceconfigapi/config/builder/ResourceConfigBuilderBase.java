package net.xstopho.resourceconfigapi.config.builder;

import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ResourceConfigBuilderBase implements IResourceConfigBuilder {

    private boolean sync;
    private String category = "";
    private Map<String, ConfigEntry<?>> entries = new LinkedHashMap<>();

    @Override
    public Map<String, ConfigEntry<?>> getEntries() {
        return entries;
    }

    @Override
    public IResourceConfigBuilder push(String category) {
        if (isEmpty(this.category)) {
            this.category = category;
        } else throw new IllegalArgumentException("Category is already set! Use pop() before pushing a new Category!");
        return this;
    }

    @Override
    public IResourceConfigBuilder pop() {
        if (isEmpty(category)) throw new IllegalArgumentException("There is no Category to remove!");
        else {
            this.category = null;
        }
        return this;
    }

    @Override
    public IResourceConfigBuilder sync() {
        this.sync = true;
        return this;
    }

    <T> ConfigEntry<T> createEntry(String path, ConfigValue<T> configValue) {
        if (this.entries.containsKey(path)) throw new IllegalStateException("Key '" + path + "' is already defined!");

        ConfigEntry<T> entry = new ConfigEntry<>(path, configValue, sync);
        addEntry(path, entry);

        return entry;
    }

    private void addEntry(String path, ConfigEntry<?> configEntry) {
        this.entries.put(path, configEntry);
        this.sync = false;
    }

    protected String createKey(String key) {
        if (isEmpty(key)) throw new IllegalArgumentException("Key can't be null or empty!");
        if (!isEmpty(category)) return category + "." + key;
        return key;
    }
}
