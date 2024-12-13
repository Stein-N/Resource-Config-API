package net.xstopho.resourceconfigapi.gui.widget.value_list;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.util.ConfigUtils;


public class CategoryEntry extends BaseEntry {

    private final Component label;
    private final Component tooltip;

    public CategoryEntry(String category) {
        this.label = ConfigUtils.createLabel(category)
                        .copy().withStyle(ChatFormatting.GOLD);
        this.tooltip = ConfigUtils.createTooltip(category);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int rowWidth, int rowHeight,
                       int mouseX, int mouseY, boolean hovered, float delta) {
        ConfigUtils.drawStringWithTooltip(guiGraphics,
                label, tooltip,
                xPos + (rowWidth / 2) - (font.width(label.getString()) / 2),
                yPos + 6, mouseX, mouseY, hovered);

    }
}
