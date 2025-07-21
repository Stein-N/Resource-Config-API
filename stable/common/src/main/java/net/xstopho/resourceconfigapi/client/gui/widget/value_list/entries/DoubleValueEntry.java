package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.client.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.NumberValueEntry;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class DoubleValueEntry extends NumberValueEntry<Double> {

    public DoubleValueEntry(String modId, String fileName, String key, Field field, Object defaultValue) {
        super(modId, fileName, key, field, defaultValue, false,
                Pattern.compile("[0-9]{0,10}(\\.[0-9]{0,10})?"));
    }


    @Override
    public Double getValue() {
        if (valueWidget instanceof RangedEntrySlider slider) {
            return slider.getValue();
        }

        if (valueWidget instanceof EditBox editBox) {
            String value = editBox.getValue();
            return value.isEmpty() ? getFieldValue() : Double.valueOf(value);
        }
        throw new IllegalStateException("Failed to get Double value from Widget for field: " + field.getName());
    }
}
