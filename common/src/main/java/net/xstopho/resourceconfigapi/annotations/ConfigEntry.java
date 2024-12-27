package net.xstopho.resourceconfigapi.annotations;

import net.xstopho.resourceconfigapi.client.gui.widget.config_list.ConfigListEntry;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigEntry {

    String category() default "";

    /**
     * Define a custom translation key, this is useful when multiple config settings
     * have the same purpose but f.e. different Tiers. <br>
     * Only set f.e. maxExtract.<br>
     * The key will be fully generated when the
     * {@link ConfigListEntry}
     * initialized the ValueEntries
     * @return Translation key
     */
    String translation() default "";
}
