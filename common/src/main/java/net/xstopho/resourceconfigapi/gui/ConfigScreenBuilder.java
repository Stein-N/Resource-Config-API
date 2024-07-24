package net.xstopho.resourceconfigapi.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;

public class ConfigScreenBuilder extends Screen {

    private final ResourceModConfig config;
    private final Screen previous;

    public ConfigScreenBuilder(Screen previous, String modName, ResourceModConfig config) {
        super(Component.literal(modName));
        this.config = config;
        this.previous = previous;
    }

    @Override
    protected void init() {

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.shouldCloseOnEsc()) {
            Minecraft.getInstance().setScreen(previous);
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
