package net.xstopho.resourceconfigapi.gui.widget.config_list;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.gui.widget.value_list.ValueListWidget;

import java.util.ArrayList;
import java.util.List;

public class ConfigListWidget extends ObjectSelectionList<ConfigListEntry> {

    private final ValueListWidget valueListWidget;
    private final List<ConfigListEntry> valueEntries = new ArrayList<>();

    public ConfigListWidget(int width, int height, int yPos, int itemHeight, List<ModConfig> configList, ValueListWidget valueListWidget) {
        super(Minecraft.getInstance(), width, height, yPos, itemHeight);
        this.valueListWidget = valueListWidget;

        configList.forEach(this::processConfigs);
    }

    private void processConfigs(ModConfig config) {
        ConfigListEntry entry = new ConfigListEntry(config, this.valueListWidget);
        this.valueEntries.add(entry);
        this.addEntry(entry);
    }

    @Override
    public int getWidth() {
        return this.width + 14;
    }

    @Override
    public int getRowWidth() {
        return this.width - 4;
    }

    public List<ConfigListEntry> getValueEntries() {
        return valueEntries;
    }
}
