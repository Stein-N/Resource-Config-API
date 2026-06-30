package net.morthen.resourceconfigapi.annotations;


import net.morthen.resourceconfigapi.api.ConfigType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    /**
     * Set the filename of your config, this can be different from the ClassName.<br>
     * This name will be used to create the translation key in thew following format: <br>
     * config.{modId}.{filename}
     * @return filename as a String
     */
    String fileName();

    /**
     * Set the Type of the Config<br>
     * Common configs should inherit all settings that are relevant for the client and the server f.e.: <br>
     * <ul>
     *     <li>durability values for Items</li>
     *     <li>drop chances</li>
     * </ul>
     * Client configs should only inherit settings that are relevant to the Client f.e.:<br>
     * <ul>
     *     <li>style options for guis or overlays</li>
     *     <li>sound options</li>
     * </ul>
     * Server configs should only inherit settings that are relevant to the Server f.e.:<br>
     * <ul>
     *     <li>behaviour of chunkloader when there is nobody on the server</li>
     * </ul>
     * @return
     */
    ConfigType type();
}
