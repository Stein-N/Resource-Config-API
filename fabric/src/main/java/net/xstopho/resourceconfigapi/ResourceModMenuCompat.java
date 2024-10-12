package net.xstopho.resourceconfigapi;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import java.util.Map;

public class ResourceModMenuCompat implements ModMenuApi {

    //TODO: correct implementation when gui is finished
    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        // String modId, ScreenFactory
        return Map.of();
    }
}
