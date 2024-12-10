package net.xstopho.resourceconfigapi_test;

import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.util.ConfigType;

public class TestConfigs {

    @Config(fileName = "general", type = ConfigType.COMMON)
    public static class CommonConfig {

        @ConfigEntry(category = "Compatibilities")
        public static boolean disableModMenuCompat = false;
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
