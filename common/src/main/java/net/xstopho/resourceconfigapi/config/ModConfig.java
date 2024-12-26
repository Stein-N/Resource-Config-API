package net.xstopho.resourceconfigapi.config;

import com.google.gson.*;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.api.ConfigType;
import net.xstopho.resourceconfigapi.platform.CoreServices;
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
    private final Map<Field, Object> defaultValueMap;

    public ModConfig(Class<?> clazz, ConfigType type, String modId) {
        this.clazz = clazz;
        this.modId = modId;

        this.configFile = new File(String.format("%s/%s/%s/%s.json",
                CoreServices.getConfigPath(),
                modId,
                type.name().toLowerCase(),
                clazz.getAnnotation(Config.class).fileName()));

        this.defaultValueMap = createDefaultValueMap();

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

    public void saveConfig() {
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

        if (field.getType().isPrimitive() && field.isAnnotationPresent(RangedEntry.class)) {
            if (field.getType() == char.class || field.getType() == Character.class) {
                Constants.LOG.error("You assign the RangedEntry to an Character, this will be ignored!");

            } else {
                RangedEntry annotation = field.getAnnotation(RangedEntry.class);
                Number objectNumber = (Number) obj;

                if (objectNumber != null && !inRange(objectNumber, annotation)) {
                    Constants.LOG.error("Value {} is not in range, using default Value!", field.getName());
                    obj = field.get(null);
                }
            }
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
                throw new IllegalStateException("Config entries must be static! Add the static Modifier to the value: " + field.getName());
            }
            if (Modifier.isFinal(field.getModifiers())) {
                throw new IllegalStateException("Config entries can't be final! Remove the final Modifier from value: " + field.getName());
            }

            ConfigEntry entry = field.getAnnotation(ConfigEntry.class);
            entries.put(field, entry);
        }

        return entries;
    }

    private Map<Field, Object> createDefaultValueMap() {
        Map<Field, Object> defaultValues = new HashMap<>();

        try {
            for (Field field : this.clazz.getDeclaredFields()) {
                defaultValues.put(field, field.get(null));
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Something went wrong while creating the default values map!");
        }

        return defaultValues;
    }

    public Object getDefaultValue(Field field) {
        if (this.defaultValueMap != null) {
            return this.defaultValueMap.get(field);
        }
        throw new IllegalStateException("Can't receive default Value for field: " + field.getName());
    }

    private boolean inRange(Number number, RangedEntry annotation) {
        return number.floatValue() <= annotation.maxValue() &&
                number.floatValue() >= annotation.minValue();
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
