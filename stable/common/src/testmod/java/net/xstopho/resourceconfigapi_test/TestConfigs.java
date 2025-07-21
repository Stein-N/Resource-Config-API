package net.xstopho.resourceconfigapi_test;

import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.api.ConfigType;

import java.io.Serializable;
import java.util.List;

public class TestConfigs {

    @Config(fileName = "general", type = ConfigType.COMMON)
    public static class CommonConfig implements Serializable {

        @ConfigEntry(category = "Widget Tests")
        public static boolean testBoolean = true;

        @ConfigEntry(category = "Widget Tests", needsGameRestart = true)
        public static byte normalByte = 100;

        @ConfigEntry(category = "Widget Tests", needsWorldRestart = true)
        @RangedEntry(minValue = 1, maxValue = 3)
        public static byte rangedByte = 1;

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

        @ConfigEntry(category = "Widget Tests")
        public static int normalInteger = 23;

        @ConfigEntry(category = "Widget Tests")
        @RangedEntry(minValue = 5, maxValue = 50)
        public static int rangedInteger = 10;

        @ConfigEntry(category = "Widget Tests")
        public static long normalLong = 23;

        @ConfigEntry(category = "Widget Tests")
        @RangedEntry(minValue = 0, maxValue = 50)
        public static long rangedLong = 10;

        @ConfigEntry(category = "Widget Tests")
        public static short normalShort = 45;

        @ConfigEntry(category = "Widget Tests")
        @RangedEntry(minValue = 0, maxValue = 20)
        public static short rangedShort = 10;

        @ConfigEntry(category = "Widget Tests")
        public static String string = "Hello World";

        @ConfigEntry(category = "Widget Tests")
        public static ConfigType configType = ConfigType.COMMON;

        @ConfigEntry(category = "Widget Tests")
        public static List<String> list = List.of("Hello", "World");
    }

    @Config(fileName = "generator", type = ConfigType.COMMON)
    public static class Generators {

        @ConfigEntry(category = "Basic Solar Panel", translation = "tier")
        public static int basicTier = 1;

        @ConfigEntry(category = "Basic Solar Panel", translation = "generateByDay")
        public static int basicGenerateByDay = 50;

        @ConfigEntry(category = "Basic Solar Panel", translation = "generateByNight")
        public static int basicGenerateByNight = 5;

        @ConfigEntry(category = "Advanced Solar Panel", translation = "tier")
        public static int advancedTier = 1;

        @ConfigEntry(category = "Advanced Solar Panel", translation = "generateByDay")
        public static int advancedGenerateByDay = 50;

        @ConfigEntry(category = "Advanced Solar Panel", translation = "generateByNight")
        public static int advancedGenerateByNight = 5;
    }

    @Config(fileName = "general", type = ConfigType.SERVER)
    public static class ServerConfig {

        @ConfigEntry
        public static boolean syncOperatorStatus = true;
    }

    @Config(fileName = "general", type = ConfigType.CLIENT)
    public static class ClientConfig {

        @ConfigEntry
        public static boolean renderItemShape = true;
    }
}
