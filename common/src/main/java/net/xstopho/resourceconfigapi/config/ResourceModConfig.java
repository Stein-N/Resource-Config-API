package net.xstopho.resourceconfigapi.config;

import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.api.ConfigType;
import net.xstopho.resourceconfigapi.config.builder.IResourceConfigBuilder;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.platform.Services;
import net.xstopho.resourceconfigapi.toml.TomlConfig;
import net.xstopho.resourceconfigapi.toml.TomlParser;
import net.xstopho.resourceconfigapi.toml.TomlWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ResourceModConfig {

    private final String modId;
    private final File file;
    private final IResourceConfigBuilder builder;
    private final ConfigType type;

    private TomlConfig config = new TomlConfig();

    public ResourceModConfig(String modId, IResourceConfigBuilder builder, ConfigType type) {
        this.file = new File(Services.getConfigPath() + "/" + modId + "/" + modId + "_" + type + ".toml");
        this.builder = builder;
        this.modId = modId;
        this.type = type;
        createFile(file);

        readConfigFile();
        List<ConfigEntry<?>> entries = new ArrayList<>(builder.getEntries().values());
        entries.forEach(this::readConfigValues);

        config = new TomlConfig();

        entries.forEach(this::writeConfigValues);

        writeConfigFile();

        entries.forEach(ConfigEntry::setLoaded);
    }

    void readConfigFile() {
        if (!file.exists()) return;
        config = new TomlParser().parse(Path.of(file.getPath()));
    }

    void writeConfigFile() {
        new TomlWriter().write(config, file);
    }

    void writeConfigValues(ConfigEntry<?> entry) {
        config.setValue(entry.getPath(), entry.value());
    }

    <T> void readConfigValues(ConfigEntry<T> entry) {
        String path = entry.getPath();
        T defaultVale = entry.getConfigValue().get();
        Class<?> clazz = defaultVale.getClass();

        if (!config.contains(path) || !entry.getConfigValue().isValid(config.get(path, clazz))) {
            entry.setValue(defaultVale);
            ResourceConfigConstants.LOG.error("Config Entry '{}' isn't correct ans is set to its default value '{}'", path, defaultVale);
        } else {
            entry.setValue((T) config.get(path, clazz));
        }
    }

    void createFile(File file) {
        try {
            if (file.createNewFile()) {
                ResourceConfigConstants.LOG.info("Created Config for Mod: {} in Path: {}", modId, file.getPath());
            }
        } catch(IOException e) {
            ResourceConfigConstants.LOG.error("Error while creating Config File: {} for Mod: {}\nError Message: {}", file.getName(), modId, e.getMessage());
        }
    }
}
