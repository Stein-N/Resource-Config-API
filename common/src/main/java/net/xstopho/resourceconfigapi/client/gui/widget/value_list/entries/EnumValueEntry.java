package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.ValueEntry;
import net.xstopho.resourceconfigapi.client.util.ClientConfigUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EnumValueEntry<T extends Enum<T>> extends ValueEntry<T> {

    private final Class<T> clazz;
    private final List<T> enumValues;
    private final Button button;
    private int index = 0;


    public EnumValueEntry(String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(modId, fileName, translationKey, field, defaultValue);

        if (field.getType().isEnum()) {
            this.clazz = (Class<T>) field.getType();
            this.enumValues = Arrays.stream(clazz.getEnumConstants()).toList();
        } else {
            throw new IllegalArgumentException("Field '" + field.getName() + "' isn't a Enum Value!");
        }

        button = Button.builder(Component.literal(getFieldValue().toString()), this::nextEnum)
                .tooltip(ClientConfigUtils.hasTranslation(tooltip) ? Tooltip.create(tooltip) : null)
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

    @Override
    public T getValue() {
        return Enum.valueOf(clazz, button.getMessage().getString());
    }

    @Override
    public void undoChanges() {
        button.setMessage(Component.literal(getFieldValue().toString()));
        changeUndoState(false);
    }

    @Override
    public void resetValues() {
        button.setMessage(Component.literal(defaultValue.toString()));
        super.resetValues();
    }

    private void nextEnum(Button button) {
        if (index < enumValues.size() - 1) index++;
        else index = 0;

        button.setMessage(Component.literal(enumValues.get(index).toString()));
        changeUndoState(!Objects.equals(getValue(), getFieldValue()));
    }
}
