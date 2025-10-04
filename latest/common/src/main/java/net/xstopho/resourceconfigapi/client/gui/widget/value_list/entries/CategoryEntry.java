package net.xstopho.resourceconfigapi.client.gui.widget.value_list.entries;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.xstopho.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.BaseEntry;
import net.xstopho.resourceconfigapi.client.util.GuiUtils;


public class CategoryEntry extends BaseEntry {

    public CategoryEntry(ResourceConfigScreen screen, String modId, String fileName, String key) {
        super(screen, modId, fileName, key, ChatFormatting.GOLD);
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float delta) {
        GuiUtils.drawStringWithTooltip(this.screen, this, guiGraphics, label, tooltip,
                this.getContentX() + (this.getContentWidth() / 2) - (font.width(label.getString()) / 2),
                this.getContentY() + 6, mouseX, mouseY);
    }
}
