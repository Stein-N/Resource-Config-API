package net.xstopho.resourceconfigapi.client.gui.widget.config_list;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.ValueListWidget;
import net.xstopho.resourceconfigapi.config.ConfigHolder;

import java.util.List;

public class ConfigListWidget extends ObjectSelectionList<ConfigListEntry> {

    private final ValueListWidget valueListWidget;

    public ConfigListWidget(int width, int height, int yPos, int itemHeight, List<ConfigHolder> configHolderList, ValueListWidget valueListWidget) {
        super(Minecraft.getInstance(), width, height, yPos, itemHeight);
        this.valueListWidget = valueListWidget;

        configHolderList.forEach(this::processConfigs);
    }

    private void processConfigs(ConfigHolder configHolder) {
        ConfigListEntry entry = new ConfigListEntry(configHolder, this.valueListWidget);
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

    public void setSelectedIndex(int selected) {
        if (selected == -1) {
            this.setSelected(null);
        } else if (this.getItemCount() != 0) {
            this.setSelected(this.getEntry(selected));
        }
    }
}
