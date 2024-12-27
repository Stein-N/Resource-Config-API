package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.client.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.ValueEntry;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class ByteValueEntry extends ValueEntry<Byte> {

    private final Pattern pattern = Pattern.compile("^(?:12[0-7]|1[01][0-9]|[1-9]?[0-9])?$");

    public ByteValueEntry(String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(modId, fileName, translationKey, field, defaultValue);

        if (isRanged()) {
            RangedEntry range = field.getAnnotation(RangedEntry.class);
            valueWidget = createSlider(range.minValue(), range.maxValue(), true);

        } else {
            valueWidget = createEditBox(pattern);
        }
    }

    @Override
    public Byte getValue() {
        if (valueWidget instanceof RangedEntrySlider slider) {
            return (byte) slider.getValue();
        }

        if (valueWidget instanceof EditBox editBox) {
            String value = editBox.getValue();
            return value.isEmpty() ? getFieldValue() : Byte.valueOf(value);
        }
        throw new IllegalStateException("Failed to get Byte value from Widget for field: " + field.getName());
    }
}
