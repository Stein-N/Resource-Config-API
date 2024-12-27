package net.xstopho.resourceconfigapi.gui.widget.value_list.base;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.util.ClientConfigUtils;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntry extends ContainerObjectSelectionList.Entry<BaseEntry> {

    protected final List<AbstractWidget> children = new ArrayList<>();
    protected final Font font = ClientConfigUtils.getFont();

    protected final Component label, tooltip;

    /**
     *
     * @param fileName is the fileName of the given config
     * @param key is the Category or Value name
     * @param chatFormatting is formatting for the label, tooltips cant be changed currently
     */
    public BaseEntry(String modId, String fileName, String key, ChatFormatting chatFormatting) {
        this.label = ConfigUtils.createModLabel(modId, fileName + "." + key).copy().withStyle(chatFormatting);
        this.tooltip = ConfigUtils.createModTooltip(modId, fileName + "." + key);
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

    public Font getFont() {
        return font;
    }

    public void saveValues() {}

    public void undoChanges() {}

    public void resetValues() {}
}
