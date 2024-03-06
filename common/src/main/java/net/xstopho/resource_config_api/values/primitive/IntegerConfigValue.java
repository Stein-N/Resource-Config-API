package net.xstopho.resource_config_api.values.primitive;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.function.Predicate;

public class IntegerConfigValue extends ConfigValue<Integer> {

    public int min, max;

    public IntegerConfigValue(Integer defaultValue, String comment) {
        super(defaultValue, comment);
    }

    public IntegerConfigValue(Integer defaultValue, int min, int max, String comment) {
        super(defaultValue, comment);
        this.min = min; this.max = max;

        this.rangedComment = " Range: " + this.min + " ~ " + this.max + " - Default: " + this.defaultValue;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isValid = o -> o instanceof Integer;

        if (isRanged() && isValid.test(value)) return (int) value >= min && (int) value <= max;
        else return !isRanged() && isValid.test(value);
    }
}
