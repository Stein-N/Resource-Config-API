package net.xstopho.resourceconfigapi.gui.widget.value_list.entries;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.gui.widget.value_list.base.ValueEntry;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

import java.lang.reflect.Field;
import java.util.Objects;

public class BooleanValueEntry extends ValueEntry<Boolean> {

    private final Button button;
    private boolean state;

    public BooleanValueEntry(String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(modId, fileName, translationKey, field, defaultValue);

        this.state = this.getFieldValue();

        this.button = Button.builder(state ? Constants.BOOLEAN_ENABLED : Constants.BOOLEAN_DISABLED, this::changeState)
                .tooltip(ConfigUtils.hasTranslation(tooltip) ? Tooltip.create(tooltip) : null)
                .bounds(0, 0, getWidgetWidth(), 20)
                .build();

        this.children.add(button);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int rowWidth,
                       int rowHeight, int mouseX, int mouseY, boolean hovered, float delta) {
        super.render(guiGraphics, index, yPos, xPos, rowWidth, rowHeight, mouseX, mouseY, hovered, delta);

        button.setPosition(xPos + rowWidth - getWidgetWidth(), yPos);
        button.setWidth(getCorrectedWidgetWidth());

        button.render(guiGraphics, mouseX, mouseX, delta);
    }

    private void changeState(Button button) {
        state = !state;

        button.setMessage(state ? Constants.BOOLEAN_ENABLED : Constants.BOOLEAN_DISABLED);

        changeUndoState(!Objects.equals(state, getFieldValue()));
    }

    @Override
    public Boolean getValue() {
        return state;
    }

    @Override
    public void undoChanges() {
        state = getFieldValue();
        button.setMessage(state ? Constants.BOOLEAN_ENABLED : Constants.BOOLEAN_DISABLED);
        changeUndoState(false);
    }

    @Override
    public void resetValues() {
        state = (boolean) this.defaultValue;
        button.setMessage(state ? Constants.BOOLEAN_ENABLED : Constants.BOOLEAN_DISABLED);
        changeUndoState(!Objects.equals(state, getFieldValue()));
        super.resetValues();
    }
}
