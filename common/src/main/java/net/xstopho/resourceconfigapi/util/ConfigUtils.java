package net.xstopho.resourceconfigapi.util;

import net.minecraft.network.chat.Component;

public class ConfigUtils {

    public static Component createLabel(String key) {
        return Component.translatable("config." + key + ".label");
    }

    public static Component createTooltip(String key) {
        return Component.translatable("config." + key + ".tooltip");
    }
}
