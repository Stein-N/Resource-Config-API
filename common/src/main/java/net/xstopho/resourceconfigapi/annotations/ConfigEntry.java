package net.xstopho.resourceconfigapi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigEntry {

    String category() default "";
    String comment() default "";
    boolean sync() default false;

    @interface Ranged {

        int defaultValue();
        int min();
        int max();
    }
}
