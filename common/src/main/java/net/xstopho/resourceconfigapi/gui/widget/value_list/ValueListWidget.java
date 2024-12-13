package net.xstopho.resourceconfigapi.gui.widget.value_list;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;

public class ValueListWidget extends ContainerObjectSelectionList<BaseEntry> {

    public ValueListWidget(Minecraft minecraft, int width, int height, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {}
}
