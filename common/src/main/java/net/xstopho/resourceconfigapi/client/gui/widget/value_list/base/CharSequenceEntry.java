package net.xstopho.resourceconfigapi.client.gui.widget.value_list.base;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.client.ClientConstants;
import net.xstopho.resourceconfigapi.client.util.GuiUtils;

import java.lang.reflect.Field;
import java.util.Objects;

public abstract class CharSequenceEntry<T> extends BaseEntry {

    private final Object defaultValue;
    private final Field field;

    private final Button reset, undo;
    protected final EditBox editBox;

    public CharSequenceEntry(String modId, String fileName, String key, Field field, Object defaultValue) {
        super(modId, fileName, key, ChatFormatting.WHITE);
        this.defaultValue = defaultValue;
        this.field = field;

        reset = Button.builder(ClientConstants.RESET, button -> resetValues())
                .tooltip(GuiUtils.hasTranslation(ClientConstants.RESET_TOOLTIP) ? Tooltip.create(ClientConstants.RESET_TOOLTIP) : null)
                .bounds(0, 0, 50, 20)
                .build();

        undo = Button.builder(Component.empty(), button -> undoChanges())
                .tooltip(GuiUtils.hasTranslation(ClientConstants.UNDO_TOOLTIP) ? Tooltip.create(ClientConstants.UNDO_TOOLTIP) : null)
                .bounds(0, 0, 20, 20)
                .build();
        undo.active = false;

        editBox = new EditBox(getFont(), getWidgetWidth(), 18, Component.empty());
        editBox.setValue(getFieldValue().toString());
        editBox.setResponder(s -> undo.active = !Objects.equals(s, getFieldValue()));

        this.children.add(editBox);
        this.children.add(reset);
        this.children.add(undo);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int rowWidth,
                       int rowHeight, int mouseX, int mouseY, boolean hovered, float delta) {
        GuiUtils.drawStringWithTooltip(guiGraphics, label, tooltip,
                xPos + 13, yPos + 6, mouseX, mouseY);

        undo.setPosition(xPos + rowWidth - undo.getWidth() - reset.getWidth(), yPos);
        reset.setPosition(xPos + rowWidth - reset.getWidth(), yPos);

        editBox.setPosition(xPos + rowWidth - getWidgetWidth(), yPos + 1);
        editBox.setWidth(getWidgetWidth() - (undo.getWidth() + reset.getWidth()) - 1);

        editBox.render(guiGraphics, mouseX, mouseY, delta);
        reset.render(guiGraphics, mouseX, mouseY, delta);
        undo.render(guiGraphics, mouseX, mouseY, delta);

        guiGraphics.blit(undoSprite, undo.getX() + 2, undo.getY() + 2,
                0f, 0f, 16, 16, 16, 16);

        GuiUtils.renderIcon(guiGraphics, field, xPos, yPos + 4, mouseX, mouseY);
    }

    public abstract T getValue();

    @Override
    public void undoChanges() {
        editBox.setValue(getFieldValue().toString());
        undo.active = false;
    }

    @Override
    public void resetValues() {
        editBox.setValue(defaultValue.toString());

        try {
            this.field.set(this.field, this.defaultValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to set default value for field: " + field.getName() + "\n" + e);
        }
    }

    @Override
    public void saveValues() {
        try {
            this.field.set(field, getValue());
            undo.active = false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Failed to save new Value for Field %s", field.getName()));
        }
    }

    protected T getFieldValue() {
        try {
            return (T) field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Failed to get Value for Field '%s'", field.getName()));
        }
    }
}
