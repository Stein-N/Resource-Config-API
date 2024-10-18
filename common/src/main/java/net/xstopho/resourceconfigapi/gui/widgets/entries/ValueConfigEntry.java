package net.xstopho.resourceconfigapi.gui.widgets.entries;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.util.ComponentCreator;

import java.util.ArrayList;
import java.util.List;

public abstract class ValueConfigEntry<T> extends ConfigBaseEntry {

    protected final ConfigEntry<T> configEntry;

    protected final SpriteIconButton undo;
    protected final Button reset;

    public int WIDGET_WIDTH = 150;

    protected List<AbstractWidget> valueWidgets = new ArrayList<>();

    public ValueConfigEntry(ConfigEntry<T> configEntry) {
        this.configEntry = configEntry;

        undo = SpriteIconButton.builder(Component.literal(""), button -> this.undo(), true)
                .sprite(ResourceLocation.fromNamespaceAndPath(ResourceConfigConstants.MOD_ID, "icon/undo"), 16, 16)
                .width(20).build();
        undo.active = false;

        reset = Button.builder(ResourceConfigConstants.RESET, button -> this.undo())
                .tooltip(Tooltip.create(ResourceConfigConstants.RESET_TOOLTIP))
                .bounds(0, 0, 50, 20).build();

        this.children.add(undo);
        this.children.add(reset);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {

        drawStringWithTooltip(guiGraphics, ComponentCreator.label(configEntry.getPath()),
                ComponentCreator.tooltip(configEntry.getPath()),
                xPos, yPos, mouseX, mouseY, hovered);

        undo.setX(xPos + entryWidth - undo.getWidth() - reset.getWidth());
        undo.setY(yPos);

        reset.setX(xPos + entryWidth - reset.getWidth());
        reset.setY(yPos);

        undo.render(guiGraphics, mouseX, mouseY, partialTick);
        reset.render(guiGraphics, mouseX, mouseY, partialTick);

        for (AbstractWidget widget : valueWidgets) {
            widget.setX(xPos + entryWidth - WIDGET_WIDTH);
            if (widget instanceof EditBox) widget.setY(yPos + 1);
            else widget.setY(yPos);
            widget.setWidth(WIDGET_WIDTH - (undo.getWidth() + reset.getWidth()) - 1);

            widget.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    protected void add(AbstractWidget widget) {
        this.children.add(widget);
        this.valueWidgets.add(widget);
    }

    protected void setUndo(boolean active) {
        undo.active = active;
    }

    @Override
    public void save() {
        configEntry.setValue(getValue());
        setUndo(false);
    }

    public abstract T getValue();
}
