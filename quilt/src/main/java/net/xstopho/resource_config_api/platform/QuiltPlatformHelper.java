package net.xstopho.resource_config_api.platform;

import net.xstopho.resource_config_api.platform.services.IPlatformHelper;
import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

public class QuiltPlatformHelper implements IPlatformHelper {
    @Override
    public Path getConfigDir() {
        return QuiltLoader.getConfigDir();
    }

    @Override
    public boolean isDevEnvironment() {
        return QuiltLoader.isDevelopmentEnvironment();
    }
}
