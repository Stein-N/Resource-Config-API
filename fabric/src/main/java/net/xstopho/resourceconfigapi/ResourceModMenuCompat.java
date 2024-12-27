package net.xstopho.resourceconfigapi;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.client.gui.screen.ResourceConfigScreen;

import java.util.HashMap;
import java.util.Map;

public class ResourceModMenuCompat implements ModMenuApi {

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> screenFactoryMap = new HashMap<>();

        for (Map.Entry<ResourceLocation, ModConfig> entry : ConfigRegistry.CONFIGS.entrySet()) {
            String modId = entry.getKey().getNamespace();

            screenFactoryMap.put(modId, screen -> new ResourceConfigScreen(screen, modId));
        }

        return screenFactoryMap;
    }
}
