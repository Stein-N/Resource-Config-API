package net.xstopho.resourceconfigapi.gui.widgets.entries;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;

public abstract class ValueConfigEntry<T> extends ConfigBaseEntry {

    protected final ConfigEntry<T> configEntry;

    protected final SpriteIconButton undo;
    protected final Button reset;

    public final int VALUE_WIDGET_WIDTH;

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

        VALUE_WIDGET_WIDTH = WIDGET_WIDTH - (undo.getWidth() + reset.getWidth());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        undo.setX(xPos + entryWidth - undo.getWidth() - reset.getWidth());
        undo.setY(yPos);

        reset.setX(xPos + entryWidth - reset.getWidth());
        reset.setY(yPos);

        undo.render(guiGraphics, mouseX, mouseY, partialTick);
        reset.render(guiGraphics, mouseX, mouseY, partialTick);
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
