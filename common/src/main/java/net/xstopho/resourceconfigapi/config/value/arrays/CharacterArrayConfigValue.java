package net.xstopho.resourceconfigapi.config.value.arrays;

import net.xstopho.resourceconfigapi.config.value.ConfigValue;

import java.util.function.Predicate;

public class CharacterArrayConfigValue extends ConfigValue<Character[]> {
    public CharacterArrayConfigValue(Character[] defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean isValid(Object value) {
        Predicate<Object> predicate = o -> o instanceof Character[];
        return predicate.test(value);
    }
}
