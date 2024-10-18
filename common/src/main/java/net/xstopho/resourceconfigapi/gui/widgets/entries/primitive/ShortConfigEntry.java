package net.xstopho.resourceconfigapi.gui.widgets.entries.primitive;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.gui.widgets.entries.ValueConfigEntry;

import java.util.Objects;
import java.util.regex.Pattern;

public class ShortConfigEntry extends ValueConfigEntry<Short> {

    private final EditBox editBox;
    private final Pattern pattern = Pattern.compile("^(-?(3276[0-7]|327[0-5]\\d|32[0-6]\\d{2}|3[01]\\d{3}|[12]\\d{4}|\\d{1,4})|-$)?$");

    public ShortConfigEntry(ConfigEntry<Short> configEntry) {
        super(configEntry);

        this.editBox = new EditBox(getFont(), 0, 0, WIDGET_WIDTH, 18, Component.literal(""));
        this.editBox.setFilter(value -> pattern.matcher(value).matches());
        this.editBox.setValue(configEntry.get().toString());
        this.editBox.setResponder(value -> setUndo(!Objects.equals(value, configEntry.get().toString())));

        this.add(editBox);
    }

    @Override
    public Short getValue() {
        if (editBox.getValue().isEmpty()) return configEntry.get();
        return Short.valueOf(editBox.getValue());
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
