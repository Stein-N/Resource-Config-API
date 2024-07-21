package net.xstopho.resourceconfigapi.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;

public class ConfigScreenBuilder extends Screen {

    private final ResourceModConfig config;

    private EditBox stringEditBox;

    public ConfigScreenBuilder(Screen previous, String modName, ResourceModConfig config) {
        super(Component.literal(modName));
        this.config = config;
    }

    @Override
    protected void init() {
        this.stringEditBox = new EditBox(this.font, 250, 40, Component.literal("Test"));
        this.addRenderableOnly(this.stringEditBox);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.stringEditBox.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
