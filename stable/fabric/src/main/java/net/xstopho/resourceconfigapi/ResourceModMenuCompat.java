package net.xstopho.resourceconfigapi;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.resources.Identifier;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;

import java.util.HashMap;
import java.util.Map;

public class ResourceModMenuCompat implements ModMenuApi {

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> screenFactoryMap = new HashMap<>();

        //TODO: rebuild the complete screen logic
//        for (Map.Entry<ResourceLocation, ModConfig> entry : ConfigRegistry.getConfigEntries()) {
//            String modId = entry.getKey().getNamespace();
//            screenFactoryMap.put(modId, screen -> new ResourceConfigScreen(screen, modId));
//        }

        return screenFactoryMap;
    }
}
