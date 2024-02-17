package net.xstopho.resource_config_api.values.base;

public abstract class ConfigValue<T> implements IConfigValue<T> {

    public final T defaultValue;
    public final String comment;

    public ConfigValue(T defaultValue, String comment) {
        this.defaultValue = defaultValue;
        this.comment = comment;
    }

    @Override
    public T get() {
        return defaultValue;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public boolean hasComment() {
        return comment != null;
    }
}
