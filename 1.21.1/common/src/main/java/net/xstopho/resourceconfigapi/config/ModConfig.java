package net.xstopho.resourceconfigapi.config;

import com.google.gson.*;
import net.xstopho.resourceconfigapi.ConfigConstants;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.api.ConfigType;
import net.xstopho.resourceconfigapi.platform.PlatformHelper;
import net.xstopho.resourceconfigapi.util.ConfigUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ModConfig {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final boolean isServer = PlatformHelper.INSTANCE.isServer();
    public final ConfigType configType;
    private final File configFile;
    public final String modId;

    private final Map<Field, Object> defaultValueMap;
    public final Class<?> clazz;

    public ModConfig(Class<?> clazz, ConfigType configType, String modId) {
        this.configType = configType;
        this.clazz = clazz;
        this.modId = modId;

        Path configPath;
        if (PlatformHelper.INSTANCE.isServer() && configType.equals(ConfigType.SERVER)) {
            configPath = PlatformHelper.INSTANCE.getServerConfigDir();
        } else {
            configPath = PlatformHelper.INSTANCE.getConfigDir();
        }

        this.configFile = new File(String.format("%s/%s/%s/%s.json",
                configPath, modId,
                configType.name().toLowerCase(),
                clazz.getAnnotation(Config.class).fileName()));

        this.defaultValueMap = createDefaultValueMap();

        setup();
    }

    private void setup() {
        fromJson(readConfig());

        if (isServer && configType.equals(ConfigType.CLIENT)) skipConfig();
        if (!isServer && configType.equals(ConfigType.SERVER)) skipConfig();

        writeConfig(toJson());
    }

    private void skipConfig() {
        ConfigConstants.LOG.info("Config '{}' from type '{}' was skipped.", configFile.getName(), configType);
    }

    /**
     * Converts the given HashMap from getConfigEntries to a JsonObject, that can be used to save the config
     * to a json file or syncing the config with the client or server.
     * @return JsonObject of the declared Config Class
     */
    public JsonObject toJson() {
        JsonObject config = new JsonObject();

        for (Map.Entry<Field, ConfigEntry> entry : getConfigEntries().entrySet()) {
            ConfigEntry annotation = entry.getValue();
            Field field = entry.getKey();

            String category = ConfigUtils.isNotEmpty(annotation.category()) ? annotation.category() : null;

            if (ConfigUtils.unsupportedDatatype(field)) {
                if (PlatformHelper.INSTANCE.isDevEnv()) {
                    ConfigConstants.LOG.error("Field '{}' is an unsupported Datatype and was skipped. This message will be silent outside the Dev Environment, so make sure you resolve all messages!", field.getName());
                }
                continue;
            }

            JsonObject jsonObject = null;
            if (category != null && config.has(category)) {
                jsonObject = config.getAsJsonObject(category);
            } else if (category != null) {
                jsonObject = new JsonObject();
            }

            String value = field.getName();
            Object obj;
            try {
                obj = field.get(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field: " + value, e);
            }

            JsonElement jsonElement = gson.toJsonTree(obj);

            if (jsonObject != null) {
                jsonObject.add(value, jsonElement);
                config.add(category, jsonObject);
            } else {
                config.add(value, jsonElement);
            }
        }

        return config;
    }

    /**
     * Apply the given JsonObject to the declared fields.<br>
     * This method is used to apply the Config Values parsed from the config file or
     * apply the values that the server send to the client.
     * @param config config converted into a JsonObject
     */
    public void fromJson(JsonObject config) {
        for (Map.Entry<Field, ConfigEntry> entry : getConfigEntries().entrySet()) {
            ConfigEntry annotation = entry.getValue();
            Field field = entry.getKey();

            String category = ConfigUtils.isNotEmpty(annotation.category()) ? annotation.category() : null;

            JsonObject jsonObject = null;
            if (category != null && config.has(category)) {
                jsonObject = config.getAsJsonObject(category);
            }

            String value = field.getName();
            JsonElement jsonElement = jsonObject != null ? jsonObject.get(value) : config.get(value);

            if (jsonElement == null) {
                if (PlatformHelper.INSTANCE.isDevEnv()) {
                    ConfigConstants.LOG.error("Failed to set Value '{}'! Seems to be a new or unsupported Value. This message will be silent outside the Dev Environment, so make sure you resolve all messages!", value);
                }
                continue;
            }

            try {
                Object obj = readValue(jsonElement, field);
                field.set(field, obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to set Value for: " + value, e);
            }
        }
    }

    /**
     * Tries to read the Value from the given JsonElement, if the value isn't readable or out
     * of Range, only when it is a Number Value, the default Value will be returned.
     * @param jsonElement parsed Value
     * @param field field corresponding to the parsed value
     * @return parsed or default value.
     * @throws IllegalAccessException can be thrown when something went wrong by accessing the given field
     */
    private Object readValue(JsonElement jsonElement, Field field) throws IllegalAccessException {
        Object obj = null;
        try {
            obj = gson.fromJson(jsonElement, field.getType());
        } catch(JsonSyntaxException | IllegalStateException e) {
            ConfigConstants.LOG.error("Failed to read value '{}', value is set to its default!", field.getName());
        }

        if (field.isAnnotationPresent(RangedEntry.class) && field.getType().isPrimitive()) {
            if (field.getType() == char.class || field.getType() == Character.class) {
                ConfigConstants.LOG.error("Character with RangedEntry annotation found, this will be ignored");
            } else {
                RangedEntry annotation = field.getAnnotation(RangedEntry.class);
                Number number = (Number) obj;

                if (number != null && outOfRange(number, annotation)) {
                    ConfigConstants.LOG.error("Value {} is out of Range, using default Value!", field.getName());
                    obj = field.get(null);
                }
            }
        }

        return obj != null ? obj : field.get(null);
    }

    /**
     * Tries to parse the defined ConfigFile
     * @return File converted to JsonObject
     */
    public JsonObject readConfig() {
        if (!configFile.exists()) {
            return toJson();
        }
        try(FileReader reader = new FileReader(configFile)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch(IOException e) {
            throw new RuntimeException("Failed to parse config file: " + configFile.getName(), e);
        }
    }

    /**
     * Writes the given JsonObject to the defined Config File.
     * @param config Config as a JsonObject
     */
    public void writeConfig(JsonObject config) {
        configFile.getParentFile().mkdirs();
        try {
            FileUtils.write(configFile, gson.toJson(config), StandardCharsets.UTF_8);
        } catch(IOException e) {
            throw new RuntimeException("Failed to write config file: " + configFile.getName(), e);
        }
    }

    /**
     * Creates a HashMap with all declared fields and there ConfigEntry annotation. <br>
     * This is used to iterate through all valid fields and apply the values parsed
     * from the config file or create the config file.
     * @return HashMap containing all valid ConfigEntry Fields
     */
    private Map<Field, ConfigEntry> getConfigEntries() {
        Map<Field, ConfigEntry> entries = new HashMap<>();

        for (Field field : this.clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigEntry.class)) {
                ConfigConstants.LOG.error("Field '{}' isn't annotated as a ConfigEntry is this correct?", field.getName());
                continue;
            }
            if (!Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                throw new RuntimeException(String.format("Field '%s' isn't static or is final, make sure it is only a static field!", field.getName()));
            }

            ConfigEntry annotation = field.getAnnotation(ConfigEntry.class);
            entries.put(field, annotation);
        }

        return entries;
    }

    /**
     * Creates a HashMap with all declared fields to save the default Values. <br>
     * This is needed to handle the reset to default action within the ConfigScreen
     * @return HashMap containing all default Values
     */
    private Map<Field, Object> createDefaultValueMap() {
        Map<Field, Object> defaultValues = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            try {
                defaultValues.put(field, field.get(null));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Error creating default values map!", e);
            }
        }

        return defaultValues;
    }

    /**
     * Get the Default value of the given Field, those default values where saved at the init state of every ModConfig
     * @param field Given Field from this ModConfig
     * @return Default value as a generic Object
     */
    public Object getDefaultValue(Field field) {
        if (this.defaultValueMap != null) {
            return this.defaultValueMap.get(field);
        }
        throw new IllegalStateException("Can't receive default Value for field: " + field.getName());
    }

    /**
     * Method can be used to save the config dynamically at runtime.<br>
     * F.e. when a command changes a Config Value, this should be saved permanently.
     */
    public void save() {
        JsonObject config = this.toJson();
        this.writeConfig(config);
    }

    private boolean outOfRange(Number number, RangedEntry annotation) {
        return number.doubleValue() >= annotation.maxValue() &&
                number.doubleValue() <= annotation.minValue();
    }
}
