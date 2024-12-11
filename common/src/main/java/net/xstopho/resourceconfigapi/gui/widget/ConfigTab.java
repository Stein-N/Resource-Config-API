package net.xstopho.resourceconfigapi.gui.widget;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.util.ConfigType;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ConfigTab implements Tab {

    private Map<ResourceLocation, ModConfig> configMap = new HashMap<>();
    private final Screen previous;
    private final ConfigType type;

    public ConfigTab(Screen previous, ConfigType type, Map<ResourceLocation, ModConfig> configs) {
        this.previous = previous;
        this.type = type;

        configs.forEach(this::processConfigs);
    }

    private void processConfigs(ResourceLocation location, ModConfig config) {
        String configType = location.getPath().split("/")[0];

        if (configType.equalsIgnoreCase(this.type.name())) {
            configMap.put(location, config);
        }
    }


    @Override
    public Component getTabTitle() {
        return ConfigUtils.createTitle(this.type.name().toLowerCase());
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {

    }

    @Override
    public void doLayout(ScreenRectangle screenRectangle) {

    }
}
