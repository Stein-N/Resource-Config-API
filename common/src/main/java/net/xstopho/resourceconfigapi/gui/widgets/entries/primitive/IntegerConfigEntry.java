package net.xstopho.resourceconfigapi.gui.widgets.entries.primitive;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.gui.widgets.entries.ValueConfigEntry;

import java.util.Objects;
import java.util.regex.Pattern;

public class IntegerConfigEntry extends ValueConfigEntry<Integer> {

    private final EditBox editBox;

    private final Pattern pattern = Pattern.compile("-?\\d*");

    public IntegerConfigEntry(ConfigEntry<Integer> configEntry) {
        super(configEntry);

        editBox = new EditBox(getFont(), 0, 0, WIDGET_WIDTH, 18, Component.empty());
        editBox.setFilter(value -> pattern.matcher(value).matches());
        editBox.setValue(configEntry.get().toString());
        editBox.setResponder(value -> setUndo(!Objects.equals(value, configEntry.get().toString())));

        this.add(editBox);
    }

    @Override
    public Integer getValue() {
        if (editBox.getValue().isEmpty()) {
            return configEntry.get();
        } else return Integer.valueOf(editBox.getValue());
    }

    @Override
    public void undo() {
        editBox.setValue(configEntry.get().toString());
    }

    @Override
    public void reset() {
        editBox.setValue(configEntry.getDefaultValue().toString());
    }
}
