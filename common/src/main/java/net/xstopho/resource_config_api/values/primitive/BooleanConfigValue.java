package net.xstopho.resource_config_api.values.primitive;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.function.Predicate;

public class BooleanConfigValue extends ConfigValue<Boolean> {
    public BooleanConfigValue(boolean defaultValue, String comment) {
        super(defaultValue, comment);

        this.rangedComment = " Allowed: true ~ false - Default: " + this.defaultValue;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isValid = o -> o instanceof Boolean;
        return isValid.test(value);
    }
}
