package net.xstopho.resourceconfigapi.config.values;

public abstract class ConfigValue<T> implements IConfigValue<T> {

    protected final T defaultValue;
    protected final String comment;
    protected String rangedComment;

    public ConfigValue(T defaultValue, String comment) {
        this.defaultValue = defaultValue;
        this.comment = comment;
    }

    @Override
    public T get() {
        return defaultValue;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public String getRangedComment() {
        if (hasComment()) return comment + "\n " + rangedComment;
        return " " + rangedComment;
    }

    @Override
    public boolean hasComment() {
        return nonEmpty(comment);
    }

    @Override
    public boolean isRanged() {
        return nonEmpty(rangedComment);
    }
}
