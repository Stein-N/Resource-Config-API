package net.xstopho.resourceconfigapi.platform;


import net.minecraft.client.Minecraft;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.platform.services.IPlatformHelper;

import java.nio.file.Path;
import java.util.ServiceLoader;

public class CoreServices {

    //TODO: separate Services between Client and Server Services

    public static Path getConfigPath() {
        return load(IPlatformHelper.class).getConfigDir();
    }

    public static Path getServerConfigPath() {
        return Path.of("./world/serverconfig");
    }

    public static boolean isInGame() {
        return Minecraft.getInstance().level != null;
    }

    public static boolean isServer() {
        return load(IPlatformHelper.class).isServer();
    }

    public static boolean isDevelopmentEnvironment() {
        return load(IPlatformHelper.class).isDevelopmentEnvironment();
    }

    public static String getModName(String modId) {
        return load(IPlatformHelper.class).getModName(modId);
    }

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}