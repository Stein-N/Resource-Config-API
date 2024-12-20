package net.xstopho.resourceconfigapi.gui.widget;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.gui.widget.config_list.ConfigListEntry;
import net.xstopho.resourceconfigapi.gui.widget.config_list.ConfigListWidget;
import net.xstopho.resourceconfigapi.gui.widget.value_list.BaseEntry;
import net.xstopho.resourceconfigapi.gui.widget.value_list.ValueListWidget;
import net.xstopho.resourceconfigapi.util.ConfigType;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ConfigTab implements Tab {

    private final List<ModConfig> configList = new ArrayList<>();
    private final List<ConfigListEntry> valueEntries;
    private final ConfigListWidget configListWidget;
    private final ValueListWidget valueListWidget;
    private final ConfigType type;

    public ConfigTab(ConfigType type, Map<ResourceLocation, ModConfig> configs) {
        this.type = type;
        configs.forEach(this::processConfigs);

        this.valueListWidget = new ValueListWidget(0, 0, 0, 24);

        this.configListWidget = new ConfigListWidget(0, 0, 0, 15, configList, valueListWidget);
        this.configListWidget.setSelectedIndex(0);
        this.valueEntries = this.configListWidget.getValueEntries();

        //Set the entries of the selected Config into the ValueListWidget
        ConfigListEntry entry = configListWidget.getSelected();
        if (entry != null && entry.getEntryList() != null) {
            this.valueListWidget.replaceEntries(entry.getEntryList());
        }
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
        consumer.accept(this.valueListWidget);
    }

    @Override
    public void doLayout(ScreenRectangle screenRectangle) {
        this.configListWidget.setRectangle(100, screenRectangle.height() - 13, 5 , 30);
        this.valueListWidget.setRectangle(screenRectangle.width() - configListWidget.getWidth() - 15, screenRectangle.height() - 11, 125 , 29);
    }

    private void consumeAction(Consumer<BaseEntry> consumer) {
        for (ConfigListEntry valueEntry : this.valueEntries) {
            valueEntry.getEntryList().forEach(consumer);
        }
    }
}
