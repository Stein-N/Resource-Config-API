package net.xstopho.resourceconfigapi.platform;

import net.fabricmc.loader.api.FabricLoader;
import net.xstopho.resourceconfigapi.platform.services.IPlatformHelper;

import java.nio.file.Path;

public class FabricPlatformHelper implements IPlatformHelper {

    private String modName;

    @Override
    public Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public String getModName(String modId) {
        FabricLoader.getInstance().getModContainer(modId).ifPresent(modContainer -> {
            modName = modContainer.getMetadata().getName();
        });
        return modName;
    }
}
