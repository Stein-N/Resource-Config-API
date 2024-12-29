package net.xstopho.resourceconfigapi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigEntry {

    /**
     * Set the Category for the ConfigEntry.<br>
     * @return Category name
     */
    String category() default "";

    /**
     * Define a unified translation key for multiple ConfigEntries.<br>
     * This key will be used for the label and tooltip translation key.<br>
     * Example:<br>
     * You have 3 Solar Panels with the same Config options, here are only the ConfigEntries for the Basic,
     * but there will be the same options for advanced and industrial.
     * <pre>{@code
     * @Config(fileName = "solar panels", type = ConfigType.COMMON)
     * public class SolarPanels {
     *      @ConfigEntry(category = "Basic Solar Panel")
     *      public static int basicTier = 1;
     *
     *      @ConfigEntry(category = "Basic Solar Panel")
     *      public static int basicGenerateByDay = 25;
     *
     *      @ConfigEntry(category = "Basic Solar Panel")
     *      public static int basicGenerateByNight = 3;
     * }
     * }</pre>
     *
     * When you now set a generic Translation key like: tier, generateByDay and generateByNight <br>
     * You only have to add the translation once instead of 3 times, this reduces the
     * added translation keys from 9 down to 3
     *
     * @return Translation key used for Label and Tooltip
     */
    String translation() default "";
}
