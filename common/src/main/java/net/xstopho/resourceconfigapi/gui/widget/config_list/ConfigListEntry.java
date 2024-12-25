package net.xstopho.resourceconfigapi.gui.widget.config_list;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.gui.widget.value_list.base.BaseEntry;
import net.xstopho.resourceconfigapi.gui.widget.value_list.ValueListWidget;
import net.xstopho.resourceconfigapi.util.ConfigHolder;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

import java.util.LinkedList;

public class ConfigListEntry extends ObjectSelectionList.Entry<ConfigListEntry> {

    private final ValueListWidget valueListWidget;
    private final LinkedList<BaseEntry> entryList;
    private final String fileName;

    public ConfigListEntry(ConfigHolder configHolder, ValueListWidget valueListWidget) {
        this.valueListWidget = valueListWidget;

        this.fileName = configHolder.getFileName();
        this.entryList = configHolder.getEntryList();
    }

    @Override
    public boolean mouseClicked(double p_331676_, double p_330254_, int p_331536_) {
        this.valueListWidget.replaceEntries(entryList);
        this.valueListWidget.setScrollAmount(0);
        return super.mouseClicked(p_331676_, p_330254_, p_331536_);
    }

    @Override
    public Component getNarration() {
        return Component.literal(fileName);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int rowWidth,
                                int rowHeight, int mouseX, int mouseY, boolean hovered, float delta) {
        guiGraphics.drawString(ConfigUtils.getFont(), fileName, xPos + 2, yPos + 2, -1, false);
    }

    public LinkedList<BaseEntry> getEntryList() {
        return entryList;
    }
}
