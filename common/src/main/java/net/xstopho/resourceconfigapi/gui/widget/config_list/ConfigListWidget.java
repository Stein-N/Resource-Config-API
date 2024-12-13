package net.xstopho.resourceconfigapi.gui.widget.config_list;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.gui.widget.value_list.ValueListWidget;

import java.util.List;

public class ConfigListWidget extends ObjectSelectionList<ConfigListEntry> {

    private final ValueListWidget valueListWidget;

    public ConfigListWidget(Minecraft client, int width, int height, int yPos, int itemHeight, List<ModConfig> configList, ValueListWidget valueListWidget) {
        super(client, width, height, yPos, itemHeight);
        this.valueListWidget = valueListWidget;

        configList.forEach(this::addConfigEntries);
    }

    private void addConfigEntries(ModConfig config) {
        this.addEntry(new ConfigListEntry(config, this.valueListWidget));
    }

    @Override
    public int getWidth() {
        return this.width + 14;
    }

    @Override
    public int getRowWidth() {
        return this.width - 4;
    }
}
