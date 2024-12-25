package net.xstopho.resourceconfigapi.annotations;


import net.xstopho.resourceconfigapi.api.ConfigType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
    String fileName();
    ConfigType type();
}
