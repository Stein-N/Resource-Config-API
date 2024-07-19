package net.xstopho.resourceconfigapi.config.values;

import java.util.function.Supplier;

public interface IConfigValue<T> extends Supplier<T> {

    String getComment();
    String getRangedComment();

    boolean hasComment();

    boolean isValid(Object value);
    boolean isRanged();

    default boolean nonEmpty(String comment) {
        return comment != null && !comment.isEmpty() && !comment.isBlank();
    }
}
