package net.xstopho.resourceconfigapi.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.gui.widgets.entries.CategoryEntry;
import net.xstopho.resourceconfigapi.gui.widgets.entries.ConfigBaseEntry;
import net.xstopho.resourceconfigapi.util.CategoryTabHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ResourceCategoryTab implements Tab {

    private final ResourceModConfig config;
    private final Screen previous;

    protected final ResourceCategoryEntryList entryList;
    private final List<ConfigBaseEntry> entries = new ArrayList<>();

    public ResourceCategoryTab(Screen previous, ResourceModConfig config) {
        this.previous = previous;
        this.config = config;

        String category = null;
        for (ConfigEntry<?> entry : config.getBuilder().getEntries().values()) {
            String path = entry.getPath();

            if (path.contains(".")) {
                String tempCategory = path.split("\\.")[0];

                if (!tempCategory.equalsIgnoreCase(category)) {
                    category = tempCategory;
                    entries.add(new CategoryEntry(category));
                }
            }

            entries.add(CategoryTabHelper.createWidget(entry));
        }

        entryList = new ResourceCategoryEntryList(Minecraft.getInstance(), this.previous.width,
                this.previous.height, 0, 24, entries);
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
        consumer.accept(entryList);
    }

    @Override
    public void doLayout(ScreenRectangle screenRectangle) {
        this.entryList.setRectangle(screenRectangle.width(), screenRectangle.height(), screenRectangle.left(), screenRectangle.top());
    }

    public Screen getPreviousScreen() {
        return previous;
    }
}
