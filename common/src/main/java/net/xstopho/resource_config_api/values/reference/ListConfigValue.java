package net.xstopho.resource_config_api.values.reference;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.List;
import java.util.function.Predicate;

public class ListConfigValue<T> extends ConfigValue<List<T>> {
    public ListConfigValue(List<T> defaultValue, String comment) {
        super(defaultValue, comment);
    }

    @Override
    public String getRangedComment() {
        if (hasComment()) return getComment();
        return null;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isValid = o -> o instanceof List<?>;
        return isValid.test(value);
    }

    @Override
    public boolean isRanged() {
        return false;
    }
}
