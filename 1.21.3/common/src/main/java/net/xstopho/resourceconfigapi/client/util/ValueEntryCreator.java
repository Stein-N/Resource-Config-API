package net.xstopho.resourceconfigapi.client.util;

import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.BaseEntry;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries.*;

import java.lang.reflect.Field;

public class ValueEntryCreator {

    public static BaseEntry create(String modId, String fileName, String translation, Field field, Object defaultValue) {
        Class<?> clazz = field.getType();

        return switch (clazz.getSimpleName().toLowerCase()) {
            case "boolean" -> new BooleanValueEntry(modId, fileName, translation, field, defaultValue);
            case "byte" -> new ByteValueEntry(modId, fileName, translation, field, defaultValue);
            case "double" -> new DoubleValueEntry(modId, fileName, translation, field, defaultValue);
            case "float" -> new FloatValueEntry(modId, fileName, translation, field, defaultValue);
            case "int", "integer" -> new IntegerValueEntry(modId, fileName, translation, field, defaultValue);
            case "long" -> new LongValueEntry(modId, fileName, translation, field, defaultValue);
            case "short" -> new ShortValueEntry(modId, fileName, translation, field, defaultValue);
            case "string" -> new StringValueEntry(modId, fileName, translation, field, defaultValue);
            default -> {
                if (clazz.isEnum()) yield new EnumValueEntry<>(modId, fileName, translation, field, defaultValue);
                throw new IllegalStateException(String.format("Something went wrong while creating a ValueEntry for Field '%s'!", field.getName()));
            }
        };
    }
}
