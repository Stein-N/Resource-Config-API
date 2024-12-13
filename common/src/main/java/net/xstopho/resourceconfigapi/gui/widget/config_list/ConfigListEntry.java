package net.xstopho.resourceconfigapi.gui.widget.config_list;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.gui.widget.value_list.ValueListWidget;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

public class ConfigListEntry extends ObjectSelectionList.Entry<ConfigListEntry> {

    private final ValueListWidget valueListWidget;
    private final Component fileName;

    public ConfigListEntry(Class<?> clazz, ValueListWidget valueListWidget) {
        this.valueListWidget = valueListWidget;

        Config annotation = clazz.getAnnotation(Config.class);
        this.fileName = Component.literal(annotation.fileName());
    }

    @Override
    public Component getNarration() {
        return fileName;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int rowWidth,
                                int rowHeight, int mouseX, int mouseY, boolean hovered, float delta) {
        guiGraphics.drawString(ConfigUtils.getFont(), fileName, xPos + 2, yPos + 2, -1, false);
    }
}
