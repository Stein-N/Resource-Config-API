package net.xstopho.resourceconfigapi.gui;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;

import java.util.ArrayList;
import java.util.List;

public class ResourceConfigScreenRegistry {

    protected static List<String> CONFIGS = new ArrayList<>();

    public static void register(String modId, ResourceModConfig config) {
        isAlreadyRegistered(modId);
        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class, () -> (modContainer, screen) -> new ConfigScreenBuilder(screen, modId, config));
        CONFIGS.add(modId);
    }

    protected static void isAlreadyRegistered(String modId) {
        if (CONFIGS.contains(modId)) {
            throw new IllegalStateException("You try to register multiple Config Screens for Mod Id: " + modId);
        }
    }
}
