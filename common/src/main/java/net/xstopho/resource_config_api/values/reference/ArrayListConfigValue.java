package net.xstopho.resource_config_api.values.reference;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayListConfigValue<T> extends ConfigValue<ArrayList<T>> {
    public ArrayListConfigValue(ArrayList<T> defaultValue, String comment) {
        super(defaultValue, comment);
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isValid = o -> o instanceof ArrayList<?>;
        return isValid.test(value);
    }
}
