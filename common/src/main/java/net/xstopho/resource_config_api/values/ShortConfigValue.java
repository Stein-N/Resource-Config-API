package net.xstopho.resource_config_api.values;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.function.Predicate;

public class ShortConfigValue extends ConfigValue<Short> {
    final short min, max;

    public ShortConfigValue(Short defaultValue, String comment) {
        this(defaultValue, (short) 0, (short) 0, comment);
    }

    public ShortConfigValue(Short defaultValue, short min, short max, String comment) {
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
        Predicate<Object> isValid = o -> o instanceof Short;

        if (isRanged() && isValid.test(value)) return (short) value >= min && (short) value <= max;
        else return !isRanged() && isValid.test(value);
    }

    @Override
    public boolean isRanged() {
        return !(min == 0 && max == 0);
    }
}
