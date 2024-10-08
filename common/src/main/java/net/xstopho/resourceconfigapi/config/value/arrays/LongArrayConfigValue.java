package net.xstopho.resourceconfigapi.config.value.arrays;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class LongArrayConfigValue extends ConfigValue<Long[]> {
    public LongArrayConfigValue(Long[] defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Long[];
        return predicate.test(value);
    }
}
