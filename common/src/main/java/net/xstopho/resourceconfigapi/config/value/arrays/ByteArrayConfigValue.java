package net.xstopho.resourceconfigapi.config.value.arrays;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class ByteArrayConfigValue extends ConfigValue<Byte[]> {
    public ByteArrayConfigValue(Byte[] defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Byte[];
        return predicate.test(value);
    }
}
