package net.xstopho.resourceconfigapi.config.value.reference;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.List;
import java.util.function.Predicate;

public class ListConfigValue<T> extends ConfigValue<List<T>> {
    public ListConfigValue(List<T> defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof List<?>;
        return predicate.test(value);
    }
}
