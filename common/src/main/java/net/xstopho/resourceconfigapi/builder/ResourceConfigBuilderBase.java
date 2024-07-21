package net.xstopho.resourceconfigapi.builder;

import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.config.values.ConfigValue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class ResourceConfigBuilderBase implements IResourceConfigBuilder {

    private String category, comment;
    private boolean sync;
    private String translation;
    private Map<String, ConfigEntry<?>> entries = new LinkedHashMap<>();
    private Map<String, String> categoryComments = new LinkedHashMap<>();

    @Override
    public Map<String, ConfigEntry<?>> getEntries() {
        return entries;
    }

    @Override
    public Map<String, String> getCategoryComment() {
        return categoryComments;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public IResourceConfigBuilder push(String category) {
        if (isEmpty(this.category)) {
             this.categoryComments.put(category, this.comment);
             this.category = category;
             this.comment = null;
        } else throw new IllegalArgumentException("Category '" + this.category + "' is already set! Use pop() before pushing a new Category.");
        return this;
    }

    @Override
    public IResourceConfigBuilder pop() {
        if (isEmpty(category)) throw new IllegalArgumentException("Failed to remove Category, because there isn't a Category to remove!");
        else this.category = null;
        return this;
    }

    @Override
    public IResourceConfigBuilder comment(String comment) {
        if (isEmpty(this.comment)) this.comment = " " + comment;
        else this.comment += "\n " + comment;
        return this;
    }

    @Override
    public IResourceConfigBuilder translation(String key) {
        if (translation != null) translation = key;
        else throw new IllegalArgumentException("You already defined an translation key!");

        return this;
    }

    @Override
    public IResourceConfigBuilder sync() {
        this.sync = true;
        return this;
    }

    <T> Supplier<T> createEntry(String path, ConfigValue<T> configValue) {
        if (this.entries.containsKey(path)) throw new IllegalStateException("Key '" + path + "' is already defined!");

        ConfigEntry<T> entry = new ConfigEntry<>(path, configValue, translation, sync);
        addEntry(path, entry);

        return entry::getValue;
    }

    private void addEntry(String path, ConfigEntry<?> configEntry) {
        this.entries.put(path, configEntry);
        this.sync = false;
        this.comment = null;
    }

    protected String createKey(String key) {
        if (isEmpty(key)) throw new IllegalArgumentException("Key can't be null or empty!");
        if (!isEmpty(category)) return category + "." + key;
        return key;
    }
}
