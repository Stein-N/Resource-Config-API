package net.xstopho.resource_config_api.values;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.function.Predicate;

public class ByteConfigValue extends ConfigValue<Byte> {
    final byte min, max;

    public ByteConfigValue(Byte defaultValue, String comment) {
        this(defaultValue, (byte) 0, (byte) 0, comment);
    }

    public ByteConfigValue(Byte defaultValue, byte min, byte max, String comment) {
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
        Predicate<Object> isValid = o -> o instanceof Byte;

        if (isRanged() && isValid.test(value)) return (byte) value >= min && (byte) value <= max;
        else return !isRanged() && isValid.test(value);
    }

    @Override
    public boolean isRanged() {
        return !(min == 0 && max == 0);
    }
}
