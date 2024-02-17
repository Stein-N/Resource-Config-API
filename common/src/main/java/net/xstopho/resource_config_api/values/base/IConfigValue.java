package net.xstopho.resource_config_api.values.base;

import java.util.function.Supplier;

public interface IConfigValue<T> extends Supplier<T> {

    String getComment();
    String getRangedComment();
    boolean validate(Object value);
    boolean isRanged();

    boolean hasComment();
}
