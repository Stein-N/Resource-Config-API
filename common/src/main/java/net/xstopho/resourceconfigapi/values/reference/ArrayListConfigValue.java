package net.xstopho.resourceconfigapi.values.reference;

import net.xstopho.resourceconfigapi.values.base.ConfigValue;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayListConfigValue<T> extends ConfigValue<ArrayList<T>> {
    public ArrayListConfigValue(ArrayList<T> defaultValue, String comment) {
        super(defaultValue, comment);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof ArrayList<?>;
        return predicate.test(value);
    }
}
