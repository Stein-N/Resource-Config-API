package net.xstopho.resourceconfigapi.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.gui.widget.value_list.base.ValueEntry;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class ShortValueEntry extends ValueEntry<Short> {

    private final Pattern pattern = Pattern.compile("^(-?(3276[0-7]|327[0-5]\\d|32[0-6]\\d{2}|3[01]\\d{3}|[12]\\d{4}|\\d{1,4})|-$)?$");

    public ShortValueEntry(String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(modId, fileName, translationKey, field, defaultValue);

        if (isRanged()) {
            RangedEntry range = field.getAnnotation(RangedEntry.class);
            this.valueWidget = createSlider(range.minValue(), range.maxValue(), true);
        } else {
            this.valueWidget = createEditBox(pattern);
        }
    }

    @Override
    public Short getValue() {
        if (valueWidget instanceof RangedEntrySlider slider) {
            return (short) slider.getValue();
        }

        if (valueWidget instanceof EditBox editBox) {
            String value = editBox.getValue();
            return value.isEmpty() ? getFieldValue() : Short.valueOf(value);
        }
        throw new IllegalStateException("Failed to get Long value from Widget for field: " + field.getName());
    }
}
