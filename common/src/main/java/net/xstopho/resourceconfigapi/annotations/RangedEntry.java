package net.xstopho.resourceconfigapi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RangedEntry {

    float minValue();
    float maxValue();
}
