package net.xstopho.resourceconfigapi.values.primitive;


import net.xstopho.resourceconfigapi.values.base.ConfigValue;

import java.util.function.Predicate;

public class BooleanConfigValue extends ConfigValue<Boolean> {
    public BooleanConfigValue(boolean defaultValue, String comment) {
        super(defaultValue, comment);

        this.rangedComment = " Allowed: true ~ false - Default: " + this.defaultValue;
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Boolean;
        return predicate.test(value);
    }
}
