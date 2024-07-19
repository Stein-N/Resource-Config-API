package net.xstopho.resourceconfigapi.config.values.primitive;

import net.xstopho.resourceconfigapi.config.values.ConfigValue;

import java.util.function.Predicate;

public class IntegerConfigValue extends ConfigValue<Integer> {

    private Integer min, max;

    public IntegerConfigValue(Integer defaultValue, String comment) {
        super(defaultValue, comment);
    }

    public IntegerConfigValue(Integer defaultValue, String comment, int min, int max) {
        this(defaultValue, comment);
        this.min = min;
        this.max = max;
        this.rangedComment = "Ranged " + this.min + " ~ " + this.max;
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Integer;

        if (isRanged() && predicate.test(value)) return (int) value >= min && (int) value <= max;
        else return !isRanged() && predicate.test(value);
    }
}
