package net.xstopho.resourceconfigapi.config.entry;

import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.config.values.ConfigValue;

public class ConfigEntry<T> {
    protected final String path, translation;
    protected final ConfigValue<T> configValue;
    protected final boolean sync;

    protected boolean isLoaded, isSynced;
    private T value, serverValue;


    public ConfigEntry(String path, ConfigValue<T> configValue, String translation, boolean sync) {
        this.path = path;
        this.configValue = configValue;
        this.translation = translation;
        this.sync = sync;
    }

    public T getValue() {
        if (!isLoaded) throw new IllegalStateException("Config isn't loaded yet!");
        return isSynced ? serverValue : value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setServerValue(T value) {
        this.serverValue = value;
    }

    public String getPath() {
        return path;
    }

    public ConfigValue<T> getConfigValue() {
        return configValue;
    }

    public boolean syncWithServer() {
        return sync;
    }

    public void setLoaded() {
        isLoaded = true;
    }

    public void setSynced() {
        isSynced = true;
    }

    public T value() {
        return value;
    }

    public String getTranslation() {
        return translation;
    }
}
