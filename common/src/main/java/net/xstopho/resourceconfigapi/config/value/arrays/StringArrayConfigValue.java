package net.xstopho.resourceconfigapi.config.value.arrays;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class StringArrayConfigValue extends ConfigValue<String[]> {
    public StringArrayConfigValue(String[] defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof String[];
        return predicate.test(value);
    }
}
