package net.xstopho.resource_config_api.values.reference;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.LinkedList;
import java.util.function.Predicate;

public class LinkedListConfigValue<T> extends ConfigValue<LinkedList<T>> {
    public LinkedListConfigValue(LinkedList<T> defaultValue, String comment) {
        super(defaultValue, comment);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof LinkedList<?>;
        return predicate.test(value);
    }
}
