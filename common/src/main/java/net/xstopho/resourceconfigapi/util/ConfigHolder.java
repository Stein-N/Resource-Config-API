package net.xstopho.resourceconfigapi.util;

import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.gui.widget.value_list.base.BaseEntry;
import net.xstopho.resourceconfigapi.gui.widget.value_list.entries.CategoryEntry;
import net.xstopho.resourceconfigapi.gui.widget.value_list.base.ValueEntry;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class ConfigHolder {

    private final ModConfig config;
    private final String fileName;

    private final LinkedList<BaseEntry> entryList;

    public ConfigHolder(ModConfig config) {
        this.config = config;

        Config annotation = config.getClazz().getAnnotation(Config.class);
        this.fileName = annotation.fileName();

        this.entryList = createEntries();
    }

    private LinkedList<BaseEntry> createEntries() {
        LinkedList<BaseEntry> entries = new LinkedList<>();
        String currentCategory = "";

        for (Field field : config.getClazz().getDeclaredFields()) {
            ConfigEntry entry = field.getAnnotation(ConfigEntry.class);

            if (ConfigUtils.unsupportedDatatype(field)) continue;

            if (entry != null) {
                String fieldCategory = entry.category();
                if (notEmpty(fieldCategory) && !fieldCategory.equals(currentCategory)) {
                    currentCategory = fieldCategory;
                    entries.add(new CategoryEntry(fileName, currentCategory));
                }

                String translationKey = notEmpty(entry.translation()) ? entry.translation() : field.getName();
                entries.add(new ValueEntry(fileName, translationKey, field, config.getDefaultValue(field)));
            }
        }

        return entries;
    }

    public LinkedList<BaseEntry> getEntryList() {
        return entryList;
    }

    public ModConfig getConfig() {
        return config;
    }

    public String getFileName() {
        return fileName;
    }

    private boolean notEmpty(String string) {
        return string != null && !string.isBlank();
    }
}
