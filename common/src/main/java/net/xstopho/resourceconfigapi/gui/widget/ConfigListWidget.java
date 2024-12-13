package net.xstopho.resourceconfigapi.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.xstopho.resourceconfigapi.config.ModConfig;

import java.util.List;

public class ConfigListWidget extends ObjectSelectionList<ConfigListEntry> {

    public ConfigListWidget(Minecraft client, int width, int height, int yPos, int itemHeight, List<ModConfig> configList) {
        super(client, width, height, yPos, itemHeight);

        configList.forEach(this::addConfigEntries);
    }

    private void addConfigEntries(ModConfig config) {
        this.addEntry(new ConfigListEntry(config.getClazz()));
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
