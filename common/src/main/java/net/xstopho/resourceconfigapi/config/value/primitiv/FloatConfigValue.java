package net.xstopho.resourceconfigapi.config.value.primitiv;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class FloatConfigValue extends ConfigValue<Float> {

    private Float min, max;

    public FloatConfigValue(Float defaultValue) {
        super(defaultValue);
    }

    public FloatConfigValue(Float defaultValue, Float min, Float max) {
        this(defaultValue);
        this.min = min;
        this.max = max;
        setRanged();
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Float;

        if (isRanged() && predicate.test(value)) {
            return (Float) value >= min && (Float) value <= max;
        }
        else return !isRanged() && predicate.test(value);
    }
}
