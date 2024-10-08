package net.xstopho.resourceconfigapi.toml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class TomlWriter implements Serializable {

    public void write(TomlConfig config, File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(writeToString(config));

        } catch(IOException e) {
            System.err.println("Something went wrong, while writing File '" + file.getName() + "'");
            System.err.println(e.getMessage());
        }
    }

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

    private void writeObject(String key, Object object, StringBuilder builder) {
        builder.append(key).append(" = ").append(TomlHelper.convertToString(object));
    }

    private void writeList(String key, Object list, StringBuilder builder) {
        if (validList(list)) {
            builder.append(key).append(" = [");
            writeList(list, builder);
            builder.append("]");
        } else throw new IllegalStateException("You can only nest Lists one Time!");
    }

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

    private boolean validList(Object object) {
        if (object instanceof List<?> list0) {
            if (list0.getFirst() instanceof List<?> list1) {
                return !(list1.getFirst() instanceof List<?> || list1.getFirst().getClass().isArray());
            }
        }
        return true;
    }
}