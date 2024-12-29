package net.xstopho.resourceconfigapi.client.gui.widget.value_list;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.BaseEntry;

import java.util.Collection;

public class ValueListWidget extends ContainerObjectSelectionList<BaseEntry> {

    public ValueListWidget(int width, int height, int y, int itemHeight) {
        super(Minecraft.getInstance(), width, height, y, itemHeight);
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {}

    @Override
    public int getRowWidth() {
        return this.width - 20;
    }
}
