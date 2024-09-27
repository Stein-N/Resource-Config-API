package net.xstopho.resourceconfigapi.api;

import java.util.List;
import java.util.Map;

public interface Config {
    Map<String, Object> getEntries();
    Config empty();

    void setValue(String key, Object object);
    <T> T get(String key, Class<T> clazz);
    <T> List<T> getAsList(String key, Class<T> clazz);
    <T> List<List<?>> getAsListOfLists(String key, Class<T> clazz);
}
