package net.xstopho.resourceconfigapi.config.values.primitive;

import net.xstopho.resourceconfigapi.config.values.ConfigValue;

import java.util.function.Predicate;

public class DoubleConfigValue extends ConfigValue<Double> {

    private Double min, max;

    public DoubleConfigValue(Double defaultValue, String comment) {
        super(defaultValue, comment);
    }

    public DoubleConfigValue(Double defaultValue, String comment, double min, double max) {
        this(defaultValue, comment);
        this.min = min;
        this.max = max;
        this.rangedComment = "Ranged: " + this.min + " ~ " + this.max;
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Double;

        if (isRanged() && predicate.test(value)) return (double) value >= min && (double) value <= max;
        else return !isRanged() && predicate.test(value);
    }
}
