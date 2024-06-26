package net.xstopho.resource_config_api.platform;

import net.fabricmc.loader.api.FabricLoader;
import net.xstopho.resource_config_api.platform.services.IPlatformHelper;

import java.nio.file.Path;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
