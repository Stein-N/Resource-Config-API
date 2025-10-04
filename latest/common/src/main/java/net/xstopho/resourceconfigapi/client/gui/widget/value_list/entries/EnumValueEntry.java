package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.ButtonValueEntry;
import net.xstopho.resourceconfigapi.client.util.GuiUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EnumValueEntry<T extends Enum<T>> extends ButtonValueEntry<T> {

    private final Class<T> clazz;
    private final List<T> enumValues;

    public EnumValueEntry(ResourceConfigScreen screen, String modId, String fileName, String translationKey, Field field, Object defaultValue) {
        super(screen, modId, fileName, translationKey, field, defaultValue);

        if (field.getType().isEnum()) {
            this.clazz = (Class<T>) field.getType();
            this.enumValues = Arrays.stream(clazz.getEnumConstants()).toList();
        } else {
            throw new IllegalArgumentException("Field '" + field.getName() + "' isn't a Enum Value!");
        }

        valueButton = Button.builder(Component.literal(getFieldValue().toString()), this::nextEnum)
                .tooltip(GuiUtils.hasTranslation(tooltip) ? Tooltip.create(tooltip) : null)
                .bounds(0, 0, getWidgetWidth(), 20)
                .build();

        this.children.add(valueButton);
    }

    @Override
    public T getValue() {
        return Enum.valueOf(clazz, valueButton.getMessage().getString());
    }

    @Override
    public void undoChanges() {
        valueButton.setMessage(Component.literal(getFieldValue().toString()));
        changeUndoState(false);
    }

    @Override
    public void resetValues() {
        valueButton.setMessage(Component.literal(defaultValue.toString()));
        changeUndoState(!Objects.equals(getValue(), getFieldValue()));
        super.resetValues();
    }

    private void nextEnum(Button button) {
        int index = enumValues.indexOf(getValue());
        if (index < enumValues.size() - 1) index++;
        else index = 0;

        button.setMessage(Component.literal(enumValues.get(index).toString()));
        super.undoChanges();
    }
}
