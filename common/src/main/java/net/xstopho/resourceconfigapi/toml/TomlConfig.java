package net.xstopho.resourceconfigapi.toml;


import net.xstopho.resourceconfigapi.api.Config;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("unchecked")
public class TomlConfig implements Config, Serializable {

    private final String INVALID_KEY = "Your key contains illegal characters. Allowed are: a-z, A-Z, whitespace and underscore!";
    private final String KEY_NOT_PRESENT = "The given key isn't present in your Config!";
    private final String NOT_LIST = "The Value you want to get isn't an instance of Collection!";
    private final String NOT_NESTED_LIST = "The Value you want to get isn't a nested List";

    private final Map<String, Object> entries = new LinkedHashMap<>();

    @Override
    public Map<String, Object> getEntries() {
        return entries;
    }

    @Override
    public TomlConfig empty() {
        return new TomlConfig();
    }

    @Override
    public void setValue(String key, Object object) {
        if (invalidKey(key)) throw new IllegalArgumentException(INVALID_KEY);

        if (key.contains(".")) {
            String[] parts = key.split("\\.");
            int partLength = parts.length;
            Map<String, Object> categoryMap = entries.containsKey(parts[0]) ? (Map<String, Object>) entries.get(parts[0]) : new LinkedHashMap<>();

            if (partLength > 2) {
                String path = createKey(parts);

                Map<String, Object> subCategoryMap = categoryMap.containsKey(path) ? (Map<String, Object>) categoryMap.get(path) : new LinkedHashMap<>();
                subCategoryMap.put(parts[partLength - 1], object);
                categoryMap.put(path, subCategoryMap);
            } else {
                categoryMap.put(parts[1], object);
            }
            entries.put(parts[0], categoryMap);
        } else {
            entries.put(key, object);
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        if (invalidKey(key)) throw new IllegalArgumentException(INVALID_KEY);

        Map<String, Object> valueMap = getValueMap(key);
        String valueName = getValueName(key);

        if (valueMap != null) {
            return (T) convert(valueMap.get(valueName), clazz);
        } else {
            if (entries.containsKey(key)) {
                return (T) convert(entries.get(key), clazz);
            } else throw new IllegalStateException(KEY_NOT_PRESENT);
        }
    }

    @Override
    public <T> List<T> getAsList(String key, Class<T> clazz) {
        if (invalidKey(key)) throw new IllegalArgumentException(INVALID_KEY);

        Map<String, Object> valueMap = getValueMap(key);
        String valueName = getValueName(key);

        List<T> list = null;

        if (valueMap != null) {
            if (valueMap.get(valueName) instanceof Collection<?>) {
                list = convertListEntries(valueMap.get(valueName), clazz);
            } else throw new IllegalStateException(NOT_LIST);
        }

        if (entries.containsKey(key)) {
            if (entries.get(valueName) instanceof Collection<?>) {
                list = convertListEntries(entries.get(key), clazz);
            }
        }

        if (list != null) return list;
        else throw new IllegalStateException(KEY_NOT_PRESENT);
    }

    @Override
    public <T> List<List<?>> getAsListOfLists(String key, Class<T> clazz) {
        if (invalidKey(key)) throw new IllegalArgumentException(INVALID_KEY);


        Map<String, Object> valueMap = getValueMap(key);
        String valueName = getValueName(key);

        List<List<?>> list = null;

        if (valueMap != null) {
            list = new ArrayList<>();
            if (valueMap.get(valueName) instanceof List<?> valueList) {
                if (valueList.getFirst() instanceof List<?>) {
                    for (Object insideList : valueList) {
                        list.add(convertListEntries(insideList, clazz));
                    }
                } else throw new IllegalStateException(NOT_NESTED_LIST);
            }
        }

        if (entries.containsKey(key)) {
            list = new ArrayList<>();
            if (entries.get(key) instanceof List<?> valueList) {
                if (valueList.getFirst() instanceof List<?>) {
                    for (Object insideList : valueList) {
                        list.add(convertListEntries(insideList, clazz));
                    }
                } else throw new IllegalStateException(NOT_NESTED_LIST);
            }
        }


        if (list != null) return list;
        else throw new IllegalStateException(KEY_NOT_PRESENT);
    }


    private Map<String, Object> getValueMap(String key) {
        if (invalidKey(key)) throw new IllegalArgumentException(INVALID_KEY);

        Map<String, Object> valueMap = null;

        if (key.contains(".")) {
            String[] parts = key.split("\\.");
            if (entries.containsKey(parts[0])) {
                valueMap = (Map<String, Object>) entries.get(parts[0]);
            }

            if (parts.length > 2 && valueMap != null) {
                if (valueMap.containsKey(createKey(parts))) {
                    valueMap = (Map<String, Object>) valueMap.get(createKey(parts));
                }
            }
        }

        return valueMap;
    }

    private Object convert(Object object, Class<?> clazz) {
        if (TomlHelper.isPrimitive(clazz)) return TomlHelper.convertToPrimitive(object.toString(), clazz);
        if (clazz.isEnum()) return TomlHelper.convertToEnum(object.toString(), clazz);
        return object.toString();
    }

    private <T> List<T> convertListEntries(Object object, Class<T> clazz) {
        if (object instanceof Collection<?>) {
            List<T> list = (List<T>) object;
            list.replaceAll(t -> (T) convert(t, clazz));

            return list;
        } else throw new IllegalStateException(NOT_LIST);
    }


    private String createKey(String[] parts) {
        StringBuilder path = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            if (i == 0) path.append(parts[i]);
            else path.append(".").append(parts[i]);
        }
        return path.toString();
    }

    private String getValueName(String key) {
        String[] parts = key.split("\\.");
        return parts[parts.length - 1];
    }

    private boolean invalidKey(String key) {
        return !key.matches("[a-zA-Z_\\s.]+");
    }
}
