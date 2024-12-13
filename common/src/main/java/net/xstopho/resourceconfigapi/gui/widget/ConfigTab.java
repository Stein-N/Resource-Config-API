package net.xstopho.resourceconfigapi.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.util.ConfigType;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ConfigTab implements Tab {

    private List<ModConfig> configList = new ArrayList<>();
    private final ConfigListWidget configListWidget;
    private final ConfigType type;

    public ConfigTab(ConfigType type, Map<ResourceLocation, ModConfig> configs) {
        this.type = type;

        configs.forEach(this::processConfigs);

        this.configListWidget = new ConfigListWidget(Minecraft.getInstance(), 0, 0, 0, 15, configList);
    }

    private void processConfigs(ResourceLocation location, ModConfig config) {
        String configType = location.getPath().split("/")[0];

        if (configType.equalsIgnoreCase(this.type.name())) {
            configList.add(config);
        }
    }


    @Override
    public Component getTabTitle() {
        return ConfigUtils.createTitle(this.type.name().toLowerCase());
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {
        consumer.accept(this.configListWidget);
    }

    @Override
    public void doLayout(ScreenRectangle screenRectangle) {
        this.configListWidget.setRectangle(100, screenRectangle.height() - 13, 5 , 30);
    }
}
