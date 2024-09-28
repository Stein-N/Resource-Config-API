package net.xstopho.resourceconfigapi.config.value;

import java.util.function.Supplier;

public abstract class ConfigValue<T> implements Supplier<T> {

    protected final T defaultValue;
    protected boolean isRanged;

    public ConfigValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public T get() {
        return defaultValue;
    }

    public boolean isRanged() {
        return isRanged;
    }

    public void setRanged() {
        isRanged = true;
    }

    public abstract boolean isValid(Object value);
}
