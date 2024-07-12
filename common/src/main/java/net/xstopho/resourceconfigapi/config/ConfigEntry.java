package net.xstopho.resourceconfigapi.config;


import net.xstopho.resourceconfigapi.values.base.ConfigValue;

public class ConfigEntry<T> {
    private final String path;
    private final ConfigValue<T> configValue;
    private boolean isLoaded;
    private T value;

    public ConfigEntry(String path, ConfigValue<T> configValue) {
        this.path = path;
        this.configValue = configValue;
    }

    public T getValue() {
        if (!isLoaded()) throw new IllegalStateException("Config isn't loaded yet!");
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    public ConfigValue<T> getConfigValue() {
        return configValue;
    }

    public String getPath() {
        return path;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded() {
        isLoaded = true;
    }
}
