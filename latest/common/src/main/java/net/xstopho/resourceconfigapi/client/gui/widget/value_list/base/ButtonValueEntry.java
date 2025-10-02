package net.xstopho.resourceconfigapi.client.gui.widget.value_list.base;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.client.ClientConstants;
import net.xstopho.resourceconfigapi.client.util.GuiUtils;

import java.lang.reflect.Field;

public abstract class ButtonValueEntry<T> extends BaseEntry {

    protected final Object defaultValue;
    private final Field field;

    private final Button reset, undo;
    protected Button valueButton;

    public ButtonValueEntry(String modId, String fileName, String key, Field field, Object defaultValue) {
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

        this.children.add(reset);
        this.children.add(undo);
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float delta) {
        GuiUtils.drawStringWithTooltip(guiGraphics, label, tooltip, this.getContentX() + 13, this.getContentY() + 6, mouseX, mouseY);

        undo.setPosition(this.getContentX() + this.getContentWidth() - undo.getWidth() - reset.getWidth(), this.getContentY());
        reset.setPosition(this.getContentX() + this.getContentWidth() - reset.getWidth(), this.getContentY());

        valueButton.setPosition(this.getContentX() + this.getContentWidth() - getWidgetWidth(), this.getContentY());
        valueButton.setWidth(getWidgetWidth() - (undo.getWidth() + reset.getWidth()) - 1);

        valueButton.render(guiGraphics, mouseX, mouseY, delta);
        reset.render(guiGraphics, mouseX, mouseY, delta);
        undo.render(guiGraphics, mouseX, mouseY, delta);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, undoSprite, undo.getX() + 2, undo.getY() + 2,
                0f, 0f, 16, 16, 16, 16);

        GuiUtils.renderIcon(guiGraphics, field, this.getContentX(), this.getContentY() + 4, mouseX, mouseY);
    }

    public abstract T getValue();

    @Override
    public void undoChanges() {
        undo.active = false;
    }

    @Override
    public void resetValues() {
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

    protected void changeUndoState(boolean state) {
        this.undo.active = state;
    }

    protected T getFieldValue() {
        try {
            return (T) field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Failed to get Value for Field '%s'", field.getName()));
        }
    }
}
