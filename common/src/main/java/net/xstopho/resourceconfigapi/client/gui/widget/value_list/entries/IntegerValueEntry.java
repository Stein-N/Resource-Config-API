package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.client.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.NumberValueEntry;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class IntegerValueEntry extends NumberValueEntry<Integer> {

    public IntegerValueEntry(String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(modId, fileName, translationKey, field, defaultValue, true, Pattern.compile("-?\\d*"));
    }

    @Override
    public Integer getValue() {
        if (valueWidget instanceof RangedEntrySlider slider) {
            return (int) slider.getValue();
        }

        if (valueWidget instanceof EditBox editBox) {
            String value = editBox.getValue();
            return value.isEmpty() ? getFieldValue() : Integer.valueOf(value);
        }
        throw new IllegalStateException("Failed to get Integer value from Widget for field: " + field.getName());
    }
}
