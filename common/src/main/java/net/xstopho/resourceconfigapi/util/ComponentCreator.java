package net.xstopho.resourceconfigapi.util;

import net.minecraft.network.chat.Component;

public class ComponentCreator {

    public static Component label(String key) {
        return Component.translatable("config." + key + ".label");
    }

    public static Component tooltip(String key) {
        return Component.translatable("config." + key + ".tooltip");
    }
}
