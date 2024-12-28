package net.xstopho.resourceconfigapi.client.util;

import net.minecraft.network.chat.Component;

public class ComponentUtils {

    public static Component title(String key) {
        return build(String.format("%s.%s", key, "title"));
    }

    public static Component label(String key) {
        return build(String.format("%s.%s", key, "label"));
    }

    public static Component tooltip(String key) {
        return build(String.format("%s.%s", key, "tooltip"));
    }

    public static Component modConfig(String modId, String key) {
        return build(String.format("%s.%s", modId, key));
    }

    public static Component modTitle(String modId, String fileName, String key) {
        return title(String.format("%s.%s.%s", modId, fileName, key));
    }

    public static Component modLabel(String modId, String fileName, String key) {
        return label(String.format("%s.%s.%s", modId, fileName, key));
    }

    public static Component modTooltip(String modId, String fileName, String key) {
        return tooltip(String.format("%s.%s.%s.", modId, fileName, key));
    }

    private static Component build(String key) {
        return Component.translatable(
                String.format("%s.%s", "config", convert(key))
        );
    }

    private static String convert(String key) {
        return key.toLowerCase().replace(" ", "_");
    }
}
