package net.xstopho.resourceconfigapi.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.gui.widget.value_list.base.ValueEntry;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class FloatValueEntry extends ValueEntry<Float> {

    private final Pattern pattern = Pattern.compile("[0-9]{0,10}(\\.[0-9]{0,10})?");

    public FloatValueEntry(String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(modId, fileName, translationKey, field, defaultValue);

        if (isRanged()) {
            RangedEntry range = field.getAnnotation(RangedEntry.class);
            this.valueWidget = createSlider(range.minValue(), range.maxValue(), false);
        } else {
            this.valueWidget = createEditBox(pattern);
        }
    }


    @Override
    public Float getValue() {
        if (valueWidget instanceof RangedEntrySlider slider) {
            return (float) slider.getValue();
        }

        if (valueWidget instanceof EditBox editBox) {
            String value = editBox.getValue();
            return value.isEmpty() ? getFieldValue() : Float.valueOf(value);
        }
        throw new IllegalStateException("Failed to get Float value from Widget for field: " + field.getName());
    }
}
