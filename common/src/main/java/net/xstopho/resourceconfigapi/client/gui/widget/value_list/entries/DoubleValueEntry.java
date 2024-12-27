package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.client.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.ValueEntry;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class DoubleValueEntry extends ValueEntry<Double> {

    private final Pattern pattern = Pattern.compile("[0-9]{0,10}(\\.[0-9]{0,10})?");

    public DoubleValueEntry(String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(modId, fileName, translationKey, field, defaultValue);

        if (isRanged()) {
            RangedEntry range = field.getAnnotation(RangedEntry.class);
            valueWidget = createSlider(range.minValue(), range.maxValue(), false);

        } else {
            valueWidget = createEditBox(pattern);
        }
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
