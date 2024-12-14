package net.xstopho.resourceconfigapi_test;

import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.util.ConfigType;

import java.io.Serializable;
import java.util.List;

public class TestConfigs {

    @Config(fileName = "general", type = ConfigType.COMMON)
    public static class CommonConfig implements Serializable {

        @ConfigEntry(category = "Compatibilities")
        public static boolean disableModMenuCompat = false;

        @ConfigEntry
        public static int testInteger = 100;
    }

    @Config(fileName = "generator", type = ConfigType.COMMON)
    public static class GeneratorConfig {

        @ConfigEntry(category = "Basic Solar Panel")
        public static int basicTier = 1;

        @ConfigEntry(category = "Basic Solar Panel")
        public static int basicGenerateByDay = 500;

        @ConfigEntry(category = "Basic Solar Panel")
        public static int basicGenerateByNight = 50;

        @ConfigEntry(category = "Basic Solar Panel")
        public static int basicMaxExtract = 500;

        @ConfigEntry(category = "Advanced Solar Panel")
        public static int advancedTier = 1;

        @ConfigEntry(category = "Advanced Solar Panel")
        public static int advancedGenerateByDay = 500;

        @ConfigEntry(category = "Advanced Solar Panel")
        public static int advancedGenerateByNight = 50;

        @ConfigEntry(category = "Advanced Solar Panel")
        public static int advancedMaxExtract = 500;

        @ConfigEntry(category = "Industrial Solar Panel")
        public static int industrialTier = 1;

        @ConfigEntry(category = "Industrial Solar Panel")
        public static int industrialGenerateByDay = 500;

        @ConfigEntry(category = "Industrial Solar Panel")
        public static int industrialGenerateByNight = 50;

        @ConfigEntry(category = "Industrial Solar Panel")
        public static int industrialMaxExtract = 500;

        @ConfigEntry(category = "unsupported")
        public static List<String> testList = List.of("Hello", "World");
    }

    @Config(fileName = "rendering", type = ConfigType.CLIENT)
    public static class ClientConfig {

        @ConfigEntry(comment = "Item Rendering multiplier")
        public static int itemSizeMultiplier = 1;
    }

    @Config(fileName = "payloads", type = ConfigType.SERVER)
    public static class ServerConfig {

        @ConfigEntry(category = "Payloads", comment = "Ticks between sending update packets")
        public static int updateSpeed = 5;
    }
}
