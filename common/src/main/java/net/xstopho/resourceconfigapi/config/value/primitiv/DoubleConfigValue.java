package net.xstopho.resourceconfigapi.config.value.primitiv;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class DoubleConfigValue extends ConfigValue<Double> {

    private Double min, max;

    public DoubleConfigValue(Double defaultValue) {
        super(defaultValue);
    }

    public DoubleConfigValue(Double defaultValue, Double min, Double max) {
        this(defaultValue);
        this.min = min;
        this.max = max;
        setRanged();
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Double;

        if (isRanged() && predicate.test(value)) {
            return (Double) value >= min && (Double) value <= max;
        }
        else return !isRanged() && predicate.test(value);
    }
}
