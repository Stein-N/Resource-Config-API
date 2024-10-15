package net.xstopho.resourceconfigapi;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.api.ConfigType;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.gui.screen.ResourceConfigScreen;

import java.util.HashMap;
import java.util.Map;

public class ResourceModMenuCompat implements ModMenuApi {

    //TODO: correct implementation when gui is finished
    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> configScreenFactoryMap = new HashMap<>();

        for (Map.Entry<String, Map<ConfigType, ResourceModConfig>> entry : ConfigRegistry.getModConfigFiles().entrySet()) {
            configScreenFactoryMap.put(entry.getKey(), screen -> new ResourceConfigScreen(screen, entry.getKey()));
        }

        return configScreenFactoryMap;
    }
}
