package net.xstopho.resourceconfigapi_test;

import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.util.ConfigType;

import java.io.Serializable;

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

        @ConfigEntry(category = "SolarPanels")
        public static int generateByDay = 500;
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
