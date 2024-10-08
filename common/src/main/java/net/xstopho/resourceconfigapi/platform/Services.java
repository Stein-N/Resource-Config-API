package net.xstopho.resourceconfigapi.platform;


import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.platform.services.IPlatformHelper;

import java.nio.file.Path;
import java.util.ServiceLoader;

public class Services {

    public static Path getConfigPath() {
        return load(IPlatformHelper.class).getConfigDir();
    }

    public static String getModName(String modId) {
        return load(IPlatformHelper.class).getModName(modId);
    }

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        ResourceConfigConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}