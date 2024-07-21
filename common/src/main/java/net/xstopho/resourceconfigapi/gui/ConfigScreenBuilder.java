package net.xstopho.resourceconfigapi.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;

public class ConfigScreenBuilder extends Screen {

    private final ResourceModConfig config;

    public ConfigScreenBuilder(String modName, ResourceModConfig config) {
        super(Component.literal(modName));
        this.config = config;
    }
}
