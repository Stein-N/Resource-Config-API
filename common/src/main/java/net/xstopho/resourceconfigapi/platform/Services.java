package net.xstopho.resourceconfigapi.platform;

import net.xstopho.resourceconfigapi.ResourceConstants;
import net.xstopho.resourceconfigapi.platform.services.IPlatformHelper;

import java.nio.file.Path;
import java.util.ServiceLoader;

public class Services {

    public static Path CONFIG_DIR = load(IPlatformHelper.class).getConfigDir();

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        ResourceConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}