package net.xstopho.resourceconfigapi.toml;

import de.stein_n.toml_lib4j.util.TomlHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Writes a {@link TomlConfig} object to a TOML file or string.
 */
@SuppressWarnings("unchecked")
public class TomlWriter {

    /**
     * Writes the given configuration to the specified file.
     *
     * @param config the configuration to write
     * @param file the file to write the configuration to
     */
    public void write(TomlConfig config, File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(writeToString(config));

        } catch(IOException e) {
            System.err.println("Something went wrong, while writing File '" + file.getName() + "'");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Converts the given configuration to a TOML formatted string.
     *
     * @param config the configuration to convert
     * @return the TOML formatted string
     */
    public String writeToString(TomlConfig config) {
        Map<String, Object> configEntries = config.getEntries();
        StringBuilder builder = new StringBuilder();

        for (Iterator<Map.Entry<String, Object>> iterator = configEntries.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, Object> configEntry = iterator.next();
            String key = configEntry.getKey();
            Object value = configEntry.getValue();

            if (key.contains(" ")) key = "\"" + key + "\"";

            if (TomlHelper.isPrimitive(value) || TomlHelper.isEnum(value)) writeObject(key, value, builder);
            if (value.getClass().isArray() || value instanceof Collection<?>) writeList(key, value, builder);
            if (value instanceof Map) writeMap(key, (Map<String, Object>) value, builder);

            if (iterator.hasNext()) builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Writes a key-value pair to the StringBuilder in TOML format.
     *
     * @param key the key
     * @param object the value
     * @param builder the StringBuilder to append the formatted string to
     */
    private void writeObject(String key, Object object, StringBuilder builder) {
        builder.append(key).append(" = ").append(TomlHelper.convertToString(object));
    }

    /**
     * Writes a collection to the StringBuilder in TOML format.
     *
     * @param key the key
     * @param list the collection
     * @param builder the StringBuilder to append the formatted string to
     */
    private void writeList(String key, Object list, StringBuilder builder) {
        if (validList(list)) {
            builder.append(key).append(" = [");
            writeList(list, builder);
            builder.append("]");
        } else throw new IllegalStateException("You can only nest Lists one Time!");
    }

    /**
     * Iterates through the Collection Entries and writes them as a List in the Toml Format.
     *
     * @param list
     * @param builder
     */
    private void writeList(Object list, StringBuilder builder) {
        if (list.getClass().isArray()) list = TomlHelper.arrayToList(list);
        for (Iterator<?> iterator = ((Collection<?>) list).iterator(); iterator.hasNext();) {
            Object value = iterator.next();

            if (value instanceof Map) throw new IllegalStateException("Lists cannot contain any type of Maps!");

            if (value.getClass().isArray() || value instanceof Collection<?>) {
                builder.append("[");
                writeList(value, builder);
                builder.append("]");
            } else {
                String stringValue = TomlHelper.convertToString(value);
                builder.append(stringValue);
            }
            if (iterator.hasNext()) builder.append(", ");
        }
    }

    /**
     * Writes a map to the StringBuilder in TOML format.
     *
     * @param category the category name
     * @param map the map to write
     * @param builder the StringBuilder to append the formatted string to
     */
    private void writeMap(String category, Map<String, Object> map, StringBuilder builder) {
        builder.append("[").append(category).append("]\n");

        for (Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey().contains(" ") ? "\"" + entry.getKey() + "\"" : entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) writeMap(key, (Map<String, Object>) value, builder);
            else {
                builder.append("\t");
                if (TomlHelper.isPrimitive(value) || TomlHelper.isEnum(value)) {
                    writeObject(key, value, builder);
                }
                if (value.getClass().isArray() || value instanceof Collection<?>) {
                    writeList(key, value, builder);
                }
            }
            if (iterator.hasNext()) builder.append("\n");
        }
    }

    /**
     *
     * @param object
     * @return
     */
    private boolean validList(Object object) {
        if (object instanceof List<?> list0) {
            if (list0.getFirst() instanceof List<?> list1) {
                return !(list1.getFirst() instanceof List<?> || list1.getFirst().getClass().isArray());
            }
        }
        return true;
    }
}