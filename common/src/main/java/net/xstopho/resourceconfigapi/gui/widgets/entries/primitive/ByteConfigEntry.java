package net.xstopho.resourceconfigapi.gui.widgets.entries.primitive;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.gui.widgets.entries.ValueConfigEntry;

import java.util.Objects;
import java.util.regex.Pattern;

public class ByteConfigEntry extends ValueConfigEntry<Byte> {

    private final EditBox editBox;
    private final Pattern pattern = Pattern.compile("^(?:-?(?:12[0-7]|1[01][0-9]|[1-9]?[0-9])|-)?$");

    public ByteConfigEntry(ConfigEntry<Byte> configEntry) {
        super(configEntry);

        this.editBox = new EditBox(getFont(), 0, 0, WIDGET_WIDTH, 18, Component.empty());
        this.editBox.setFilter(value -> pattern.matcher(value).matches());
        this.editBox.setValue(configEntry.get().toString());
        this.editBox.setResponder(value -> setUndo(!Objects.equals(value, configEntry.get().toString())));

        this.add(editBox);
    }

    @Override
    public Byte getValue() {
        if (editBox.getValue().isEmpty()) {
            return configEntry.get();
        }
        return Byte.valueOf(editBox.getValue());
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
