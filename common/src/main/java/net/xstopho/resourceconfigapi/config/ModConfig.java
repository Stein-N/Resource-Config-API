package net.xstopho.resourceconfigapi.config;

import com.google.gson.*;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.platform.CoreServices;
import net.xstopho.resourceconfigapi.util.ConfigType;
import net.xstopho.resourceconfigapi.util.ConfigUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ModConfig {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File configFile;
    private final String modId;

    private final Class<?> clazz;

    public ModConfig(Class<?> clazz, ConfigType type, String modId) {
        this.clazz = clazz;
        this.modId = modId;

        this.configFile = new File(String.format("%s/%s/%s/%s.json",
                CoreServices.getConfigPath(),
                modId,
                type.name().toLowerCase(),
                clazz.getAnnotation(Config.class).fileName()));

        setup();
    }

    private void setup() {
        if (configFile.exists()) {
            JsonObject config = readConfig();

            applyJsonObject(config);
        }

        saveConfig();
    }

    public void syncWithServerConfig(JsonObject config) {
        applyJsonObject(config);
    }

    private void saveConfig() {
        Map<Field, ConfigEntry> entries = getConfigEntries(this.clazz);
        JsonObject config = new JsonObject();

        for (Map.Entry<Field, ConfigEntry> entry : entries.entrySet()) {
            ConfigEntry annotation = entry.getValue();
            Field field = entry.getKey();

            String categoryKey = notEmpty(annotation.category()) ? annotation.category() : null;

            if (ConfigUtils.unsupportedDatatype(field)) {
                Constants.LOG.error("List and Maps aren't supported, Key '{}' was skipped", field.getName());
                continue;
            }

            JsonObject category = null;
            if (categoryKey != null && config.has(categoryKey)) {
                category = config.getAsJsonObject(categoryKey);
            } else if (categoryKey != null){
                category = new JsonObject();
            }

            String valueKey = field.getName();
            if ((category != null && category.has(valueKey)) || config.has(valueKey)) {
                throw new IllegalStateException("Something bad happened, duplicate key found: " + valueKey);
            }

            Object value;
            try {
                value = field.get(null);
            } catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            JsonElement valueElement = gson.toJsonTree(value);

            if (category != null) {
                category.add(field.getName(), valueElement);
                config.add(annotation.category(), category);
            } else {
                config.add(field.getName(), valueElement);
            }
        }

        writeConfig(config);
    }

    // TODO:    - Ranged annotation is ignored currently
    private void applyJsonObject(JsonObject config) {
        Map<Field, ConfigEntry> entries = getConfigEntries(this.clazz);

        for (Map.Entry<Field, ConfigEntry> entry : entries.entrySet()) {
            ConfigEntry annotation = entry.getValue();
            Field field = entry.getKey();

            String categoryKey = notEmpty(annotation.category()) ? annotation.category() : null;

            JsonObject category = null;
            if (categoryKey != null && config.has(categoryKey)) {
                category = config.getAsJsonObject(categoryKey);
            }

            String valueKey = field.getName();
            JsonElement valueElement = category != null ? category.get(valueKey) : config.get(valueKey);

            // If the category/value name was changed or an unsupported Datatype was parsed
            if (valueElement == null) {
                Constants.LOG.error("Failed to set Value for '{}'! The reason can be newly added Values, changed Category/Value name or unsupported Datatypes.",  valueKey);
                continue;
            }

            try {
                Object value;
                value = readValue(valueElement, field);

                field.set(field, value);
            } catch(IllegalAccessException e) {
                throw new RuntimeException("Failed to set Key: " + valueKey, e);
            }
        }
    }

    public JsonObject readConfig() {
        try(FileReader reader = new FileReader(configFile)) {
            return JsonParser.parseReader(reader).getAsJsonObject();

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Config File: " + configFile.getName(), e);
        }
    }

    private Object readValue(JsonElement value, Field field) throws IllegalAccessException {
        Object obj = null;
        try {
            obj = gson.fromJson(value, field.getType());
        } catch (JsonSyntaxException | IllegalStateException e) {
            Constants.LOG.error("Failed to read value for '{}', value is set to its default!", field.getName());
        }
        return obj != null ? obj : field.get(null);
    }

    @SuppressWarnings("all")
    private void writeConfig(JsonObject config) {
        final String json = gson.toJson(config);

        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdirs();
        }

        try {
            FileUtils.writeStringToFile(configFile, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write config file: " + configFile.getName(), e);
        }
    }

    private Map<Field, ConfigEntry> getConfigEntries(Class<?> clazz) {
        Map<Field, ConfigEntry> entries = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigEntry.class)) {
                continue;
            }
            if (!Modifier.isStatic(field.getModifiers())) {
                throw new IllegalStateException("Config entries must be static!");
            }
            ConfigEntry entry = field.getAnnotation(ConfigEntry.class);
            entries.put(field, entry);
        }

        return entries;
    }

    private boolean notEmpty(String string) {
        return string != null && !string.isBlank();
    }

    public String getModId() {
        return modId;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
