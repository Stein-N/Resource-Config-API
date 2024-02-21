package net.xstopho.resource_config_api.values.primitive;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.function.Predicate;

public class BooleanConfigValue extends ConfigValue<Boolean> {
    public BooleanConfigValue(boolean defaultValue, String comment) {
        super(defaultValue, comment);
    }

    @Override
    public String getRangedComment() {
        String comment = " Allowed: true ~ false - Default: " + this.defaultValue;
        if (hasComment()) return getComment() + "\n" + comment;
        else return comment;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isValid = o -> o instanceof Boolean;
        return isValid.test(value);
    }

    @Override
    public boolean isRanged() {
        return false;
    }
}
