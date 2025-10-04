package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.xstopho.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.CharSequenceEntry;

import java.lang.reflect.Field;

public class StringValueEntry extends CharSequenceEntry<String> {
    public StringValueEntry(ResourceConfigScreen screen, String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(screen, modId, fileName, translationKey, field, defaultValue);
    }

    @Override
    public String getValue() {
        if (editBox.getValue().isEmpty()) {
            return getFieldValue();
        }

        return editBox.getValue();
    }
}
