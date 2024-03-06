package net.xstopho.resource_config_api.values.primitive;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.function.Predicate;

public class DoubleConfigValue extends ConfigValue<Double> {

    private double min, max;

    public DoubleConfigValue(Double defaultValue, String comment) {
        super(defaultValue, comment);
    }

    public DoubleConfigValue(Double defaultValue, double min, double max, String comment) {
        super(defaultValue, comment);
        this.min = min; this.max = max;

        this.rangedComment = " Range: " + this.min + " ~ " + this.max + " - Default: " + this.defaultValue;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isValid = o -> o instanceof Double;

        if (isRanged() && isValid.test(value)) return (double) value >= min && (double) value <= max;
        else return !isRanged() && isValid.test(value);
    }
}
