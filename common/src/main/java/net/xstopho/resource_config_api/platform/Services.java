package net.xstopho.resource_config_api.platform;

import net.xstopho.resource_config_api.ResourceConstants;
import net.xstopho.resource_config_api.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        ResourceConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}