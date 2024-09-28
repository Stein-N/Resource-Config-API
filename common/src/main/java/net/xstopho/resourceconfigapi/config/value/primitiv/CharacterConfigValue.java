package net.xstopho.resourceconfigapi.config.value.primitiv;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class CharacterConfigValue extends ConfigValue<Character> {

    public CharacterConfigValue(Character defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Character;
        return predicate.test(value);
    }
}
