package net.xstopho.resource_config_api.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.InMemoryCommentedFormat;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import net.xstopho.resource_config_api.ResourceConstants;
import net.xstopho.resource_config_api.builder.IConfigBuilder;
import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModConfig {

    private final String modId;
    private final Path path;
    private final File file;
    private final IConfigBuilder builder;
    private final TomlWriter writer = new TomlWriter();

    private CommentedConfig config = CommentedConfig.inMemory();

    public ModConfig(String modId, String fileName, IConfigBuilder builder, Path path) {
        this.file = new File(path + "/" + fileName + ".toml");
        this.path = path;
        this.builder = builder;
        this.modId = modId;

        List<ConfigEntry<?>> entries = new ArrayList<>(builder.getEntries().values());

        readConfigFile();

        entries.forEach(this::readConfigValue);

        config = CommentedConfig.of(LinkedHashMap::new, InMemoryCommentedFormat.withUniversalSupport());

        entries.forEach(this::writeConfigValue);
        writeCategoryComments();
        writeConfigFile();

        entries.forEach(ConfigEntry::setLoaded);
    }

    void readConfigFile() {
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            config = new TomlParser().parse(reader);
        } catch (IOException | ParsingException e) {
            ResourceConstants.LOG.error("Reading '{}' from mod '{}' failed!\nError: {}", file.getName(), modId, e.getMessage());
        }
    }

    void writeConfigFile() {
        createFilePathIfNeeded(); // Create custom config path if needed
        writer.setIndentArrayElementsPredicate(values -> true); // write Lists as an actual List and not a Line
        writer.write(config, file, WritingMode.REPLACE);
    }

    <T> void readConfigValue(ConfigEntry<T> entry) {
        String path = entry.getPath();
        T defaultValue = entry.getConfigValue().get();

        if (!config.contains(path) || !entry.getConfigValue().validate(config.get(path))) {
            entry.setValue(defaultValue);
            ResourceConstants.LOG.error("Config Entry key '{}' isn't correct and is set to its default value '{}'!", path, defaultValue);
        } else entry.setValue(config.get(path));
    }

    void writeConfigValue(ConfigEntry<?> entry) {
        writeValueComment(entry);
        config.set(entry.getPath(), entry.value());
    }

    void writeValueComment(ConfigEntry<?> entry) {
        ConfigValue<?> value = entry.getConfigValue();

        if (value.hasRangedComment()) config.setComment(entry.getPath(), value.getRangedComment());
        else if (value.hasComment()) config.setComment(entry.getPath(), value.getComment());
    }

    void writeCategoryComments() {
        for (Map.Entry<String, String> comment : builder.getCategoryComments().entrySet()) {
            config.setComment(comment.getKey(), comment.getValue());
        }
    }

    void createFilePathIfNeeded() {
        if (new File(path.toString()).mkdirs()) {
            ResourceConstants.LOG.info("Created custom Config Path: '{}' for Mod: '{}'", path, modId);
        }
    }
}
