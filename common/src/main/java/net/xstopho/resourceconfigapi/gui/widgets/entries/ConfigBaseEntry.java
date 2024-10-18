package net.xstopho.resourceconfigapi.gui.widgets.entries;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.util.EntryLabelTooltipPosition;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigBaseEntry extends ContainerObjectSelectionList.Entry<ConfigBaseEntry> {

    protected final List<AbstractWidget> children = new ArrayList<>();
    protected final Font font = Minecraft.getInstance().font;

    protected void drawStringWithTooltip(GuiGraphics guiGraphics, Component component, Component tooltip,
                                         int xPos, int yPos, int mouseX, int mouseY, boolean hovered) {
        yPos += 6;
        if (component != null) {
            guiGraphics.drawString(font, component, xPos, yPos, -1, false);
            if (tooltip != null && hovered) {
                int xMax = xPos + font.width(component.getString());
                int yMax = yPos + font.lineHeight;
                if (xPos <= mouseX && xMax >= mouseX && yPos <= mouseY && yMax >= mouseY) {
                    guiGraphics.renderTooltip(font, font.split(tooltip, 50),
                            EntryLabelTooltipPosition.INSTANCE, mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public abstract void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick);

    public abstract void save();
    public abstract void undo();
    public abstract void reset();

    @Override
    public List<? extends NarratableEntry> narratables() {
        return this.children;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return this.children;
    }

    public Font getFont() {
        return font;
    }
}
