package net.xstopho.resourceconfigapi_test;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.xstopho.resourceconfigapi.annotations.Config;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.util.ConfigType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConfigs {

    @Config(fileName = "general", type = ConfigType.COMMON)
    public static class CommonConfig implements Serializable {

        @ConfigEntry(category = "Compatibilities")
        public static boolean disableModMenuCompat = false;

        @ConfigEntry
        public static int testInteger = 100;

        @ConfigEntry
        public static List<Item> itemList = List.of(Items.DIAMOND, Items.RAW_IRON, Items.NETHERITE_INGOT);

        @ConfigEntry
        public static Map<String, String> hashMap = new HashMap<>(){{put("Hello", "World");}};
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
