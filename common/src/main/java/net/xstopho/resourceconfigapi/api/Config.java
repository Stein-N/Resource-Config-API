package net.xstopho.resourceconfigapi.api;

import net.xstopho.resourceconfigapi.toml.TomlConfig;

import java.util.List;
import java.util.Map;

public interface Config {
    Map<String, Object> getEntries();
    TomlConfig empty();
    boolean contains(String key);

    void setValue(String key, Object object);
    <T> T get(String key, Class<T> clazz);
    <T> List<T> getAsList(String key, Class<T> clazz);
    <T> T[] getAsArray(String key, Class<T> clazz);
}
