package net.xstopho.resourceconfigapi.config.value.arrays;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class IntegerArrayConfigValue extends ConfigValue<Integer[]> {
    public IntegerArrayConfigValue(Integer[] defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Integer[];
        return predicate.test(value);
    }
}
