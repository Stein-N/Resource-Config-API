package net.xstopho.resourceconfigapi.gui;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.platform.Services;

import java.util.ArrayList;
import java.util.List;

public class ResourceConfigScreenRegistry{

    protected static List<String> CONFIGS = new ArrayList<>();

    public static void register(String modId, ResourceModConfig config) {
        isAlreadyRegistered(modId);
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new ConfigScreenBuilder(screen, Services.getModName(modId), config))
        );
        CONFIGS.add(modId);
    }

    protected static void isAlreadyRegistered(String modId) {
        if (CONFIGS.contains(modId)) {
            throw new IllegalStateException("You try to register multiple Config Screens for Mod Id: " + modId);
        }
    }
}
