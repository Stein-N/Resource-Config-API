package net.morthen.resourceconfigapi.client.gui.widget.config_list;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.morthen.resourceconfigapi.client.gui.widget.value_list.ValueListWidget;
import net.morthen.resourceconfigapi.client.gui.widget.value_list.base.BaseEntry;
import net.morthen.resourceconfigapi.client.util.ComponentUtils;
import net.morthen.resourceconfigapi.client.util.GuiUtils;
import net.morthen.resourceconfigapi.config.ConfigHolder;

import java.util.LinkedList;

public class ConfigListEntry extends ObjectSelectionList.Entry<ConfigListEntry> {

    private final ValueListWidget valueListWidget;
    private final ConfigHolder configHolder;
    private final Component fileName;

    public ConfigListEntry(ConfigHolder configHolder, ValueListWidget valueListWidget) {
        this.valueListWidget = valueListWidget;

        this.configHolder = configHolder;
        this.fileName = ComponentUtils.modConfig(configHolder.getModId(), configHolder.getFileName());
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseEvent, boolean p_432750_) {
        this.valueListWidget.replaceEntries(configHolder.getEntryList());
        this.valueListWidget.setScrollAmount(0);
        return super.mouseClicked(mouseEvent, p_432750_);
    }

    @Override
    public Component getNarration() {
        return fileName;
    }

    @Override
    public void extractContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, boolean hovered, float delta) {
        guiGraphics.text(GuiUtils.getFont(), fileName, this.getContentX() + 2, this.getContentY() + 2, -1, false);
    }

    public LinkedList<BaseEntry> getEntryList() {
        return configHolder.getEntryList();
    }
}
