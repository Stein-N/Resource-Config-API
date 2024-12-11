package net.xstopho.resourceconfigapi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigEntry {

    String category() default "";
    String comment() default "";

    @interface Ranged {

        int defaultValue();
        int min();
        int max();
    }
}
