package net.xstopho.resourceconfigapi.gui.widgets.entries;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.util.ComponentCreator;

public class CategoryEntry extends ConfigBaseEntry {

    private final Component label, tooltip;

    public CategoryEntry(String category) {
        this.label = ComponentCreator.label(category).copy().withStyle(ChatFormatting.GOLD);
        this.tooltip = ComponentCreator.tooltip(category);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {

        drawStringWithTooltip(guiGraphics, label, tooltip,
                xPos + (entryWidth / 2) - (font.width(label.getString()) / 2),
                yPos, mouseX, mouseY, hovered);
    }

    @Override
    public void save() {}

    @Override
    public void undo() {}

    @Override
    public void reset() {}
}
