package net.xstopho.resourceconfigapi.config.value.arrays;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class FloatArrayConfigValue extends ConfigValue<Float[]> {
    public FloatArrayConfigValue(Float[] defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Float[];
        return predicate.test(value);
    }
}
