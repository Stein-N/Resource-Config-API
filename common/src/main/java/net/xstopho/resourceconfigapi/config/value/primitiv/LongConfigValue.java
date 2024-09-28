package net.xstopho.resourceconfigapi.config.value.primitiv;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class LongConfigValue extends ConfigValue<Long> {

    private Long min, max;

    public LongConfigValue(Long defaultValue) {
        super(defaultValue);
    }

    public LongConfigValue(Long defaultValue, Long min, Long max) {
        this(defaultValue);
        this.min = min;
        this.max = max;
        setRanged();
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Long;

        if (isRanged() && predicate.test(value)) {
            return (Long) value >= min && (Long) value <= max;
        }
        else return !isRanged() && predicate.test(value);
    }
}
