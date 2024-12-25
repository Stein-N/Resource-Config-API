package net.xstopho.resourceconfigapi.gui.util;

import net.xstopho.resourceconfigapi.gui.widget.value_list.base.ValueEntry;
import net.xstopho.resourceconfigapi.gui.widget.value_list.entries.BooleanValueEntry;
import net.xstopho.resourceconfigapi.gui.widget.value_list.entries.ByteValueEntry;
import net.xstopho.resourceconfigapi.gui.widget.value_list.entries.DoubleValueEntry;
import net.xstopho.resourceconfigapi.gui.widget.value_list.entries.FloatValueEntry;

import java.lang.reflect.Field;

public class ValueEntryCreator {

    public static ValueEntry<?> create(String fileName, String translation, Field field, Object defaultValue) {
        Class<?> clazz = field.getType();

        if (clazz == Boolean.class || clazz == boolean.class) {
            return new BooleanValueEntry(fileName, translation, field, defaultValue);
        }
        if (clazz == Byte.class || clazz == byte.class) {
            return new ByteValueEntry(fileName, translation, field, defaultValue);
        }
        if (clazz == Double.class || clazz == double.class) {
            return new DoubleValueEntry(fileName, translation, field, defaultValue);
        }
        if (clazz == Float.class || clazz == float.class) {
            return new FloatValueEntry(fileName, translation, field, defaultValue);
        }

        throw new IllegalStateException(String.format("Something went wrong while creating a ValueEntry for Field '%s'!", field.getName()));
    }
}
