package net.xstopho.resourceconfigapi.config.value.primitiv;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class IntegerConfigValue extends ConfigValue<Integer> {

    private Integer min, max;

    public IntegerConfigValue(Integer defaultValue) {
        super(defaultValue);
    }

    public IntegerConfigValue(Integer defaultValue, Integer min, Integer max) {
        this(defaultValue);
        this.min = min;
        this.max = max;
        setRanged();
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Integer;

        if (isRanged() && predicate.test(value)) {
            return (int) value >= min && (int) value <= max;
        }
        else return !isRanged() && predicate.test(value);
    }
}
