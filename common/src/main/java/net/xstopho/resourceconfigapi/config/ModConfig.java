package net.xstopho.resourceconfigapi.config;

import com.google.gson.*;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.platform.CoreServices;
import net.xstopho.resourceconfigapi.util.ConfigType;
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

        this.configFile = new File(CoreServices.getConfigPath() + "/" + modId + "/" + type.name().toLowerCase(),
                clazz.getAnnotation(Config.class).fileName() + ".json");

        setup();
    }

    private void setup() {
        if (configFile.exists()) {
            Map<Field, ConfigEntry> entries = getConfigEntries(this.clazz);

            JsonObject config = readConfig();

            for (Map.Entry<Field, ConfigEntry> entry : entries.entrySet()) {
                Field field = entry.getKey();
                ConfigEntry annotation = entry.getValue();

                JsonObject category = null;
                if (notEmpty(annotation.category()) && config.has(annotation.category())) {
                    category = config.getAsJsonObject(annotation.category());
                }

                String key = field.getName();
                JsonObject valueObject;
                if (category != null) {
                    valueObject = category.getAsJsonObject(key);
                } else {
                    valueObject = config.getAsJsonObject(key);
                }

                try {
                    Object value = null;
                    if (valueObject != null) {
                        value = readValue(valueObject.get("value"), field);
                    }

                    if (value != null) {
                        field.set(field, value);
                    }

                } catch(IllegalAccessException e) {
                    throw new RuntimeException("Failed to set Key: " + key, e);
                }
            }

        }

        saveConfig();
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

    private void saveConfig() {
        Map<Field, ConfigEntry> entries = getConfigEntries(this.clazz);
        JsonObject config = new JsonObject();

        for (Map.Entry<Field, ConfigEntry> entry : entries.entrySet()) {
            Field field = entry.getKey();
            ConfigEntry annotation = entry.getValue();

            JsonObject category = null;
            if (notEmpty(annotation.category()) && config.has(annotation.category())) {
                category = config.getAsJsonObject(annotation.category());
            } else if (notEmpty(annotation.category())){
                category = new JsonObject();
            }

            String key = field.getName();
            if (category != null && category.has(key)) {
                throw new IllegalStateException("Something bad happened, duplicate key found: " + key);
            }

            JsonObject valueObject = new JsonObject();
            if (notEmpty(annotation.comment())) {
                valueObject.addProperty("comment", annotation.comment());
            }

            Object value;
            try {
                value = field.get(null);
            } catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            JsonElement valueElement = gson.toJsonTree(value);
            valueObject.add("value", valueElement);

            if (category != null) {
                category.add(key, valueObject);
                config.add(annotation.category(), category);
            } else {
                config.add(key, valueObject);
            }
        }

        writeConfig(config);
    }

    public void syncWithServerConfig(JsonObject config) {
        Map<Field, ConfigEntry> entries = getConfigEntries(this.clazz);

        for (Map.Entry<Field, ConfigEntry> entry : entries.entrySet()) {
            Field field = entry.getKey();
            ConfigEntry annotation = entry.getValue();

            JsonObject category = null;
            if (notEmpty(annotation.category()) && config.has(annotation.category())) {
                category = config.getAsJsonObject(annotation.category());
            }

            String key = field.getName();
            JsonObject valueObject;
            if (category != null) {
                valueObject = category.getAsJsonObject(key);
            } else {
                valueObject = config.getAsJsonObject(key);
            }

            try {
                Object value = null;
                if (valueObject != null) {
                    value = readValue(valueObject.get("value"), field);
                }

                if (value != null) {
                    field.set(field, value);
                }

            } catch(IllegalAccessException e) {
                throw new RuntimeException("Failed to set Key: " + key, e);
            }
        }
    }

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

    public JsonObject readConfig() {
        try(FileReader reader = new FileReader(configFile)) {
            return JsonParser.parseReader(reader).getAsJsonObject();

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Config File: " + configFile.getName(), e);
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
