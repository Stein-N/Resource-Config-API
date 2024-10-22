package net.xstopho.resourceconfigapi.gui.widgets.entries.reference;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.gui.widgets.entries.ValueConfigEntry;

import java.util.Objects;

public class StringConfigEntry extends ValueConfigEntry<String> {

    private final EditBox editBox;

    public StringConfigEntry(ConfigEntry<String> configEntry) {
        super(configEntry);

        this.editBox = new EditBox(getFont(), 0, 0, WIDGET_WIDTH + 50, 18, Component.literal(""));
        this.editBox.setResponder(string -> setUndo(!Objects.equals(string, configEntry.get())));
        this.editBox.setValue(configEntry.get());
        this.editBox.setCursorPosition(0);
        this.editBox.setHighlightPos(0);

        this.add(editBox);
    }

    @Override
    public String getValue() {
        if (editBox.getValue().isEmpty()) {
            return configEntry.get();
        } else return editBox.getValue();
    }

    @Override
    public void undo() {
        editBox.setValue(configEntry.get());
    }

    @Override
    public void reset() {
        editBox.setValue(configEntry.getDefaultValue());
    }
}
