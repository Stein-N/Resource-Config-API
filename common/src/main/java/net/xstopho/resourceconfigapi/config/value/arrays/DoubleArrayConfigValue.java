package net.xstopho.resourceconfigapi.config.value.arrays;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class DoubleArrayConfigValue extends ConfigValue<Double[]> {
    public DoubleArrayConfigValue(Double[] defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Double[];
        return predicate.test(value);
    }
}
