package net.xstopho.resource_config_api.values.base;

public abstract class ConfigValue<T> implements IConfigValue<T> {

    public final T defaultValue;
    public final String comment;
    public String rangedComment;

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
        return validateComment(comment);
    }

    @Override
    public boolean hasRangedComment() {
        return validateComment(rangedComment);
    }

    @Override
    public boolean isRanged() {
        return hasRangedComment();
    }

    boolean validateComment(String comment) {
        return comment != null && !comment.isEmpty() && !comment.isBlank();
    }
}
