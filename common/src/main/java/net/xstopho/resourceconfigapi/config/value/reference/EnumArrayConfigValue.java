package net.xstopho.resourceconfigapi.config.value.reference;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class EnumArrayConfigValue<T extends Enum<T>> extends ConfigValue<T[]> {
    public EnumArrayConfigValue(T[] defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Enum<?>[];
        return predicate.test(value);
    }
}
