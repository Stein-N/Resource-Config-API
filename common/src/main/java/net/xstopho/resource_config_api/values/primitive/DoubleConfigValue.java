package net.xstopho.resource_config_api.values.primitive;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.function.Predicate;

public class DoubleConfigValue extends ConfigValue<Double> {

    private final double min, max;

    public DoubleConfigValue(Double defaultValue, String comment) {
        this(defaultValue, 0.0, 0.0, comment);
    }

    public DoubleConfigValue(Double defaultValue, double min, double max, String comment) {
        super(defaultValue, comment);
        this.min = min;
        this.max = max;
    }

    @Override
    public String getRangedComment() {
        String rangedComment = " Range: " + this.min + " ~ " + this.max + " - Default: " + this.defaultValue;
        if (isRanged() && hasComment()) return getComment() + "\n" + rangedComment;
        if (isRanged()) return rangedComment;
        return null;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isValid = o -> o instanceof Double;

        if (isRanged() && isValid.test(value)) return (double) value >= min && (double) value <= max;
        else return !isRanged() && isValid.test(value);
    }

    @Override
    public boolean isRanged() {
        return !(min == 0.0 && max == 0.0);
    }
}
