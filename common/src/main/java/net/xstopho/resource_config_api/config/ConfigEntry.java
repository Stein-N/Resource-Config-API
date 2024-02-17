package net.xstopho.resource_config_api.config;

import net.xstopho.resource_config_api.values.base.ConfigValue;

public class ConfigEntry<T> {
    public final String path;
    public final ConfigValue<T> configValue;
    public boolean isLoaded = false;
    public T value;

    public ConfigEntry(String path, ConfigValue<T> configValue) {
        this.path = path;
        this.configValue = configValue;
    }

    public T getValue() {
        if (!this.isLoaded) throw new IllegalStateException("Config isn't loaded yet!");
        return this.value;
    }
}
