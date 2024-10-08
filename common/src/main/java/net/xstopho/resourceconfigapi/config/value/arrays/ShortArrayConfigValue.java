package net.xstopho.resourceconfigapi.config.value.arrays;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class ShortArrayConfigValue extends ConfigValue<Short[]> {
    public ShortArrayConfigValue(Short[] defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Short[];
        return predicate.test(value);
    }
}
