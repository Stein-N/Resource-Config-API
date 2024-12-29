package net.xstopho.resourceconfigapi.client.util;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class WidgetCreator {

    public static EditBox createEditBox(int widgetWidth, Object fieldValue, Pattern pattern, List<AbstractWidget> children, Consumer<String> consumer) {
        EditBox editBox = new EditBox(GuiUtils.getFont(), 0, 0, widgetWidth, 18, Component.empty());
        editBox.setValue(fieldValue.toString());
        editBox.setResponder(consumer);

        if (pattern != null) {
            editBox.setFilter(s -> pattern.matcher(s).matches());
        }
        children.add(editBox);

        return editBox;
    }
}
