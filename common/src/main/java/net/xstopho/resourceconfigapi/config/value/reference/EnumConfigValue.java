package net.xstopho.resourceconfigapi.config.value.reference;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class EnumConfigValue<T extends Enum<T>> extends ConfigValue<Enum<T>> {
    public EnumConfigValue(Enum<T> defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Enum<?>;
        return predicate.test(value);
    }
}
