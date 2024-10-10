package net.xstopho.resourceconfigapi.config.value.arrays;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class BooleanArrayConfigValue extends ConfigValue<Boolean[]> {
    public BooleanArrayConfigValue(Boolean[] defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Boolean[];
        return predicate.test(value);
    }
}
