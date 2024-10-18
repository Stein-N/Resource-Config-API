package net.xstopho.resourceconfigapi.gui.widgets.entries.primitive;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.gui.widgets.entries.ValueConfigEntry;

import java.util.Objects;

public class BooleanConfigEntry extends ValueConfigEntry<Boolean> {

    private final Button button;
    private boolean state;

    private final Component ENABLED = ResourceConfigConstants.BOOLEAN_ENABLED;
    private final Component DISABLED = ResourceConfigConstants.BOOLEAN_DISABLED;

    public BooleanConfigEntry(ConfigEntry<Boolean> configEntry) {
        super(configEntry);
        this.state = configEntry.get();

        this.button = Button.builder(state ? ENABLED : DISABLED, this::setButtonState)
                .bounds(0, 0, WIDGET_WIDTH, 20).build();

        this.add(button);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        super.render(guiGraphics, index, yPos, xPos, entryWidth, entryHeight, mouseX, mouseY, hovered, partialTick);
    }

    private void setButtonState(Button button) {
        state = !state;

        if (state) button.setMessage(ENABLED);
        else button.setMessage(DISABLED);

        setUndo(!Objects.equals(state, configEntry.get()));
    }

    @Override
    public Boolean getValue() {
        return state;
    }

    @Override
    public void undo() {
        button.setMessage(configEntry.get() ? ENABLED : DISABLED);
        state = configEntry.get();
        setUndo(false);
    }

    @Override
    public void reset() {
        button.setMessage(configEntry.get() ? ENABLED : DISABLED);
        state = configEntry.get();
        setUndo(!Objects.equals(state, configEntry.get()));
    }
}
