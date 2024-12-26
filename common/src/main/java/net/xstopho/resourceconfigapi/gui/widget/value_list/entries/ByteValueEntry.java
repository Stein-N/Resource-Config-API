package net.xstopho.resourceconfigapi.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.gui.widget.value_list.base.ValueEntry;

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
            if (editBox.getValue().isEmpty()) {
                return getFieldValue();
            }
            return Byte.valueOf(editBox.getValue());
        }
        throw new IllegalStateException("Failed to get Float value from Widget for field: " + field.getName());
    }
}
