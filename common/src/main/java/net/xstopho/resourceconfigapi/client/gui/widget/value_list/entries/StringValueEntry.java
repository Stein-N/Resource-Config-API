package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.EditBox;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.ValueEntry;

import java.lang.reflect.Field;

public class StringValueEntry extends ValueEntry<String> {
    public StringValueEntry(String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(modId, fileName, translationKey, field, defaultValue);

        valueWidget = createEditBox(null);
    }

    @Override
    public String getValue() {
        EditBox box = (EditBox) valueWidget;

        if (box.getValue().isEmpty()) {
            return getFieldValue();
        }

        return box.getValue();
    }
}
