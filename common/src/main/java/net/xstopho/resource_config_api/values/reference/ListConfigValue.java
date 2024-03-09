package net.xstopho.resource_config_api.values.reference;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.List;
import java.util.function.Predicate;

public class ListConfigValue<T> extends ConfigValue<List<T>> {
    public ListConfigValue(List<T> defaultValue, String comment) {
        super(defaultValue, comment);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof List<?>;
        return predicate.test(value);
    }
}
