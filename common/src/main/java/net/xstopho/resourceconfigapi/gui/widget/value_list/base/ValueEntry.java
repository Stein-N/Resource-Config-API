package net.xstopho.resourceconfigapi.gui.widget.value_list.base;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.ClientConstants;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.gui.widget.RangedEntrySlider;
import net.xstopho.resourceconfigapi.util.ClientConfigUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.regex.Pattern;

public abstract class ValueEntry<T> extends BaseEntry {

    private final ResourceLocation undoSprite = Constants.of("textures/gui/sprites/icon/undo.png");

    protected final Field field;
    protected final Object defaultValue;

    protected final int widgetWidth = 150;

    protected final Button reset, undo;
    protected AbstractWidget valueWidget;

    public ValueEntry(String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(modId, fileName, translationKey, ChatFormatting.WHITE);
        this.field = field;
        this.defaultValue = defaultValue;

        reset = Button.builder(ClientConstants.RESET, button -> resetValues())
                .tooltip(ClientConfigUtils.hasTranslation(ClientConstants.RESET_TOOLTIP) ? Tooltip.create(ClientConstants.RESET_TOOLTIP) : null)
                .bounds(0, 0, 50, 20)
                .build();

        undo = Button.builder(Component.empty(), button -> undoChanges())
                .tooltip(ClientConfigUtils.hasTranslation(ClientConstants.UNDO_TOOLTIP) ? Tooltip.create(ClientConstants.UNDO_TOOLTIP) : null)
                .bounds(0, 0, 20, 20)
                .build();

        undo.active = false;

        this.children.add(reset);
        this.children.add(undo);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int rowWidth,
                       int rowHeight, int mouseX, int mouseY, boolean hovered, float delta) {

        ClientConfigUtils.drawStringWithTooltip(guiGraphics, label, tooltip,
                xPos, yPos + 6, mouseX, mouseY);

        undo.setPosition(xPos + rowWidth - undo.getWidth() - reset.getWidth(), yPos);
        reset.setPosition(xPos + rowWidth - reset.getWidth(), yPos);

        if (valueWidget != null) {
            valueWidget.setPosition(xPos + rowWidth - getWidgetWidth(), yPos);
            valueWidget.setWidth(getCorrectedWidgetWidth());

            valueWidget.render(guiGraphics, mouseX, mouseY, delta);
        }

        reset.render(guiGraphics, mouseX, mouseY, delta);
        undo.render(guiGraphics, mouseX, mouseY, delta);

        guiGraphics.blit(RenderType::guiTexturedOverlay, undoSprite, undo.getX() + 2, undo.getY() + 2,
                0f, 0f, 16, 16, 16, 16);
    }

    @Override
    public void undoChanges() {
        if (valueWidget instanceof RangedEntrySlider slider) {
            slider.undoValue();
        }

        if (valueWidget instanceof EditBox editBox) {
            editBox.setValue(getFieldValue().toString());
        }

        changeUndoState(false);
    }

    @Override
    public void resetValues() {
        if (valueWidget instanceof RangedEntrySlider slider) {
            slider.setValue(Double.parseDouble(defaultValue.toString()));
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
            changeUndoState(false);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Failed to save new Value for Field %s", field.getName()));
        }
    }

    public abstract T getValue();

    protected T getFieldValue() {
        try {
            return (T) field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Failed to get Value for Field '%s'", field.getName()));
        }
    }

    protected int getWidgetWidth() {
        return widgetWidth;
    }

    protected int getCorrectedWidgetWidth() {
        return getWidgetWidth() - (undo.getWidth() + reset.getWidth()) - 1;
    }

    protected void changeUndoState(boolean state) {
        this.undo.active = state;
    }

    protected boolean isRanged() {
        return field.isAnnotationPresent(RangedEntry.class);
    }

    protected RangedEntrySlider createSlider(double minValue, double maxValue, boolean integer) {
        RangedEntrySlider slider = new RangedEntrySlider(0, 0, widgetWidth, 20, Double.parseDouble(getFieldValue().toString()), minValue, maxValue, integer);
        slider.setResponder(aDouble -> changeUndoState(!Objects.equals(aDouble, getFieldValue())));
        this.children.add(slider);

        return slider;
    }

    protected EditBox createEditBox(Pattern pattern) {
        EditBox editBox = new EditBox(getFont(), 0, 0, widgetWidth, 18, Component.empty());
        editBox.setValue(getFieldValue().toString());
        editBox.setResponder(s -> changeUndoState(!Objects.equals(s, getFieldValue().toString())));

        if (pattern != null) {
            editBox.setFilter(s -> pattern.matcher(s).matches());
        }

        this.children.add(editBox);

        return editBox;
    }
}
