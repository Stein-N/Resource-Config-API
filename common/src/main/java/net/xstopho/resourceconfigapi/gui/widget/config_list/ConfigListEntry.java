package net.xstopho.resourceconfigapi.gui.widget.config_list;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.gui.widget.value_list.BaseEntry;
import net.xstopho.resourceconfigapi.gui.widget.value_list.CategoryEntry;
import net.xstopho.resourceconfigapi.gui.widget.value_list.ValueEntry;
import net.xstopho.resourceconfigapi.gui.widget.value_list.ValueListWidget;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConfigListEntry extends ObjectSelectionList.Entry<ConfigListEntry> {

    private final ValueListWidget valueListWidget;
    private LinkedList<BaseEntry> entryList = new LinkedList<>();
    private final Component fileName;

    public ConfigListEntry(ModConfig modConfig, ValueListWidget valueListWidget) {
        this.valueListWidget = valueListWidget;

        Config annotation = modConfig.getClazz().getAnnotation(Config.class);
        this.fileName = Component.literal(annotation.fileName());

        this.entryList = createEntries(modConfig.getClazz());
    }

    @Override
    public boolean mouseClicked(double p_331676_, double p_330254_, int p_331536_) {
        this.valueListWidget.replaceEntries(entryList);
        this.valueListWidget.setScrollAmount(0);
        return super.mouseClicked(p_331676_, p_330254_, p_331536_);
    }

    @Override
    public Component getNarration() {
        return fileName;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int rowWidth,
                                int rowHeight, int mouseX, int mouseY, boolean hovered, float delta) {
        guiGraphics.drawString(ConfigUtils.getFont(), fileName, xPos + 2, yPos + 2, -1, false);
    }

    private LinkedList<BaseEntry> createEntries(Class<?> clazz) {
        LinkedList<BaseEntry> entries = new LinkedList<>();
        String currentCategory = "";

        for (Field field : clazz.getDeclaredFields()) {
            ConfigEntry entry = field.getAnnotation(ConfigEntry.class);

            if (unsupportedDatatype(field)) continue;

            if (entry != null) {
                String fieldCategory = entry.category();
                if (notEmpty(fieldCategory) && !fieldCategory.equals(currentCategory)) {
                    currentCategory = fieldCategory;
                    entries.add(new CategoryEntry(currentCategory));
                }

                entries.add(new ValueEntry(field.getName(), field));
            }
        }

        return entries;
    }

    private boolean unsupportedDatatype(Field field) {
        try {
            Object value = field.get(null);
            if (value instanceof List<?> || value instanceof Map<?,?>) {
                return true;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    private boolean notEmpty(String string) {
        return string != null && !string.isBlank();
    }

    public LinkedList<BaseEntry> getEntryList() {
        return entryList;
    }
}
