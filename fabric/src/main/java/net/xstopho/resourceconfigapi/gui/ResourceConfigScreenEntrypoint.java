package net.xstopho.resourceconfigapi.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.xstopho.resourceconfigapi.ResourceConfig;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;

public class ResourceConfigScreenEntrypoint implements ModMenuApi {

    private final String modId;
    private final ResourceModConfig config;

    public ResourceConfigScreenEntrypoint(String modId, ResourceModConfig config) {
        isAlreadyRegistered(modId);
        this.modId = modId;
        this.config = config;

        ResourceConfig.CONFIGS.add(modId);
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new ConfigScreenBuilder(modId, config);
    }

    private void isAlreadyRegistered(String modId) {
        if (ResourceConfig.CONFIGS.contains(modId)) {
            throw new IllegalStateException("You try to register multiple Config Screens for Mod Id: " + modId);
        }
    }
}
