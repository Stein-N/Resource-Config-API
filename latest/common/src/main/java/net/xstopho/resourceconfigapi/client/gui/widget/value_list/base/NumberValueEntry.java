package net.xstopho.resourceconfigapi.client.gui.widget.value_list.base;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.client.ClientConstants;
import net.xstopho.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.xstopho.resourceconfigapi.client.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.client.util.GuiUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.regex.Pattern;

public abstract class NumberValueEntry<T> extends BaseEntry {

    private final Object defaultValue;
    protected final Field field;

    private final Button reset, undo;
    protected AbstractWidget valueWidget;


    public NumberValueEntry(ResourceConfigScreen screen, String modId, String fileName, String key, Field field, Object defaultValue, boolean integer, Pattern pattern) {
        super(screen, modId, fileName, key, ChatFormatting.WHITE);
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

        if (field.isAnnotationPresent(RangedEntry.class)) {
            RangedEntry range = field.getAnnotation(RangedEntry.class);
            valueWidget = new RangedEntrySlider(getWidgetWidth(), Double.parseDouble(getFieldValue().toString()), range.minValue(), range.maxValue(), integer)
                    .setResponder(aDouble -> undo.active = !Objects.equals(aDouble, getFieldValue()));
        } else {
            valueWidget = new EditBox(getFont(), getWidgetWidth(), 18, Component.empty());
            ((EditBox) valueWidget).setResponder(s -> undo.active = !Objects.equals(s, getFieldValue().toString()));
            ((EditBox) valueWidget).setFilter(s -> pattern.matcher(s).matches());
            ((EditBox) valueWidget).setValue(getFieldValue().toString());
        }

        this.children.add(valueWidget);
        this.children.add(reset);
        this.children.add(undo);
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float delta) {
        GuiUtils.drawStringWithTooltip(this.screen, this, guiGraphics, label, tooltip, this.getContentX() + 13, this.getContentY() + 6, mouseX, mouseY);

        undo.setPosition(this.getContentX() + this.getContentWidth() - undo.getWidth() - reset.getWidth(), this.getContentY());
        reset.setPosition(this.getContentX() + this.getContentWidth() - reset.getWidth(), this.getContentY());

        valueWidget.setPosition(this.getContentX() + this.getContentWidth() - getWidgetWidth(), this.getContentY() + 1);
        valueWidget.setWidth(getWidgetWidth() - (undo.getWidth() + reset.getWidth()) - 1);

        valueWidget.render(guiGraphics, mouseX, mouseY, delta);
        reset.render(guiGraphics, mouseX, mouseY, delta);
        undo.render(guiGraphics, mouseX, mouseY, delta);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, undoSprite, undo.getX() + 2, undo.getY() + 2,
                0f, 0f, 16, 16, 16, 16);

        GuiUtils.renderIcon(this.screen, guiGraphics, field, this.getContentX(), this.getContentY() + 4, mouseX, mouseY);
    }

    public abstract T getValue();

    @Override
    public void undoChanges() {
        if (valueWidget instanceof RangedEntrySlider slider) {
            slider.undoValue();
        }

        if (valueWidget instanceof EditBox editBox) {
            editBox.setValue(getFieldValue().toString());
        }
        this.undo.active = false;
    }

    @Override
    public void resetValues() {
        if (valueWidget instanceof RangedEntrySlider slider) {
            slider.setValueWrapper(Double.parseDouble(defaultValue.toString()));
        }

        if (valueWidget instanceof EditBox editBox) {
            editBox.setValue(defaultValue.toString());
        }

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
            this.undo.active = false;
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
