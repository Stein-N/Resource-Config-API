package net.xstopho.resourceconfigapi.config.value.primitiv;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class ByteConfigValue extends ConfigValue<Byte> {

    private Byte min, max;

    public ByteConfigValue(Byte defaultValue) {
        super(defaultValue);
    }

    public ByteConfigValue(Byte defaultValue, Byte min, Byte max) {
        this(defaultValue);
        this.min = min;
        this.max = max;
        setRanged();
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Byte;

        if (isRanged() && predicate.test(value)) {
            return (Byte) value >= min && (Byte) value <= max;
        }
        else return !isRanged() && predicate.test(value);
    }
}
