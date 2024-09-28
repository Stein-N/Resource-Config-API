package net.xstopho.resourceconfigapi.config.value.primitiv;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class ShortConfigValue extends ConfigValue<Short> {

    private Short min, max;

    public ShortConfigValue(Short defaultValue) {
        super(defaultValue);
    }

    public ShortConfigValue(Short defaultValue, Short min, Short max) {
        this(defaultValue);
        this.min = min;
        this.max = max;
        setRanged();
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Short;

        if (isRanged() && predicate.test(value)) {
            return (Short) value >= min && (Short) value <= max;
        }
        else return !isRanged() && predicate.test(value);
    }
}
