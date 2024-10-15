package net.xstopho.resourceconfigapi.gui.widgets;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;

import java.util.function.Consumer;

public class ResourceCategoryTab implements Tab {

    private final ResourceModConfig config;
    private final Screen previous;

    public ResourceCategoryTab(Screen previous, ResourceModConfig config) {
        this.previous = previous;
        this.config = config;
    }

    @Override
    public Component getTabTitle() {
        return switch (config.getType()) {
            case COMMON -> Component.literal("Common");
            case SERVER -> Component.literal("Server");
            case CLIENT -> Component.literal("Client");
        };
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {

    }

    @Override
    public void doLayout(ScreenRectangle screenRectangle) {

    }

    public Screen getPreviousScreen() {
        return previous;
    }
}
