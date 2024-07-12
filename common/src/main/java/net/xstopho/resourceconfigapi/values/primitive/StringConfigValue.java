package net.xstopho.resourceconfigapi.values.primitive;

import net.xstopho.resourceconfigapi.values.base.ConfigValue;

import java.util.function.Predicate;

public class StringConfigValue extends ConfigValue<String> {

    private int min, max;

    public StringConfigValue(String defaultValue, String comment) {
        super(defaultValue, comment);
    }

    public StringConfigValue(String defaultValue, int minLength, int maxLength, String comment) {
        super(defaultValue, comment);
        this.min = minLength; this.max = maxLength;

        this.rangedComment = " Allowed Length: " + this.min + " ~ " + this.max + " chars.";
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof String;

        if (isRanged() && predicate.test(value)) return value.toString().length() >= this.min && value.toString().length() <= this.max;
        else return !isRanged() && predicate.test(value);
    }
}
