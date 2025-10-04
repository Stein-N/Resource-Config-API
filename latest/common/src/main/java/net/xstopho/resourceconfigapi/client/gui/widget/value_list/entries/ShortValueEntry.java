package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.xstopho.resourceconfigapi.client.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.NumberValueEntry;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class ShortValueEntry extends NumberValueEntry<Short> {

    public ShortValueEntry(ResourceConfigScreen screen, String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(screen, modId, fileName, translationKey, field, defaultValue, true,
                Pattern.compile("^(-?(3276[0-7]|327[0-5]\\d|32[0-6]\\d{2}|3[01]\\d{3}|[12]\\d{4}|\\d{1,4})|-$)?$"));
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
