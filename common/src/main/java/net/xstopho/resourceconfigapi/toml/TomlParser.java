package net.xstopho.resourceconfigapi.toml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class TomlParser {

    public static TomlConfig parse(Path filePath) {
        try (Stream<String> lines = Files.lines(filePath)) {
            return parse(lines.toList());

        } catch(IOException e) {
            System.err.println("Something went wrong while parsing/reading file: " + filePath);
            System.err.println(e.getMessage());
            return new TomlConfig();
        }
    }

    public TomlConfig parse(String fileContent) {
        String[] lines = fileContent.split("\n");
        return parse(Arrays.asList(lines));
    }

    private static TomlConfig parse(List<String> lines) {
        TomlConfig config = new TomlConfig();
        Map<String, Object> currentMap = null;
        String mapKey = "";

        for (String line : lines) {
            if (line.isEmpty()) continue;

            char firstChar = line.charAt(0);

            // Create new Table/Map
            if (firstChar == '[') {
                if (currentMap != null) {
                    config.setValue(mapKey, currentMap);
                }
                currentMap = new LinkedHashMap<>();
                mapKey = getRawKey(line);
                continue;
            }

            // Parse Table/Map Values
            if (firstChar == ' ' && currentMap != null) {
                parseMap(currentMap, line);
                continue;
            }

            if (Character.isLetter(firstChar) || firstChar == '"') {
                if (currentMap != null && mapKey.isEmpty()) {
                    config.setValue(mapKey, currentMap);
                    currentMap = null;
                    mapKey = "";
                } else {
                    parseValue(config, line);
                }
            }
        }
        if (currentMap != null) config.setValue(mapKey, currentMap);

        return config;
    }

    private static void parseMap(Map<String, Object> currentMap, String line) {
        String[] parts = line.split("=");
        String key = getRawKey(parts[0]);
        if (parts[1].contains("[")) {
            currentMap.put(key, parseList(parts[1]));
        } else {
            currentMap.put(key, getRawKey(parts[1]));
        }
    }

    private static void parseValue(TomlConfig config, String line) {
        String[] parts = line.split("=");

        String key = getRawKey(parts[0]);

        if (parts[1].contains("[")) {
            config.setValue(key, parseList(parts[1]));
        } else {
            config.setValue(key,  getRawKey(parts[1]));
        }
    }

    private static List<Object> parseList(String value) {
        List<Object> list = new ArrayList<>();

        if (value.contains("[[")) {
            return parseArrayList(value);
        } else {
            String[] parts = getRawKey(value).split(",");

            for (String part : parts) {
                list.add(getRawKey(part));
            }

            return list;
        }
    }

    private static List<Object> parseArrayList(String value) {
        List<Object> list = new ArrayList<>();
        String[] parts = value.split("],");

        for (String part : parts) {
            List<Object> arrayList = new ArrayList<>();
            String[] arrayParts = getRawKey(part).split(",");
            for (String arrayPart : arrayParts) {
                arrayList.add(getRawKey(arrayPart));
            }
            list.add(arrayList);
        }

        return list;
    }

    private static String getRawKey(String value) {
        if (value.contains("[")) value = value.replace("[", "");
        if (value.contains("]")) value = value.replace("]", "");
        if (value.contains("\"")) value = value.replace("\"", "");
        if (value.contains("'")) value = value.replace("'", "");
        return value.trim();
    }
}