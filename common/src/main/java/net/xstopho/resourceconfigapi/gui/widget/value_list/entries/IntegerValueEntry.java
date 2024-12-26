package net.xstopho.resourceconfigapi.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.gui.widget.value_list.base.ValueEntry;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class IntegerValueEntry extends ValueEntry<Integer> {

    private final Pattern pattern = Pattern.compile("-?\\d*");

    public IntegerValueEntry(String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(modId, fileName, translationKey, field, defaultValue);

        if (isRanged()) {
            RangedEntry range = field.getAnnotation(RangedEntry.class);
            valueWidget = createSlider(range.minValue(), range.maxValue(), true);
        } else {
            valueWidget = createEditBox(pattern);
        }
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
