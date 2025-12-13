package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.xstopho.resourceconfigapi.client.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.NumberValueEntry;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class ByteValueEntry extends NumberValueEntry<Byte> {

    public ByteValueEntry(ResourceConfigScreen screen, String modId, String fileName, String key, Field field, Object defaultValue) {
        super(screen, modId, fileName, key, field, defaultValue, true,
                Pattern.compile("^(?:12[0-7]|1[01][0-9]|[1-9]?[0-9])?$"));
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
