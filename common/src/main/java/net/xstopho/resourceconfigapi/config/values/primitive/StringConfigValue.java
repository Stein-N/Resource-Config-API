package net.xstopho.resourceconfigapi.config.values.primitive;

import net.xstopho.resourceconfigapi.config.values.ConfigValue;

import java.util.function.Predicate;

public class StringConfigValue extends ConfigValue<String> {

    private int min, max;

    public StringConfigValue(String defaultValue, String comment) {
        super(defaultValue, comment);
    }

    public StringConfigValue(String defaultValue, String comment, int minLength, int maxLength) {
        super(defaultValue, comment);
        this.min = minLength; this.max = maxLength;

        this.rangedComment = "Allowed Length: " + this.min + " ~ " + this.max + " chars.";
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof String;

        if (isRanged() && predicate.test(value)) return value.toString().length() >= this.min && value.toString().length() <= this.max;
        else return !isRanged() && predicate.test(value);
    }
}
