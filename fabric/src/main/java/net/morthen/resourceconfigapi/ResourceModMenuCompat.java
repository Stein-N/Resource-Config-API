package net.morthen.resourceconfigapi;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.resources.Identifier;
import net.morthen.resourceconfigapi.api.ConfigRegistry;
import net.morthen.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.morthen.resourceconfigapi.config.ModConfig;

import java.util.HashMap;
import java.util.Map;

public class ResourceModMenuCompat implements ModMenuApi {

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> screenFactoryMap = new HashMap<>();

        for (Map.Entry<Identifier, ModConfig> entry : ConfigRegistry.getConfigEntries()) {
            String modId = entry.getKey().getNamespace();
            screenFactoryMap.put(modId, screen -> new ResourceConfigScreen(screen, modId));
        }

        return screenFactoryMap;
    }
}
