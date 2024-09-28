package net.xstopho.resourceconfigapi.config.value.primitiv;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class BooleanConfigValue extends ConfigValue<Boolean> {

    public BooleanConfigValue(Boolean defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Boolean;
        return predicate.test(value);
    }
}
