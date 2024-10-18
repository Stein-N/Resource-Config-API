package net.xstopho.resourceconfigapi.util;

import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.gui.widgets.entries.ValueConfigEntry;
import net.xstopho.resourceconfigapi.gui.widgets.entries.primitive.*;

public class CategoryTabHelper {

    public static ValueConfigEntry<?> createWidget(ConfigEntry<?> configEntry) {
        Class<?> clazz = configEntry.get().getClass();

        if (clazz == Boolean.class) return new BooleanConfigEntry((ConfigEntry<Boolean>) configEntry);
        if (clazz == Byte.class) return new ByteConfigEntry((ConfigEntry<Byte>) configEntry);
        if (clazz == Character.class) return new CharacterConfigEntry((ConfigEntry<Character>) configEntry);
        if (clazz == Double.class) return new DoubleConfigEntry((ConfigEntry<Double>) configEntry);
        if (clazz == Float.class) return new FloatConfigEntry((ConfigEntry<Float>) configEntry);
        if (clazz == Integer.class) return new IntegerConfigEntry((ConfigEntry<Integer>) configEntry);
        if (clazz == Long.class) return new LongConfigEntry((ConfigEntry<Long>) configEntry);
        if (clazz == Short.class) return new ShortConfigEntry((ConfigEntry<Short>) configEntry);
        return null;
    }
}
