package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.client.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.NumberValueEntry;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class LongValueEntry extends NumberValueEntry<Long> {

    public LongValueEntry(String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(modId, fileName, translationKey, field, defaultValue, true, Pattern.compile("[0-9]{0,15}"));
    }

    @Override
    public Long getValue() {
        if (valueWidget instanceof RangedEntrySlider slider) {
            return (long) slider.getValue();
        }

        if (valueWidget instanceof EditBox editBox) {
            String value = editBox.getValue();
            return value.isEmpty() ? getFieldValue() : Long.valueOf(value);
        }
        throw new IllegalStateException("Failed to get Long value from Widget for field: " + field.getName());
    }
}
