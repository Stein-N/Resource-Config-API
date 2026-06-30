package net.morthen.resourceconfigapi.client.util;

import net.morthen.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.morthen.resourceconfigapi.client.gui.widget.value_list.base.BaseEntry;
import net.morthen.resourceconfigapi.client.gui.widget.value_list.entries.*;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries.*;

import java.lang.reflect.Field;

public class ValueEntryCreator {

    public static BaseEntry create(ResourceConfigScreen screen, String modId, String fileName, String translation, Field field, Object defaultValue) {
        Class<?> clazz = field.getType();

        return switch (clazz.getSimpleName().toLowerCase()) {
            case "boolean" -> new BooleanValueEntry(screen, modId, fileName, translation, field, defaultValue);
            case "byte" -> new ByteValueEntry(screen, modId, fileName, translation, field, defaultValue);
            case "double" -> new DoubleValueEntry(screen, modId, fileName, translation, field, defaultValue);
            case "float" -> new FloatValueEntry(screen, modId, fileName, translation, field, defaultValue);
            case "int", "integer" -> new IntegerValueEntry(screen, modId, fileName, translation, field, defaultValue);
            case "long" -> new LongValueEntry(screen, modId, fileName, translation, field, defaultValue);
            case "short" -> new ShortValueEntry(screen, modId, fileName, translation, field, defaultValue);
            case "string" -> new StringValueEntry(screen, modId, fileName, translation, field, defaultValue);
            default -> {
                if (clazz.isEnum()) yield new EnumValueEntry<>(screen, modId, fileName, translation, field, defaultValue);
                throw new IllegalStateException(String.format("Something went wrong while creating a ValueEntry for Field '%s'!", field.getName()));
            }
        };
    }
}
