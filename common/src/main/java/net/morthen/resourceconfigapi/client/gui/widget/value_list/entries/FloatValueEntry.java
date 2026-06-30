package net.morthen.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.morthen.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.morthen.resourceconfigapi.client.gui.widget.RangedEntrySlider;
import net.morthen.resourceconfigapi.client.gui.widget.value_list.base.NumberValueEntry;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class FloatValueEntry extends NumberValueEntry<Float> {

    public FloatValueEntry(ResourceConfigScreen screen, String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(screen, modId, fileName, translationKey, field, defaultValue, false,
                Pattern.compile("[0-9]{0,10}(\\.[0-9]{0,10})?"));
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
