package net.morthen.resourceconfigapi.client.gui.widget.value_list;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.morthen.resourceconfigapi.client.gui.widget.value_list.base.BaseEntry;

import java.util.Collection;

public class ValueListWidget extends ContainerObjectSelectionList<BaseEntry> {

    public ValueListWidget(int width, int height, int y, int itemHeight) {
        super(Minecraft.getInstance(), width, height, y, itemHeight);
    }

    @Override
    public void replaceEntries(Collection<BaseEntry> entries) {
        super.replaceEntries(entries);
    }

    @Override
    public int getRowWidth() {
        return this.width - 20;
    }

    @Override
    public void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float delta) {
        super.extractWidgetRenderState(guiGraphics, mouseX, mouseY, delta);
    }
}
