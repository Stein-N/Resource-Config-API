package net.xstopho.resourceconfigapi.gui.widget.value_list.entries;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.xstopho.resourceconfigapi.gui.widget.value_list.base.BaseEntry;
import net.xstopho.resourceconfigapi.util.ConfigUtils;


public class CategoryEntry extends BaseEntry {

    public CategoryEntry(String fileName, String key) {
        super(fileName, key, ChatFormatting.GOLD);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int rowWidth, int rowHeight,
                       int mouseX, int mouseY, boolean hovered, float delta) {
        ConfigUtils.drawStringWithTooltip(guiGraphics, label, tooltip,
                xPos + (rowWidth / 2) - (font.width(label.getString()) / 2),
                yPos + 6, mouseX, mouseY, hovered);
    }
}
