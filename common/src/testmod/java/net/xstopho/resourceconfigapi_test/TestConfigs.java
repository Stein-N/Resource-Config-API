package net.xstopho.resourceconfigapi_test;

import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.api.ConfigType;

import java.io.Serializable;

public class TestConfigs {

    @Config(fileName = "general", type = ConfigType.COMMON)
    public static class CommonConfig implements Serializable {

        @ConfigEntry(category = "Widget Tests")
        public static boolean testBoolean = true;

        @ConfigEntry(category = "Widget Tests")
        public static byte normalByte = 100;

        @ConfigEntry(category = "Widget Tests")
        @RangedEntry(minValue = 0, maxValue = 50)
        public static byte rangedByte = 25;

        @ConfigEntry(category = "Widget Tests")
        public static double normalDouble = 2.3;

        @ConfigEntry(category = "Widget Tests")
        @RangedEntry(minValue = 0, maxValue = 10)
        public static double rangedDouble = 2.38;

        @ConfigEntry(category = "Widget Tests")
        public static float normalFloat = 23.4f;

        @ConfigEntry(category = "Widget Tests")
        @RangedEntry(minValue = 0, maxValue = 1)
        public static float rangedFloat = 0.04f;
    }
}
