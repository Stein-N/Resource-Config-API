package net.xstopho.resource_config_api.values;

import net.xstopho.resource_config_api.values.base.ConfigValue;

import java.util.function.Predicate;

public class CharConfigValue extends ConfigValue<Character> {
    public CharConfigValue(Character defaultValue, String comment) {
        super(defaultValue, comment);
    }

    @Override
    public String getRangedComment() {
        String comment = "This is a char value";
        if (hasComment()) return getComment() + "\n" + comment;
        else return comment;
    }

    @Override
    public boolean validate(Object value) {
        Predicate<Object> isValid = o -> o instanceof Character;
        return isValid.test(value);
    }

    @Override
    public boolean isRanged() {
        return false;
    }
}
