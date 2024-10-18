package net.xstopho.resourceconfigapi.config.entry;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Supplier;

public class ConfigEntry<T> implements Supplier<T> {
    protected final String path;
    protected final ConfigValue<T> configValue;
    protected final boolean sync;

    private boolean isLoaded;
    private T value;

    public ConfigEntry(String path, ConfigValue<T> configValue, boolean sync) {
        this.path = path;
        this.configValue = configValue;
        this.sync = sync;
    }

    @Override
    public T get() {
        if (!isLoaded) throw new IllegalStateException("Config isn't loaded yet!");
        return value;
    }

    public T getDefaultValue() {
        return configValue.get();
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ConfigValue<T> getConfigValue() {
        return configValue;
    }

    public String getPath() {
        return path;
    }

    public void setLoaded() {
        isLoaded = true;
    }

    public T value() {
        return value;
    }
}
