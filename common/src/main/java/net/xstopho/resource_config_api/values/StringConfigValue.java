package net.xstopho.resource_config_api.values;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.function.Predicate;

public class StringConfigValue extends ConfigValue<String> {

    private final int min, max;

    public StringConfigValue(String defaultValue, String comment) {
        this(defaultValue, 0, 0, comment);
    }

    public StringConfigValue(String defaultValue, int minLength, int maxLength, String comment) {
        super(defaultValue, comment);
        this.min = minLength;
        this.max = maxLength;
    }

    @Override
    public String getRangedComment() {
        String comment = " Allowed Length: " + this.min + " ~ " + this.max + " chars.";
        if (isRanged() && hasComment()) return getComment() + "\n" + comment;
        if (isRanged()) return comment;
        return null;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isValid = o -> o instanceof String;

        if (isRanged() && isValid.test(value)) return value.toString().length() >= this.min && value.toString().length() <= this.max;
        else return !isRanged() && isValid.test(value);
    }

    @Override
    public boolean isRanged() {
        return !(min == 0 && max == 0);
    }
}
