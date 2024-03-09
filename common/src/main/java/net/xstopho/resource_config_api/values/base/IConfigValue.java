package net.xstopho.resource_config_api.values.base;

import java.util.function.Supplier;

public interface IConfigValue<T> extends Supplier<T> {

    String getComment();
    String getRangedComment();

    boolean hasComment();
    boolean hasRangedComment();

    boolean isValid(Object value);
    boolean isRanged();
}
