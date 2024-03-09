package net.xstopho.resource_config_api.values.base;

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
        if (hasComment()) return comment + '\n' + rangedComment;
        else return rangedComment;
    }

    @Override
    public boolean hasComment() {
        return validComment(comment);
    }

    @Override
    public boolean hasRangedComment() {
        return validComment(rangedComment);
    }

    @Override
    public boolean isRanged() {
        return hasRangedComment();
    }

    boolean validComment(String comment) {
        return comment != null && !comment.isEmpty() && !comment.isBlank();
    }
}
