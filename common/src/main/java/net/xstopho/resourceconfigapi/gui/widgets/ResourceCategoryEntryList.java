package net.xstopho.resourceconfigapi.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.xstopho.resourceconfigapi.gui.widgets.entries.ConfigBaseEntry;

public class ResourceCategoryEntryList extends ContainerObjectSelectionList<ConfigBaseEntry> {

    public ResourceCategoryEntryList(Minecraft minecraft, int width, int height, int y, int itemHeight, Iterable<ConfigBaseEntry> entries) {
        super(minecraft, width, height, y, itemHeight);
        entries.forEach(this::addEntry);
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {}

    @Override
    public int getRowWidth() {
        return this.width - (Minecraft.getInstance().screen.width / 4);
    }
}
