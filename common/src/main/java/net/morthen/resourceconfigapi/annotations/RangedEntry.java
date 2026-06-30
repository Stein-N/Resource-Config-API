package net.morthen.resourceconfigapi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RangedEntry {

    /**
     * Minimum Value to the {@link ConfigEntry}
     * @return minimum value
     */
    double minValue();

    /**
     * Maximum Value to the {@link ConfigEntry}
     * @return maximum value
     */
    double maxValue();
}
