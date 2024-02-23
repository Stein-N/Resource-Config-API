package net.xstopho.resource_config_api.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.InMemoryCommentedFormat;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import net.xstopho.resource_config_api.ResourceConstants;
import net.xstopho.resource_config_api.builder.IConfigBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModConfig {

    private final String modId, filePath;
    private final File file;
    private final IConfigBuilder builder;
    private final TomlWriter writer = new TomlWriter();

    private CommentedConfig config = CommentedConfig.inMemory();

    public ModConfig(String modId, String fileName, IConfigBuilder builder, String filePath) {
        this.file = new File(filePath + "/" + fileName + ".toml");
        this.filePath = filePath;
        this.builder = builder;
        this.modId = modId;

        List<ConfigEntry<?>> entries = new ArrayList<>(builder.getEntries().values());

        readConfigFile();

        entries.forEach(this::readConfigValue);

        this.config = CommentedConfig.of(LinkedHashMap::new, InMemoryCommentedFormat.withUniversalSupport());

        entries.forEach(this::writeConfigValue);
        writeCategoryComments();
        writeConfigFile();

        entries.forEach(configEntry -> configEntry.isLoaded = true);
    }

    void readConfigFile() {
        if (!this.file.exists()) return;

        try (FileReader reader = new FileReader(this.file)) {
            this.config = new TomlParser().parse(reader);
        } catch (IOException | ParsingException e) {
            ResourceConstants.LOG.error("Reading '{}' from mod '{}' failed!\nError: {}", this.file.getName(), this.modId, e.getMessage());
        }
    }

    void writeConfigFile() {
        createFilePathIfNeeded(this.filePath); // Create custom config path if needed
        writer.setIndentArrayElementsPredicate(values -> true); // write Lists as an actual List and not a Line
        writer.write(this.config, this.file, WritingMode.REPLACE);
    }

    <T> void readConfigValue(ConfigEntry<T> entry) {
        String path = entry.path;
        if (!this.config.contains(path)) entry.value = entry.configValue.get();
        else {
            T value = this.config.get(path);
            if (value != null && entry.configValue.validate(value)) entry.value = value;
            else {
                entry.value = entry.configValue.get();
                ResourceConstants.LOG.error("Config Entry key '{}' isn't correct and is set to its default value '{}'!", path, entry.configValue.get());
            }
        }
    }

    void writeConfigValue(ConfigEntry<?> entry) {
        writeValueComment(entry);
        this.config.set(entry.path, entry.value);
    }

    void writeValueComment(ConfigEntry<?> entry) {
        if (entry.configValue.getRangedComment() == null) {
            this.config.setComment(entry.path, entry.configValue.getComment());
        } else this.config.setComment(entry.path, entry.configValue.getRangedComment());
    }

    void writeCategoryComments() {
        for (Map.Entry<String, String> comment : this.builder.getCategoryComments().entrySet()) {
            this.config.setComment(comment.getKey(), comment.getValue());
        }
    }

    void createFilePathIfNeeded(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) file.mkdirs();
    }
}
