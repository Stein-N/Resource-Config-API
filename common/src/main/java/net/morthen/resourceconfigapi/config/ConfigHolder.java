package net.morthen.resourceconfigapi.config;

import net.morthen.resourceconfigapi.annotations.Config;
import net.morthen.resourceconfigapi.annotations.ConfigEntry;
import net.morthen.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.morthen.resourceconfigapi.client.gui.widget.value_list.base.BaseEntry;
import net.morthen.resourceconfigapi.client.gui.widget.value_list.entries.CategoryEntry;
import net.morthen.resourceconfigapi.client.util.ValueEntryCreator;
import net.morthen.resourceconfigapi.util.ConfigUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class ConfigHolder {

    private final ModConfig config;
    private final String fileName;

    private final LinkedList<BaseEntry> entryList;

    public ConfigHolder(ModConfig config, ResourceConfigScreen screen) {
        this.config = config;

        Config annotation = config.clazz.getAnnotation(Config.class);
        this.fileName = annotation.fileName();

        this.entryList = createEntries(screen);
    }

    private LinkedList<BaseEntry> createEntries(ResourceConfigScreen screen) {
        LinkedList<BaseEntry> entries = new LinkedList<>();
        String currentCategory = "";

        for (Field field : config.clazz.getDeclaredFields()) {
            ConfigEntry entry = field.getAnnotation(ConfigEntry.class);

            if (ConfigUtils.unsupportedDatatype(field)) continue;

            if (entry != null) {
                String fieldCategory = entry.category();
                if (notEmpty(fieldCategory) && !fieldCategory.equals(currentCategory)) {
                    currentCategory = fieldCategory;
                    entries.add(new CategoryEntry(screen, getModId(), fileName, currentCategory));
                }

                String translationKey = notEmpty(entry.translation()) ? entry.translation() : field.getName();
                entries.add(ValueEntryCreator.create(screen, getModId(), fileName, translationKey, field, config.getDefaultValue(field)));
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

    public String getModId() {
        return config.modId;
    }

    private boolean notEmpty(String string) {
        return string != null && !string.isBlank();
    }
}
