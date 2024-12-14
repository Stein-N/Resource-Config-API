package net.xstopho.resourceconfigapi.gui.widget.value_list;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntry extends ContainerObjectSelectionList.Entry<BaseEntry> {

    protected final List<AbstractWidget> children = new ArrayList<>();
    protected final Font font = ConfigUtils.getFont();

    protected final Component label, tooltip;

    public BaseEntry(String key, ChatFormatting chatFormatting) {
        this.label = ConfigUtils.createLabel(key).copy().withStyle(chatFormatting);
        this.tooltip = ConfigUtils.createTooltip(key);
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return this.children;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return this.children;
    }

    @Override
    public abstract void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int rowWidth,
                       int rowHeight, int mouseX, int mouseY, boolean hovered, float delta);

    public void saveValues() {
        Constants.LOG.error("Saving Values for: {}", this.label.getString());
    }

    public void undoChanges() {
        Constants.LOG.error("Undo Changes for: {}", this.label.getString());
    }

    public void resetValues() {
        Constants.LOG.error("Reset Values for: {}", this.label.getString());
    }
}
