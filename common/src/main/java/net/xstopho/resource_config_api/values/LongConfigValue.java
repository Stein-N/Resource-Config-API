package net.xstopho.resource_config_api.values;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.function.Predicate;

public class LongConfigValue extends ConfigValue<Long> {
    final long min, max;

    public LongConfigValue(Long defaultValue, String comment) {
        this(defaultValue, 0, 0, comment);
    }

    public LongConfigValue(Long defaultValue, long min, long max, String comment) {
        super(defaultValue, comment);
        this.min = min;
        this.max = max;
    }

    @Override
    public String getRangedComment() {
        String comment = " Range: " + this.min + " ~ " + this.max + " - Default: " + this.defaultValue;
        if (isRanged() && hasComment()) return getComment() + "\n" + comment;
        if (isRanged()) return comment;
        return null;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isValid = o -> o instanceof Long;

        if (isRanged() && isValid.test(value)) return (long) value >= min && (long) value <= max;
        else return !isRanged() && isValid.test(value);
    }

    @Override
    public boolean isRanged() {
        return !(min == 0 && max == 0);
    }
}
